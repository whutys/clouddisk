package cn.clouddisk.controller.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NoPermissionException {
    private static final Logger log = LoggerFactory.getLogger(NoPermissionException.class);

    @ExceptionHandler(UnauthorizedException.class)
    public String handleShiroException(Exception e) {
        log.info(e.getMessage());
        return "redirect:/unauth";
    }

    @ExceptionHandler(AuthorizationException.class)
    public String AuthorizationException(Exception e) {
        log.info(e.getMessage());
        return "redirect:/unauth";
    }

}
