package com.example.springbootdemo.product.service;

import com.example.springbootdemo.product.dto.CommentDTO;
import com.example.springbootdemo.product.enums.CommentTypeEnum;
import com.example.springbootdemo.product.exception.CustomizeErrorCode;
import com.example.springbootdemo.product.exception.CustomizeException;
import com.example.springbootdemo.product.mapper.CommentMapper;
import com.example.springbootdemo.product.mapper.QuestionExtMapper;
import com.example.springbootdemo.product.mapper.QuestionMapper;
import com.example.springbootdemo.product.mapper.UserMapper;
import com.example.springbootdemo.product.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;
    //事物
    @Transactional
    public void insert(Comment comment) {

       if(null==comment.getParentId()||comment.getParentId()==0){
           throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
       }
       if(comment.getType()==null|| !CommentTypeEnum.isExit(comment.getType())){
           throw  new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WARN);
       }

       if(comment.getType().equals(CommentTypeEnum.COMMENT)){
           //回复评论
           Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
           if(dbComment==null) {
               throw new CustomizeException(CustomizeErrorCode.COMMENT_PARAM_NOT_FOUND);
           }
           commentMapper.insert(dbComment);
       }else{
           //回复问题
           Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
            }
           commentMapper.insert(comment);
            //增加评论数
           question.setCommentCount(1L);
           questionExtMapper.incCommentCount(question);
       }
    }


    /**
     * 通过questionId 获取回复列表
     * @param id
     * @return
     */
    public List<CommentDTO> listByQuestionId(Long id) {
        //1.通过问题id和提问类型找到集合
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(CommentTypeEnum.QUEESTION.getType());
        //按照创建时间倒序
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        //2.找到comments里面的user set去重
        Set<Long> usersets = comments.stream().map(item -> item.getCommentor()).collect(Collectors.toSet());
        if(usersets.size()==0){
            return  new ArrayList<>();
        }
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(usersets);
        //3.通过userIds 找到users
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);

        //4.将set转成map方便组合
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //5.组合成新的List<CommentDTO>
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentor()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
