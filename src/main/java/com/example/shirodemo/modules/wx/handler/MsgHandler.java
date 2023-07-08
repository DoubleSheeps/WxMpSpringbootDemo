package com.example.shirodemo.modules.wx.handler;

import com.example.shirodemo.Utils.JsonUtils;
import com.example.shirodemo.modules.wx.service.TemplateMessageService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Component
@AllArgsConstructor
public class MsgHandler extends AbstractHandler {

    private final TemplateMessageService templateMessageService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
            String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);
            System.out.println(content);
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
//            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
//                && weixinService.getKefuService().kfOnlineList()
//                .getKfOnlineList().size() > 0) {
//                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
//                    .fromUser(wxMessage.getToUser())
//                    .toUser(wxMessage.getFromUser()).build();
//            }
            if (StringUtils.startsWithAny(wxMessage.getContent(), "绑定", "账号")){
                WxMpUser userWxInfo = weixinService.getUserService()
                    .userInfo(wxMessage.getFromUser(), null);
                templateMessageService.sendBindMessage(userWxInfo);
            }else if(StringUtils.startsWithAny(wxMessage.getContent(), "课程", "上课","课表")){
                WxMpUser userWxInfo = weixinService.getUserService()
                    .userInfo(wxMessage.getFromUser(), null);
                templateMessageService.sendClassTipMessage(userWxInfo);
            }else {
                //return new TextBuilder().build("客服不在线哦。", wxMessage, weixinService);
                return null;
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }catch (Exception e){
            System.out.println( "------------- "+this.getClass().toString()+".PostData() : 出现异常 Exception -------------");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
