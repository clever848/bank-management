package bankManagement.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import bankManagement.dto.Response;
import bankManagement.entity.Bank;
import bankManagement.exception.InvalidException;
import bankManagement.exception.NonUniqueException;
import bankManagement.exception.NotFoundException;
import bankManagement.exception.NullException;
import bankManagement.repository.BankRepo;
@Service
public class BankService {
	@Autowired
	BankRepo bankRepo;
	//insert bank
	public Response<Bank> saveBank(Bank bank)
	{
		validateBank(bank); // validate bank details
		validateNewBank(bank);
		bank.getAddress().setBank(bank);
		Bank savedBank = bankRepo.save(bank);
		Response<Bank> res = new Response<Bank>();
		return res.saveResponse(savedBank);
	}
	//validate bank
	public void validateBank(Bank bank)
	{
		if(bank.getContactNo() == null)
		{
			throw new NullException("bank must need contact number");
		}
		else if(bank.getIfscCode() == null){
			throw new NullException("bank must need IFSC code");
		}
		else if (bank.getAddress() == null) {
			throw new NullException("address can't be empty");
		}
		else if(bank.getAddress().getPinCode() == null)
		{
			throw new NullException("address must need pincode");
		}
		String contactNo = bank.getContactNo()+"";
		String pinCode = bank.getAddress().getPinCode()+"";
		if(contactNo.length()!=10)
		{
			throw new InvalidException("invalid contact number ,"
					+ "contact number must be 10 digit only");
		}
		else if (pinCode.length() !=6) {
			throw new InvalidException("invalid pincode , pincode must be 6 digit");
		}
	}
	public void validateNewBank(Bank bank)
	{
		if (bankRepo.existsByIfscCode(bank.getIfscCode())) {
			throw new NonUniqueException("ifsc code already exists");
		}
		else if(bankRepo.existsByContactNo(bank.getContactNo()))
		{
			throw new NonUniqueException("contact number already exists");
		}
	}
	//get bank by id
	public Response<Bank> getBankById(int id)
	{
		Bank bank = bankRepo.findById(id).orElseThrow(()->
		new NotFoundException("no bank found with id:"+id));
		Response<Bank> res = new Response<Bank>();
		return res.fetchResponse(bank);
	}
	//validate bank
	//get all banks
	public Response<List<Bank>> getAllBank()
	{
		List<Bank> banks = bankRepo.findAll();
		if(banks.size()>0)
		{
			Response<List<Bank>> res = new Response<List<Bank>>();
			return res.fetchResponse(banks);
		}
		throw new NotFoundException("no banks found");
	}
	//get bank by pagination and sorting
	public Response<Page<Bank>> getBankByPaginationAndSorting(int pgNo,
			int pgSize,String fieldName,String sort)
	{
		Page<Bank> page;
		if(sort.equals("des"))
		{
			page = bankRepo.findAll(PageRequest.of(pgNo, pgSize,Sort.by(fieldName).descending()));	
		}
		else {
			page = bankRepo.findAll(PageRequest.of(pgNo, pgSize,Sort.by(fieldName).ascending()));
		}
		if(page.getSize()==0)
		{
			throw new NotFoundException("no banks found");
		}
		Response<Page<Bank>> res = new Response<Page<Bank>>();
		return res.fetchResponse(page);
	}	
	//get bank by ifsc code
	public Response<Bank> getBankByIfscCode(String ifscCode)
	{
		Bank bank = bankRepo.findByIfscCode(ifscCode).orElseThrow(()->new
				NotFoundException("no bank found with ifsc code : "+ifscCode));
		Response<Bank> res = new Response<Bank>();
		return res.fetchResponse(bank);
	}
	//get bank by address id
	public Response<Bank> getBankByAddressId(int id)
	{
		Bank bank = bankRepo.findByAddressAddId(id).orElseThrow(()->
		new NotFoundException("no bank found with address id : "+id));
		Response<Bank> res = new Response<Bank>();
		return res.fetchResponse(bank);
	}
	//get bank by city
	public Response<List<Bank>> getBanksByCity(String city)
	{
		List<Bank> l = bankRepo.findByAddressCity(city);
		if(l.size()>0)
		{
			Response<List<Bank>> res = new Response<List<Bank>>();
			return res.fetchResponse(l);
		}
		throw new NotFoundException("no bank found in city "+city);
	}
	//get bank by contact number
	public Response<Bank> getBankByContactNo(long contactNo)
	{
		Bank bank = bankRepo.findByContactNo(contactNo).orElseThrow(()->
		new NotFoundException("no bank found contact : "+contactNo));
		Response<Bank> res = new Response<Bank>();
		return res.fetchResponse(bank);
	}
	//delete bank by id
	public Response<Bank> deleteBankById(int id)
	{
		Bank bank = bankRepo.findById(id).orElseThrow(()->
		new NotFoundException("no bank found with the id "+id));
		bankRepo.deleteById(id);
		Response<Bank> res = new Response<Bank>();
		return res.deleteResponse();
	}
	//update bank by id
	public Response<Bank> fullyUpdateBankById(int id,Bank bank)
	{
		Bank fetchedBank = bankRepo.findById(id).orElseThrow(()->
		new NotFoundException("bank with id:"+id+" not found"));
		validateBank(bank);
		validateUpdateBank(bank,fetchedBank);
		Response<Bank> res = new Response<Bank>();
		bank.setId(id);
		bank.getAddress().setAddId(fetchedBank.getAddress().getAddId());
		return res.fetchResponse(bankRepo.save(bank));
	}
	//validate before updating
	public void validateUpdateBank(Bank bank,Bank fetchedBank)
	{
		if(!bank.getIfscCode().equals(fetchedBank.getIfscCode()) &&
			bankRepo.existsByIfscCode(bank.getIfscCode()))
		{
			throw new NonUniqueException("ifsc code already exists! ");
		}
		if(!bank.getContactNo().equals(fetchedBank.getContactNo()) &&
				bankRepo.existsByContactNo(bank.getContactNo()))
		{
			System.out.println(bank.getContactNo());
			System.out.println(fetchedBank.getContactNo());
			throw new NonUniqueException("contact number is already exists");
		}
	}
}
