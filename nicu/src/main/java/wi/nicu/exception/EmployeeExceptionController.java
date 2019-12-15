package wi.nicu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //Handle the exceptions globally
public class EmployeeExceptionController {
	@ExceptionHandler(value = EmployeeNotFoundException.class) //Used to handle the specific exceptions and sending the custom responses to the client
	public ResponseEntity<Object> exception(EmployeeNotFoundException exception){
		return new ResponseEntity<>("null",HttpStatus.NOT_FOUND); //Return Http body and status code
	}
}
