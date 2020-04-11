package com.example.springbootdemo.product.service;

import com.example.springbootdemo.product.dto.PagetationDTO;
import com.example.springbootdemo.product.dto.QuestionDto;
import com.example.springbootdemo.product.mapper.QuestionMapper;
import com.example.springbootdemo.product.mapper.UserMapper;
import com.example.springbootdemo.product.model.Question;
import com.example.springbootdemo.product.model.QuestionExample;
import com.example.springbootdemo.product.model.User;
import com.example.springbootdemo.product.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.h2.util.StringUtils;
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
        List<Question> listQuestion = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(offset,size));

        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : listQuestion) {
            //将question数据复制后到questionDtoList
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(Integer.parseInt(question.getCreator()));
            List<User> users = userMapper.selectByExample(example);
//            User user = userMapper.selectId(question.getCreator());
            if(users.size()>0)
            questionDto.setUser(users.get(0));
            questionDtoList.add(questionDto);
        }
        Integer total =(int) questionMapper.countByExample(new QuestionExample());
        pagetationDTO.setQuestions(questionDtoList);
        pagetationDTO.setPagetation(total,page,size);

        return pagetationDTO;
    }

    public PagetationDTO getList(Integer id, int page, int size) {
        PagetationDTO pagetationDTO = new PagetationDTO();
        Integer offset =( page-1)*size;
        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdEqualTo(id);
        List<Question> listQuestion = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, size));
//        List<Question> listQuestion = questionMapper.listById(id,offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : listQuestion) {
            //将question数据复制后到questionDtoList
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            User user = userMapper.selectByPrimaryKey(Integer.parseInt(question.getCreator()));
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        QuestionExample example1 = new QuestionExample();
        example1.createCriteria().andIdEqualTo(id);
        Integer total = (int)questionMapper.countByExample(example1);
        pagetationDTO.setQuestions(questionDtoList);
        pagetationDTO.setPagetation(total,page,size);
        return pagetationDTO;
    }

    public QuestionDto getById(String id) {

       Question question =  questionMapper.selectByPrimaryKey(Integer.parseInt(id));
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(question.getCreator()));
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            questionMapper.insert(question);
        }else{
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question, example);
        }
    }
}
