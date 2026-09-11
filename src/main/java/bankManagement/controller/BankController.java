package bankManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bankManagement.dto.Response;
import bankManagement.entity.Bank;
import bankManagement.repository.BankRepo;
import bankManagement.service.BankService;

@RestController
@RequestMapping("/bank")
public class BankController {
	@Autowired
	BankRepo bankRepo;
	@Autowired
	BankService bankService;
	@PostMapping
	public ResponseEntity<Response<Bank>> createBank(@RequestBody Bank bank)
	{
		return new ResponseEntity<>(bankService.saveBank(bank),HttpStatus.CREATED);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Response<Bank>> getBankById(@PathVariable int id)
	{
		return new ResponseEntity<>(bankService.getBankById(id),HttpStatus.OK);
	}
	@GetMapping("/all")
	public ResponseEntity<Response<List<Bank>>> getAllBanks()
	{
		return new ResponseEntity<>(bankService.getAllBank(),HttpStatus.OK);
	}
	@GetMapping("/pagination/{pgNo}/{pgSize}/{fieldName}/{sort}")
	public ResponseEntity<Response<Page<Bank>>> getByPaginationAndSorting(
			@PathVariable int pgNo,@PathVariable int pgSize,@PathVariable String fieldName,
			@PathVariable String sort)
	{
		return new ResponseEntity<>(bankService.getBankByPaginationAndSorting(pgNo, pgSize, fieldName, sort),HttpStatus.OK);
	}
	@GetMapping("/ifsc/{ifscCode}")
	public ResponseEntity<Response<Bank>> getBankByIfscCode(@PathVariable String ifscCode)
	{
		return new ResponseEntity<>(bankService.getBankByIfscCode(ifscCode),HttpStatus.OK);
	}
	@GetMapping("/address/{id}")
	public ResponseEntity<Response<Bank>> getBankByAddressId(@PathVariable int id)
	{
		return new ResponseEntity<>(bankService.getBankByAddressId(id),HttpStatus.OK);
	}
	@GetMapping("/address/city/{city}")
	public ResponseEntity<Response<List<Bank>>> getBankByCity(@PathVariable String city)
	{
		return new ResponseEntity<>(bankService.getBanksByCity(city),HttpStatus.OK);
	}
	@GetMapping("/contact/{contactNo}")
	public ResponseEntity<Response<Bank>> getBankByContactNo(long contactNo)
	{
		return new ResponseEntity<>(bankService.getBankByContactNo(contactNo),HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Bank>> deleteBankById(@PathVariable int  id)
	{
		return new ResponseEntity<>(bankService.deleteBankById(id),HttpStatus.OK);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Response<Bank>> fullyUpdateBankById(@PathVariable int id,@RequestBody Bank bank)
	{
		return new ResponseEntity<>(bankService.fullyUpdateBankById(id,bank),HttpStatus.OK);
	}
	
}
