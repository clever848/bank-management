package bankManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bankManagement.dto.AccountType;
import bankManagement.entity.Account;

public interface AccountRepo extends JpaRepository<Account,Integer>{

	public Boolean existsByAccNoAndBankId(long accNo,int id);
	public Boolean existsByAccIdAndBankId(int accId,int bankId);
	public List<Account> findByBankId(int id);
	public List<Account> findByAccType(AccountType accType);
	public List<Account> findByBalGreaterThan(double price);
}
