package com.payvyne.merchant.rest;

import com.payvyne.merchant.domain.common.ServiceException;
import com.payvyne.merchant.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

  @ExceptionHandler(value = ServiceException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @RequestMapping(produces = "application/json")
  private ApiResponse<Void> borrowBookException(ServiceException exception) {
    log.error("Handling ServiceException", exception);
    return ApiResponse.createErrorResponse(ApiError.fromErrorCode(exception.getError()));
  }

  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @RequestMapping(produces = "application/json")
  private ApiResponse<Void> unknownException(Exception exception) {
    log.error("Handling UnknownException", exception);
    return ApiResponse.createErrorResponse(ApiError.fromErrorCode(ErrorCode.E1));
  }
}
