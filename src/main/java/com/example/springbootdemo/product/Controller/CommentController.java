package com.example.springbootdemo.product.Controller;

import com.example.springbootdemo.product.dto.CommentDTO;
import com.example.springbootdemo.product.enums.CommentTypeEnum;
import com.example.springbootdemo.product.exception.CustomizeErrorCode;
import com.example.springbootdemo.product.exception.CustomizeException;
import com.example.springbootdemo.product.dto.CommentCreateDTO;
import com.example.springbootdemo.product.dto.ResultDTO;
import com.example.springbootdemo.product.model.Comment;
import com.example.springbootdemo.product.model.User;
import com.example.springbootdemo.product.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object comment(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            throw new  CustomizeException(CustomizeErrorCode.USER_NOT);
        }
        if(commentCreateDTO==null|| StringUtils.isBlank(commentCreateDTO.getContent())){
            return new ResultDTO(CustomizeErrorCode.COMMENT_CONTENT_EMPTY);
        }
        Comment comment = new Comment();
        comment.setContent(commentCreateDTO.getContent());
        comment.setLikeCount(0L);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentor(user.getId());
        comment.setType(commentCreateDTO.getType());
        comment.setParentId(commentCreateDTO.getParent_id());
        commentService.insert(comment);
        return new ResultDTO(CustomizeErrorCode.RESULT_SUCCESS);
    }

    /**
     * 查看二级评论
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/comment/{id}")
    public ResultDTO comment(@PathVariable("id")Long id){
        List<CommentDTO> comments = commentService.listById(id, CommentTypeEnum.COMMENT);
        return  new ResultDTO(CustomizeErrorCode.RESULT_SUCCESS,comments);

    }
}
