package com.mmrath.sapp.spring;

import com.mmrath.sapp.ApplicationRuntimeException;
import com.mmrath.sapp.Message;
import com.mmrath.sapp.MessageCollection;
import com.mmrath.sapp.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  private final MessageSource messageSource;

  @Autowired
  public GlobalExceptionHandler(MessageSource messageSource){
    this.messageSource = messageSource;
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    Locale currentLocale = LocaleContextHolder.getLocale();
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
    MessageCollection messages = new MessageCollection();
    for (FieldError fieldError : fieldErrors) {
      Message message = new Message();
      message.setField(fieldError.getField());
      message.setText(messageSource.getMessage(fieldError,currentLocale));
      messages.error(message);
    }
    for (ObjectError objectError : globalErrors) {
      Message message = new Message();
      message.setText(objectError.getDefaultMessage());
      messages.error(message);
    }
    return new ResponseEntity(messages, headers, status);
  }

  @ExceptionHandler({ResourceNotFoundException.class})
  public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request){
    return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({ApplicationRuntimeException.class})
  public ResponseEntity<List<Message>> handleApplicationRuntimeException(ApplicationRuntimeException e, WebRequest request){
    return new ResponseEntity<>(e.getMessages(),HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
      request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
    }
    logger.error("Error handling request", ex);
    return new ResponseEntity<Object>(body, headers, status);
  }
}