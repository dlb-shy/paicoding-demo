package com.sun.controller;

import com.mysql.cj.util.StringUtils;
import com.sun.helper.WxHelper;
import com.sun.vo.BaseWxMsgResVo;
import com.sun.vo.WxTxtMsgReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sunshine
 * @date 2023/5/11 下午2:22
 */
@RequestMapping(path = "wx")
@RestController
public class WxRestController {

    @Autowired
    WxHelper wxHelper;

    /**
     * 微信的公众号接入 token 验证，即返回echostr的参数值
     *
     * @param request
     * @return
     */
    @GetMapping(path = "callback")
    public String check(HttpServletRequest request) {
        String echoStr = request.getParameter("echostr");
        if (StringUtils.isNullOrEmpty(echoStr)) {
            return echoStr;
        }
        return "";
    }

    @PostMapping(path = "callback",
        consumes = {"application/xml", "text/xml"},
        produces = "application/xml;charset=utf-8")
    public BaseWxMsgResVo callBack(@RequestBody WxTxtMsgReqVo msg){
        String content = msg.getContent();
        // 获取验证码，与redis里面的进行对比  固定验证码
        BaseWxMsgResVo res = wxHelper.buildResponseBody(msg.getEvent(), content, msg.getFromUserName());
        fillResVo(res, msg);
        return res;
    }

    private void fillResVo(BaseWxMsgResVo res, WxTxtMsgReqVo msg) {
        res.setFromUserName(msg.getToUserName());
        res.setToUserName(msg.getFromUserName());
        res.setCreateTime(System.currentTimeMillis() / 1000);
    }

}
