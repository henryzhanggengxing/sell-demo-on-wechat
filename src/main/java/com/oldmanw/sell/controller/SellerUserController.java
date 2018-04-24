package com.oldmanw.sell.controller;

import com.oldmanw.sell.config.ProjectUrlConfig;
import com.oldmanw.sell.constant.CookieConstant;
import com.oldmanw.sell.constant.RedisConstant;
import com.oldmanw.sell.dataobject.SellerInfo;
import com.oldmanw.sell.enums.ResultEnum;
import com.oldmanw.sell.service.SellerService;
import com.oldmanw.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 负责用户的登陆和登出
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {

        //1.openid和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            //登录失败重新跳转至登陆的界面
            map.put("url", "/wechat/sellerSystem");
            return new ModelAndView("common/error");
        }

        //2.设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expireTime = RedisConstant.EXPIRE_TIME;
        //key, value, 过期时间, 时间单位
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expireTime, TimeUnit.SECONDS);

        //3.设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE_TIME);

        //先返回成功页面，再返回至订单页面
        map.put("msg", ResultEnum.LOGIN_SUCCESS.getMsg());
        map.put("url", "/seller/order/list");
        return new ModelAndView("common/success", map);

        //直接返回至订单页面
//        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/seller/order/list");

        //不能直接返回空的模板！！！
        //        return new ModelAndView("order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {

        //1.从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2.清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            //3.清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        //跳转到登出成功页面
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMsg());
        //由于使用了aop身份验证，因此跳转到订单页面不会成功，会在SellExceptionHandler中重新跳转至扫码登陆页面
        map.put("url", "/seller/order/list");
        return new ModelAndView("common/success", map);
    }

}
