package bankManagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bankManagement.dto.Response;
import bankManagement.entity.Address;
import bankManagement.entity.Bank;
import bankManagement.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
	@Autowired
	AddressService addressService;
	@GetMapping("/{id}")
	public ResponseEntity<Response<Address>> getAddressById(@PathVariable int id)
	{
		return new ResponseEntity<>(addressService.getAddressById(id),HttpStatus.OK);
	}
	@GetMapping("/city/{city}")
	public ResponseEntity<Response<List<Address>>> getAddressByCity(@PathVariable String city)
	{
		return new ResponseEntity<>(addressService.getAddressByCity(city),HttpStatus.OK);
	}
	@GetMapping("/bank/{id}")
	public ResponseEntity<Response<Address>> getAddressByBankId(@PathVariable int id)
	{
		return new ResponseEntity<>(addressService.getAddressByBankId(id),HttpStatus.OK);
	}
	@PatchMapping("/{id}")
	public ResponseEntity<Response<Address>> partialUpdateAddress(@PathVariable int id,
			@RequestBody Map<String,Object> m)
	{
		return new ResponseEntity<>(addressService.updateAddress(id,m),HttpStatus.OK);
	}
}
