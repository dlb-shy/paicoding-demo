package com.sun.exception;

import com.sun.dto.Status;
import com.sun.dto.StatusEnum;
import com.sun.util.JsonUtil;
import com.sun.vo.ResVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.NestedRuntimeException;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sunshine
 * @date 2023/4/23 下午6:16
 */
@Slf4j
@Order(-100)
public class ForumExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Status errStatus = buildToastMsg(ex);
        if (restResponse(request, response)) {
            // 表示返回Json数据格式的异常提示信息
            if (response.isCommitted()) {
                // 如果返回已提交过，直接退出即可
                return new ModelAndView();
            }

            try {
                response.reset();
                //
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setHeader("Cache-Control", "no-cache, must-revalidate");
                response.getWriter().println(JsonUtil.toStr(ResVo.fail(errStatus)));
                response.getWriter().flush();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        String view = getErrorPage(errStatus, response);
        ModelAndView mv = new ModelAndView(view);
        response.setContentType(MediaType.TEXT_HTML_VALUE);
//        mv.getModel().put("global", SpringUtil.getBean(GlobalIni))
        mv.getModel().put("res", ResVo.fail(errStatus));
        mv.getModel().put("toast", JsonUtil.toStr(ResVo.fail(errStatus)));
        return mv;
    }

    public Status buildToastMsg(Exception ex) {
        if (ex instanceof ForumException) {
            return ((ForumException) ex).getStatus();
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            return Status.newStatus(StatusEnum.RECORDS_NOT_EXISTS, ExceptionUtils.getStackTrace(ex));
        } else if (ex instanceof NestedRuntimeException) {
            log.error("unexpect error! {}", ReqInfoContext.getReqInfo(), ex);
            return Status.newStatus(StatusEnum.UNEXPECT_ERROR, ex.getMessage());
        } else {
            log.error("unexpect error! {}", ReqInfoContext.getReqInfo(), ex);
            return Status.newStatus(StatusEnum.UNEXPECT_ERROR, ExceptionUtils.getStackTrace(ex));
        }


    }

    private boolean restResponse(HttpServletRequest request, HttpServletResponse response) {
        if (request.getRequestURI().startsWith("/api/admin/") || request.getRequestURI().startsWith("/admin")) {
            return true;
        }
        if (request.getRequestURI().startsWith("/image/upload")) {
            return true;
        }
        if (response.getContentType() != null && response.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            return true;
        }
        if (isAjaxRequest(request)) {
            return true;
        }
        // 数据接口请求
        AntPathMatcher pathMatcher = new AntPathMatcher();
        if (pathMatcher.match("/**/api/**", request.getRequestURI())) {
            return true;
        }
        return false;
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWith);
    }

    private String getErrorPage(Status status, HttpServletResponse response) {
        // 根据异常吗解析需要返回的错误页面
        if (StatusEnum.is5xx(status.getCode())) {
            response.setStatus(500);
            return "error/500";
        } else if (StatusEnum.is403(status.getCode())) {
            response.setStatus(403);
            return "error/403";
        } else {
            response.setStatus(404);
            return "error/404";
        }
    }
}

















