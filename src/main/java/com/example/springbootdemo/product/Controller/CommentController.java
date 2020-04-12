package com.example.springbootdemo.product.Controller;

import com.example.springbootdemo.product.exception.CustomizeErrorCode;
import com.example.springbootdemo.product.exception.CustomizeException;
import com.example.springbootdemo.product.dto.CommentCreateDTO;
import com.example.springbootdemo.product.dto.ResultDTO;
import com.example.springbootdemo.product.model.Comment;
import com.example.springbootdemo.product.model.User;
import com.example.springbootdemo.product.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
}
