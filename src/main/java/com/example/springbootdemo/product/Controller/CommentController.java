package com.example.springbootdemo.product.Controller;

import com.example.springbootdemo.product.dto.CommentDTO;
import com.example.springbootdemo.product.mapper.CommentMapper;
import com.example.springbootdemo.product.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object comment(@RequestBody CommentDTO commentDTO){
        Comment record = new Comment();
        record.setContent(commentDTO.getContent());
        record.setParentId(commentDTO.getParent_id());
        record.setType(commentDTO.getType());
        record.setLikeCount(0L);
        record.setGmtCreate(System.currentTimeMillis());
        record.setGmtModified(System.currentTimeMillis());
        record.setCommentor(1L);
        commentMapper.insert(record);
        return  null;
    }
}
