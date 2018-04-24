package com.oldmanw.sell.handler;

import com.oldmanw.sell.exception.SellerAuthorizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellExceptionHandler {

    //拦截登陆异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView sellerAuthorizeExceptionHandler() {
        return new ModelAndView("redirect:/wechat/sellerSystem");
    }

}
