package bankManagement.dto;

import org.springframework.http.HttpStatus;

import bankManagement.entity.Bank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T>{
	private String message;
	private T data;
	private Integer stausCode;	
	
	//fetch response
	public Response<T> fetchResponse(T data)
	{
		Response<T> res = new Response<T>();
		res.setData(data);
		res.setMessage("fetched successfully");
		res.setStausCode(HttpStatus.OK.value());
		return res;
	}
	//save response
	public Response<T> saveResponse(T data)
	{
		Response<T> res = new Response<T>();
		res.setData(data);
		res.setMessage("saved successfully");
		res.setStausCode(HttpStatus.CREATED.value());
		return res;
	}
	//deleted response
	public Response<T> deleteResponse()
	{
		Response<T> res = new Response<T>();
		res.setData(null);
		res.setMessage("deleted successfully");
		res.setStausCode(HttpStatus.OK.value());
		return res;
	}
	//update response
	public Response<T> updateResponse(T data)
	{
		Response<T> res = new Response<T>();
		res.setData(data);
		res.setMessage("updated successfully");
		res.setStausCode(HttpStatus.OK.value());
		return res;
	}
	//transaction response
	public Response<T> transactionResponse(T data)
	{
		Response<T> res = new Response<T>();
		res.setData(data);
		res.setMessage("transaction successfully");
		res.setStausCode(HttpStatus.OK.value());
		return res;
	}
}
