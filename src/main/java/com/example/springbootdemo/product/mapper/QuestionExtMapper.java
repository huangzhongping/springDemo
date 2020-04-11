package com.example.springbootdemo.product.mapper;

import com.example.springbootdemo.product.model.Question;
import com.example.springbootdemo.product.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 自定义mapper 处理数据库累加（并发处理）
 */
public interface QuestionExtMapper {
    int incView(Question question);

}