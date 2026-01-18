package com.portfolio.my_portfolio_backend.exception.handler;

import com.portfolio.my_portfolio_backend.exception.ValidationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {




    @ExceptionHandler(ValidationException.class)
    public String handlerValidationEception(ValidationException ex, Model model){


        model.addAttribute("errors", ex.getBindingResult().getAllErrors());
        model.addAttribute("message", "Se encontraron errrores de validacion");
        return "error/validation";
    }
}
