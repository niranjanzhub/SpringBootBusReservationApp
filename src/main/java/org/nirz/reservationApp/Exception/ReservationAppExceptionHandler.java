package org.nirz.reservationApp.Exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.nirz.reservationApp.Dto.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationAppExceptionHandler {

    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleAdminNotFoundException(AdminNotFoundException exception) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setData("Admin Not Found");
        structure.setMessage(exception.getMessage());
        structure.setStatusCode(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(structure);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleUserNotFoundException(UserNotFoundException exception) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setData("User Not Found");
        structure.setMessage(exception.getMessage());
        structure.setStatusCode(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(structure);
    }

    @ExceptionHandler(BusNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleBusNotFoundException(BusNotFoundException exception) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setData("Bus Not Found");
        structure.setMessage(exception.getMessage());
        structure.setStatusCode(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(structure);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseStructure<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setData("Constraint Violation");
        structure.setMessage(ex.getMessage());
        structure.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structure);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        for (ObjectError objectError : objectErrors) {
            String fieldName = ((FieldError) objectError).getField();
            String errorMessage = objectError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }

        ResponseStructure<Map<String, String>> structure = new ResponseStructure<>();
        structure.setData(errors);
        structure.setMessage("Validation Error");
        structure.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structure);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<String>> handleGenericException(Exception exception) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setData("An unexpected error occurred");
        structure.setMessage(exception.getMessage());
        structure.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(structure);
    }
    
 

	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(IllegalStateException.class)
	public ResponseStructure<String> handle(IllegalStateException exception) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("Cannot SingIn");
		structure.setMessage(exception.getMessage());
		structure.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		return structure;
	}

	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseStructure<String> handle(IllegalArgumentException exception) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("Cannot Complete the Action");
		structure.setMessage(exception.getMessage());
		structure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
		return structure;
}
}