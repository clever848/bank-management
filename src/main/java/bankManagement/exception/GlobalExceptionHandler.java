package bankManagement.exception;

import bankManagement.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import bankManagement.dto.Response;
import bankManagement.exception.IllegalTransaction;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(NullException.class)
	public ResponseEntity<Response<String>> handleNullException(NullException e)
	{
		Response<String> res = new Response<String>();
		res.setData(null);
		res.setMessage(e.getMessage());
		res.setStausCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(NonUniqueException.class)
	public ResponseEntity<Response<String>> handleNUE(NonUniqueException e)
	{
		Response<String> res = new Response<String>();
		res.setMessage(e.getMessage());
		res.setStausCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(InvalidException.class)
	public ResponseEntity<Response<String>> handleIE(InvalidException e)
	{
		Response<String> res = new Response<String>();
		res.setMessage(e.getMessage());
		res.setData(null);
		res.setStausCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Response<String>> handleNFE(NotFoundException e)
	{
		Response<String> res = new Response<String>();
		res.setData(null);
		res.setMessage(e.getMessage());
		res.setStausCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(IllegalTransaction.class)
	public ResponseEntity<Response<String>> handleIT(IllegalTransaction e)
	{
		Response<String> res = new Response<String>();
		res.setData(null);
		res.setMessage(e.getMessage());
		res.setStausCode(HttpStatus.CONFLICT.value());
		return new ResponseEntity<>(res,HttpStatus.CONFLICT);
	}
}
