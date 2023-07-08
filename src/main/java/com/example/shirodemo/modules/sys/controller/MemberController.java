package com.example.shirodemo.modules.sys.controller;

import com.example.shirodemo.error.BusinessException;
import com.example.shirodemo.error.EmBusinessError;
import com.example.shirodemo.model.WxUser;
import com.example.shirodemo.model.common.CommonReturnType;
import com.example.shirodemo.modules.sys.controller.VO.ActivityForm;
import com.example.shirodemo.modules.sys.controller.VO.TagForm;
import com.example.shirodemo.modules.sys.controller.VO.TemplateMsgBatchForm;
import com.example.shirodemo.modules.sys.controller.VO.UserTagForm;
import com.example.shirodemo.modules.wx.service.MemberService;
import com.example.shirodemo.modules.wx.service.TemplateMessageService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {
    private final TemplateMessageService templateMessageService;
    private final MemberService memberService;



    @GetMapping("/template")
//    @RequiresPermissions("wx:msgtemplate:list")
    public CommonReturnType listTemplate(){
        return CommonReturnType.create(templateMessageService.getWxTemplateList());
    }
    @GetMapping("/aync/template")
//    @RequiresPermissions("wx:msgtemplate:list")
    public CommonReturnType ayncTemplate() throws WxErrorException {
        templateMessageService.syncWxTemplate();
        return CommonReturnType.create("同步模板中，请稍后刷新页面！");
    }
    @GetMapping("/activity")
//    @RequiresPermissions("wx:msgtemplate:list")
    public CommonReturnType listActivity(){
        return CommonReturnType.create(templateMessageService.list());
    }

    @PostMapping("/activity/add")
    public CommonReturnType addActivity(@RequestBody ActivityForm form){
        templateMessageService.addActivity(form);
        return CommonReturnType.create("添加成功");
    }

    @PostMapping("/activity/push")
    public CommonReturnType pushActivity(@RequestBody TemplateMsgBatchForm form){
        templateMessageService.sendMsgBatch(form);
        return CommonReturnType.create("正在发送中，可在发送日志中查看进度。");
    }

    @GetMapping("/list")
//    @RequiresPermissions("wx:msgtemplate:list")
    public CommonReturnType listMember()  {
        return CommonReturnType.create(memberService.getAll());
    }
    @GetMapping("/aync")
//    @RequiresPermissions("wx:msgtemplate:list")
    public CommonReturnType ayncMember() {
        memberService.syncWxUsers();
        return CommonReturnType.create("同步中，请稍后刷新页面查看");
    }

    @GetMapping("/userTags/{openid}")
    public CommonReturnType userTags(@PathVariable String openid){
        if(openid==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"openid为空");
        }
        WxUser wxUser = memberService.getById(openid);
        if(wxUser==null){
            wxUser=memberService.refreshUserInfo(openid);
            if(wxUser==null) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户未关注");
            }
        }
        return CommonReturnType.create(wxUser.getTagidList());
    }

    @PostMapping("/tagging")
    public CommonReturnType tagging(@RequestBody UserTagForm form) {
        try {
            memberService.tagging(form.getTagid(), form.getOpenid());
        }catch (WxErrorException e){
            WxError error = e.getError();
            if(50005==error.getErrorCode()){//未关注公众号
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户未关注");
            }else {
                throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,error.getErrorMsg());
            }
        }
        return CommonReturnType.create("绑定成功！");
    }

    @PostMapping("/untagging")
    public CommonReturnType untagging(@RequestBody UserTagForm form) throws WxErrorException {
        memberService.untagging(form.getTagid(), form.getOpenid());
        return CommonReturnType.create("解绑成功！");
    }

    @GetMapping("/tag/list")
    public CommonReturnType list() throws WxErrorException {
        List<WxUserTag> wxUserTags =  memberService.getWxTags();
        return CommonReturnType.create(wxUserTags);
    }

    @PostMapping("/tag/save")
    public CommonReturnType save(@Valid @RequestBody TagForm form) throws WxErrorException{
        Long tagid = form.getId();
        if(tagid==null || tagid<=0){
            return CommonReturnType.create(memberService.creatTag(form.getName()));
        }else {
            return CommonReturnType.create(memberService.updateTag(tagid,form.getName()));
        }
    }
}
