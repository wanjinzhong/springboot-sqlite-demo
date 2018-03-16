package com.neil.demo;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.mchange.v2.resourcepool.CannotAcquireResourceException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(0)
public class GlobalExceptionHandler {

    @ExceptionHandler(CannotCreateTransactionException.class)
    public String handleUnauthenticatedException() {
        Common.isDataBaseOK = false;
        return "数据库异常";
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "异常";
    }
}