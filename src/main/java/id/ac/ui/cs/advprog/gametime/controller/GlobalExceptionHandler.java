package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.exception.QuantityValidationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuantityValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleQuantityValidationException(QuantityValidationException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
