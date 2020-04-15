package com.example.springbootdemo.product.service;

import com.example.springbootdemo.product.dto.NotificationDTO;
import com.example.springbootdemo.product.dto.PagetationDTO;
import com.example.springbootdemo.product.dto.QuestionDto;
import com.example.springbootdemo.product.enums.NotificationStatusEnum;
import com.example.springbootdemo.product.enums.NotificationTypeEnum;
import com.example.springbootdemo.product.mapper.NotificationMapper;
import com.example.springbootdemo.product.mapper.QuestionMapper;
import com.example.springbootdemo.product.mapper.UserMapper;
import com.example.springbootdemo.product.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PagetationDTO getList(Long id, int page, int size) {
        PagetationDTO pagetationDTO = new PagetationDTO();
        Integer offset =( page-1)*size;
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiveridEqualTo(id);
        notificationExample.setOrderByClause("gtm_create desc");
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(notificationExample,new RowBounds(offset,size));
        if(notificationList.size()==0){
            notificationList = new ArrayList<>();
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notificationList) {
            //将question数据复制后到questionDtoList
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setGtm_create(notification.getGtmCreate());
            User user = userMapper.selectByPrimaryKey(notification.getSenderid());
            notificationDTO.setName(user.getName());
            notificationDTO.setQuestionId(notification.getQuestionid());
            Question question = questionMapper.selectByPrimaryKey(notification.getQuestionid());
            notificationDTO.setQuesitonTitle(question.getTitle());
            notificationDTO.setRead(notification.getType());
            notificationDTO.setId(notification.getId());
            notificationDTO.setTypeName(NotificationStatusEnum.getName(notification.getStatus()));
            notificationDTOS.add(notificationDTO);
        }
        Integer total =(int) notificationMapper.countByExample(new NotificationExample());
        pagetationDTO.setData(notificationDTOS);
        pagetationDTO.setPagetation(total,page,size);

        return pagetationDTO;
    }

    public long getUnReadCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiveridEqualTo(id).
                andTypeEqualTo(NotificationTypeEnum.UNREAD.getType());
        long count = notificationMapper.countByExample(notificationExample);
        return count;
    }

    public void read(long id) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        notification.setType(NotificationTypeEnum.READ.getType());
        notificationMapper.updateByPrimaryKeySelective(notification);

    }
}
