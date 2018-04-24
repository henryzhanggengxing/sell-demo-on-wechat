package com.oldmanw.sell.controller;

import com.oldmanw.sell.config.OpenidConfig;
import com.oldmanw.sell.config.ProjectUrlConfig;
import com.oldmanw.sell.config.WechatAccountConfig;
import com.oldmanw.sell.enums.ResultEnum;
import com.oldmanw.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 借用账号不使用本地的验证登陆方法(authorize,userInfo和qrAuthorize)
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Autowired
    private OpenidConfig openidConfig;

    @GetMapping("/authorize")
    public String Authorize(@RequestParam("returnUrl") String returnUrl) {
        String url = projectUrlConfig.getWechatMpAuthorize() + "/wechat/userInfo";
        String redirectUrl = null;
        try {
            redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
        return "redirect:" + redirectUrl;
//        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx67c842c73fadb5a9&redirect_uri=http://sell123.nat300.top/sell/wechat/userInfo&response_type=code&scope=snsapi_userinfo&state=http%3A%2F%2Fwww.baidu.com#wechat_redirect";
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {

//        System.out.println("进入userInfo方法");
//        return null;

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();

        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权错误】 {}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }

        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl + "?openid=" + openId;
    }

    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
//        String url = projectUrlConfig.getWechatOpenAuthorize() + "/sell/wechat/qrUserInfo";
        String url = "http://sell.springboot.cn/sell/wechat/qrUserInfo";
        String redirectUrl = null;
        try {
            redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QrConnectScope.SNSAPI_LOGIN, URLEncoder.encode(returnUrl, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code) {

        String returnUrl = "http://sell123.nat300.top/seller/login";
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();

        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权错误】 {}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        log.info("wxMpOAuth2AccessToken = {}", wxMpOAuth2AccessToken);
        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl + "?openid=" + openId;

    }

    /**
     * 使用借用账号情况下的转发方法，转发至借用账号的公众平台进行验证
     */
    @GetMapping("/sellerSystem")
    public String myRedirect() {
        String returnUrl = "https://open.weixin.qq.com/connect/qrconnect?appid=" + wechatAccountConfig.getOpenAppId()
                + "&redirect_uri=http%3A%2F%2Fsell.springboot.cn%2Fsell%2Fqr%2F" + openidConfig.getMpOpenid()
                + "&response_type=code&scope=snsapi_login&state=http%3a%2f%2fsell123.nat300.top%2fwechat%2fqrUserInfo";

        return "redirect:" + returnUrl;
    }

}
