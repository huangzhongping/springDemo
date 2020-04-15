package com.example.springbootdemo.product.mapper;

import com.example.springbootdemo.product.model.Comment;
import com.example.springbootdemo.product.model.CommentExample;
import com.example.springbootdemo.product.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}