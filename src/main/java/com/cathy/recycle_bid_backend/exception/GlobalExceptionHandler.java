package com.cathy.recycle_bid_backend.exception;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 422：商業邏輯錯誤
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException e) {

        log.warn("商業邏輯錯誤: {}", e.getMessage());

        return ResponseEntity
                .unprocessableContent()
                .body(e.getMessage());
    }

    // 400：Request 驗證錯誤
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(
            MethodArgumentNotValidException e) {

        String message = e.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        log.warn("Request 驗證失敗: {}", message);

        return ResponseEntity
                .badRequest()
                .body(message);
    }

    // 404：找不到資料
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {

        log.warn("找不到資料: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    // 500：其他沒預期到的系統錯誤
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {

        log.error("系統發生未預期錯誤", e);

        return ResponseEntity
                .internalServerError()
                .body("系統發生錯誤");
    }

}
