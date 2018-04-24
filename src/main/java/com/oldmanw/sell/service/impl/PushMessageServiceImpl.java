package com.oldmanw.sell.service.impl;

import com.oldmanw.sell.config.OpenidConfig;
import com.oldmanw.sell.config.WechatAccountConfig;
import com.oldmanw.sell.dto.OrderDTO;
import com.oldmanw.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private OpenidConfig openidConfig;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {

        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        templateMessage.setToUser(openidConfig.getMpOpenid());

        List<WxMpTemplateData> list = Arrays.asList(
                //测试号这些都用不到。。。
//                new WxMpTemplateData("first", "亲，请记得收货。"),
//                new WxMpTemplateData("keyword1", "微信点餐"),
//                new WxMpTemplateData("keyword2", "18868812345"),
//                new WxMpTemplateData("keyword3", orderDTO.getOrderId()),
//                new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnum().getMsg()),
//                new WxMpTemplateData("keyword5", "￥" + orderDTO.getOrderAmount()),
//                new WxMpTemplateData("remark", "欢迎再次光临！")
        );
        templateMessage.setData(list);

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.info("【微信模板消息异常】 消息发送失败，e = {}", e);
        }
    }
}
