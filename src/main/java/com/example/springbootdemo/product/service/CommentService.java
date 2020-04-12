package com.example.springbootdemo.product.service;

import com.example.springbootdemo.enums.CommentTypeEnum;
import com.example.springbootdemo.exception.CustomizeErrorCode;
import com.example.springbootdemo.exception.CustomizeException;
import com.example.springbootdemo.product.dto.CommentDTO;
import com.example.springbootdemo.product.mapper.CommentMapper;
import com.example.springbootdemo.product.mapper.QuestionExtMapper;
import com.example.springbootdemo.product.mapper.QuestionMapper;
import com.example.springbootdemo.product.model.Comment;
import com.example.springbootdemo.product.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

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
}
