package NewProject.example.hospital.management.system.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import NewProject.example.hospital.management.system.DTO.exception.ExceptionResponseDto;
import NewProject.example.hospital.management.system.DTO.exception.ValidationExceptionResponseDto;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ExceptionResponseDto> HandleResourceNotFoundException(ResourceNotFoundException re,
                        HttpServletRequest request) {

                ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(
                                LocalDateTime.now(),
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase(),
                                re.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(exceptionResponse);
        }

        @ExceptionHandler(DuplicateResourceException.class)
        public ResponseEntity<ExceptionResponseDto> HandleDuplicateResourceNotFoundException(
                        DuplicateResourceException re,
                        HttpServletRequest request) {

                ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(
                                LocalDateTime.now(),
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase(),
                                re.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(exceptionResponse);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ValidationExceptionResponseDto> handleMethodArgumentNotValidException(
                        MethodArgumentNotValidException ex, HttpServletRequest request) {

                Map<String, String> fieldErrors = new HashMap<>();

                ex.getBindingResult().getFieldErrors()
                                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

                ValidationExceptionResponseDto exceptionResponse = new ValidationExceptionResponseDto(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                "Validation failed",
                                request.getRequestURI(),
                                fieldErrors);

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(exceptionResponse);
        }

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ExceptionResponseDto> HandleRuntimeException(RuntimeException re,
                        HttpServletRequest request) {
                ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(
                                LocalDateTime.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                re.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(exceptionResponse);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ExceptionResponseDto> HandleGenericException(Exception re,
                        HttpServletRequest request) {

                ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(
                                LocalDateTime.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                re.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(exceptionResponse);
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ExceptionResponseDto> HandleMethodNOTAllowedException(
                        HttpRequestMethodNotSupportedException re,
                        HttpServletRequest request) {

                ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(
                                LocalDateTime.now(),
                                HttpStatus.METHOD_NOT_ALLOWED.value(),
                                HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(),
                                re.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.METHOD_NOT_ALLOWED)
                                .body(exceptionResponse);
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ExceptionResponseDto> handleBadRequestException(
                        BadRequestException ex,
                        HttpServletRequest request) {

                ExceptionResponseDto response = new ExceptionResponseDto(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(response);
        }

}
