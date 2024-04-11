package com.pea.common.exception;

import com.pea.common.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(GlobalException.class)
    public Result<Object> handleApiException(GlobalException e) {
        log.error("自定义GlobalException 抛出:", e);
        return Result.failed(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.failed();
    }

}
