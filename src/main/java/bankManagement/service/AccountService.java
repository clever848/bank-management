package bankManagement.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import bankManagement.dto.AccountType;
import bankManagement.dto.Response;
import bankManagement.entity.Account;
import bankManagement.entity.Address;
import bankManagement.entity.Bank;
import bankManagement.exception.IllegalTransaction;
import bankManagement.exception.InvalidException;
import bankManagement.exception.NonUniqueException;
import bankManagement.exception.NotFoundException;
import bankManagement.exception.NullException;
import bankManagement.repository.AccountRepo;
import bankManagement.repository.BankRepo;

@Service
public class AccountService {
	@Autowired
	AccountRepo accRepo;
	@Autowired
	BankRepo bankRepo;
	//create account
	public Response<Account> createAccount(int id,Account acc)
	{
		Bank bank = bankRepo.findById(id).orElseThrow(()->
		new NotFoundException("no bank found with the id : "+id));
		validateAccount(acc,bank);
		bank.getAccounts().add(acc);
		acc.setBank(bank);
		Account savedAccount = accRepo.save(acc);
		Response<Account> res = new Response<Account>();
		return res.saveResponse(savedAccount);
	}
	public void validateAccount(Account acc,Bank bank)
	{
		if(acc.getAccNo() == null) 
			throw new NullException("account must need account number");
		if(accRepo.existsByAccNoAndBankId(acc.getAccNo(),bank.getId()))
			throw new NonUniqueException("account number already exists");
		if(acc.getBal() == null)
			throw new NullException("account need some balance");
		if(acc.getBal()<0)
			throw new InvalidException("balance must be +ve");
		if(acc.getBal()<=999)
			throw new InvalidException("account need minimum balance of 1000");
	}
	public Response<Account> getAccountById(int id)
	{
		Account account = accRepo.findById(id).orElseThrow(()->
		new NotFoundException("no account found with id : "+id));
		Response<Account> res = new Response<Account>();
		return res.fetchResponse(account);
	}
	public Response<List<Account>> getAllAccount()
	{
		List<Account> l = accRepo.findAll();
		if(l.size()>0)
		{
			Response<List<Account>> res = new Response<List<Account>>();
			return res.fetchResponse(l);
		}
		throw new NotFoundException("no account found");
	}
	//delete account by id
	public Response<String> deleteAccountById(int id)
	{
		Account acc = accRepo.findById(id).orElseThrow(()->
		new NotFoundException("no account found with id : "+id));
		accRepo.deleteById(id);
		Response<String> res = new Response<String>();
		return res.deleteResponse();
	}
	//update account holder name and account type
	public Response<Account> updateHolderNameAndAccountType(int accId,Map<String,Object> m)
	{
		Account account = isAccountExists(accId);
		for(Map.Entry<String,Object> e:m.entrySet())
		{
			switch (e.getKey()) {
			case "accHolderName":
				account.setAccHolderName((String)e.getValue());
				break;
			case "accType":
				account.setAccType(AccountType.valueOf((String)e.getValue()));
//			default:
//				throw new InvalidException("invalid field name");
			}
		}
		Response<Account> res = new Response<Account>();
		return res.updateResponse(accRepo.save(account));
	}
	//deposit
	public Response<Account> deposit(int accId,double amount)
	{
		Account account = accRepo.findById(accId).orElseThrow(()->
		new NotFoundException("no account found with id "+accId));
		if(amount<=0)
			throw new InvalidException("deposited amount must be positive");
		if(amount<=50)
			throw new InvalidException("depoisted amount must be greater then to 50");
		
		account.setBal(account.getBal()+amount);
		Response<Account> res = new Response<Account>();
		return res.updateResponse(accRepo.save(account));
	}
	//check is account exists
	public Account isAccountExists(int accId)
	{
		Account account = accRepo.findById(accId).orElseThrow(()->
		new NotFoundException("no account found with id "+accId));
		return account;
	}
	//transfer
	public Response<String> transfer(int accId1,int accId2,int amt)
	{
		Account acc1 = isAccountExists(accId1);
		Account acc2 = isAccountExists(accId2);
		if(acc1.getAccId() == acc2.getAccId())
			throw new IllegalTransaction("cant do self transfer");
		if(amt<=0)
			throw new IllegalTransaction("amount can't be 0 or less than 0");
		if(amt>acc1.getBal())
			throw new IllegalTransaction("insufficent balance!");
		acc1.setBal(acc1.getBal()-amt);
		acc2.setBal(acc2.getBal()+amt);
		accRepo.save(acc1);
		accRepo.save(acc2);
		Response<String> res = new Response<String>();
		return res.transactionResponse("success");
	}
	//withdraw
	public Response<Account> withdraw(int accId,double amt)
	{
//		Account acc = isAccountExists(bankId, accId);
		Account acc = isAccountExists(accId);
		if(acc.getBal()<amt)
			throw new IllegalTransaction("insufficient balance!");
		acc.setBal(acc.getBal()-amt);
		Response<Account> res = new Response<Account>();
		return res.transactionResponse(accRepo.save(acc));
	}
	//find accounts by bank id
	public Response<List<Account>> getAccountByBankId(int id)
	{
		List<Account> l = accRepo.findByBankId(id);
		if(l.size()>0)
		{
			Response<List<Account>> res = new Response<List<Account>>();
			return res.fetchResponse(l);
		}
		throw new NotFoundException("no account found with bank id "+id);
	}
	//find accounts by account type
	public Response<List<Account>> getAccountByAccType(AccountType accType)
	{
		List<Account> l = accRepo.findByAccType(accType);
		if(l.size()==0)
			throw new NotFoundException("no account found with type : "+accType);
		Response<List<Account>> res = new Response<List<Account>>();
		return res.fetchResponse(l);
	}
	//find account balance greater than
	public Response<List<Account>> getAccountGreaterThan(double bal)
	{
		List<Account> l = accRepo.findByBalGreaterThan(bal);
		if(l.size()==0)
			throw new NotFoundException("no account with balance greater than "+bal);
		Response<List<Account>> res = new Response<List<Account>>();
		return res.fetchResponse(l);
	}
	//find account based on pagination and sorting
	public Response<Page<Account>> findAccountByPaginationAndSorting(
			int pgNo,int pgSize,String fieldName)
	{
		Page<Account> page = accRepo.findAll(PageRequest.of(pgNo, pgSize,Sort.by(fieldName).ascending()));
		if(page.isEmpty()) throw new NotFoundException("no account found in the page : "+pgNo
				+" with page size : "+pgSize);
		Response<Page<Account>> res = new Response<Page<Account>>();
		return res.fetchResponse(page);
	}
	
}
