package bankManagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bankManagement.dto.AccountType;
import bankManagement.dto.Response;
import bankManagement.entity.Account;
import bankManagement.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	@Autowired
	AccountService accServ;
	@PostMapping("/{bankId}")
	public ResponseEntity<Response<Account>> createAccount(@PathVariable int bankId,@RequestBody Account account)
	{
		return new ResponseEntity<>(accServ.createAccount(bankId,account),HttpStatus.CREATED);
	}
	@GetMapping("{id}")
	public ResponseEntity<Response<Account>> getAccountById(@PathVariable int id)
	{
		return new ResponseEntity<>(accServ.getAccountById(id),HttpStatus.OK);
	}
	@GetMapping("/all")
	public ResponseEntity<Response<List<Account>>> getAllAccount()
	{
		return new ResponseEntity<>(accServ.getAllAccount(),HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<String>> deleteAccountByid(@PathVariable int id)
	{
		return new ResponseEntity<>(accServ.deleteAccountById(id),HttpStatus.OK);
	}
	@PatchMapping("/{accId}")
	public ResponseEntity<Response<Account>> updateAccount(@PathVariable int accId,@RequestBody Map<String,Object> m)
	{
		return new ResponseEntity<>(accServ.updateHolderNameAndAccountType(accId, m),HttpStatus.OK);
	}
	@PatchMapping("/deposit/{accId}/{amt}")
	public ResponseEntity<Response<Account>> deposit(@PathVariable int accId,@PathVariable double amt)
	{
		return new ResponseEntity<>(accServ.deposit(accId,amt),HttpStatus.OK);
	}
	@PatchMapping("/transfer/from/{accId1}/to/{accId2}/{amt}")
	public ResponseEntity<Response<String>> transfer(@PathVariable int accId1,
			@PathVariable int accId2,@PathVariable int amt)
	{
		return new ResponseEntity<>(accServ.transfer(accId1,accId2,amt),HttpStatus.OK);
	}
	@PatchMapping("withdraw/{accId}/{amt}")
	public ResponseEntity<Response<Account>> withdrawAmount(@PathVariable int accId,@PathVariable double amt)
	{
		return new ResponseEntity<>(accServ.withdraw(accId, amt),HttpStatus.OK);
	}
	@GetMapping("/bank/{id}")
	public ResponseEntity<Response<List<Account>>> getAccountsByBankId(@PathVariable int id)
	{
		return new ResponseEntity<>(accServ.getAccountByBankId(id),HttpStatus.OK);
	}
	@GetMapping("/acctype/{accType}")
	public ResponseEntity<Response<List<Account>>> getAccountByAccType(@PathVariable AccountType accType)
	{
		return new ResponseEntity<>(accServ.getAccountByAccType(accType),HttpStatus.OK);
	}
	@GetMapping("/balGreaterThan/{bal}")
	public ResponseEntity<Response<List<Account>>> getAccountsByBalGreaterThan(
			@PathVariable double bal){
		return new ResponseEntity<>(accServ.getAccountGreaterThan(bal),HttpStatus.OK);
	}
	@GetMapping("/page/{pgNo}/{pgSize}/sort/{fieldName}")
	public ResponseEntity<Response<Page<Account>>> getAccountByPaginationAndSorting(
			@PathVariable int pgNo,@PathVariable int pgSize,
			@PathVariable String fieldName)
	{
		return new ResponseEntity<>(accServ.findAccountByPaginationAndSorting(pgNo, pgSize, fieldName),HttpStatus.OK);
	}
}
