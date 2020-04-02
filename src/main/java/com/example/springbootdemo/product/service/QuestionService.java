package com.example.springbootdemo.product.service;

import com.example.springbootdemo.product.dto.PagetationDTO;
import com.example.springbootdemo.product.dto.QuestionDto;
import com.example.springbootdemo.product.mapper.QuestionMapper;
import com.example.springbootdemo.product.mapper.UserMapper;
import com.example.springbootdemo.product.model.Question;
import com.example.springbootdemo.product.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要做数据中间层
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PagetationDTO getList(int page, int size) {
        PagetationDTO pagetationDTO = new PagetationDTO();
        Integer offset =( page-1)*size;
        List<Question> listQuestion = questionMapper.list(offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : listQuestion) {
            //将question数据复制后到questionDtoList
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            User user = userMapper.selectId(question.getCreator());
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        Integer total = questionMapper.getCount();
        pagetationDTO.setQuestions(questionDtoList);
        pagetationDTO.setPagetation(total,page,size);

        return pagetationDTO;
    }
}
