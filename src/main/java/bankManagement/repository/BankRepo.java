package bankManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bankManagement.entity.Bank;

public interface BankRepo extends JpaRepository<Bank,Integer>{
	
	public Boolean existsByIfscCode(String ifscCode);
	public Boolean existsByContactNo(Long contactNo);
	public Optional<Bank> findByIfscCode(String ifsc);
	public Optional<Bank> findByAddressAddId(int id);
	public List<Bank> findByAddressCity(String city);
	public Optional<Bank> findByContactNo(Long contactNo);
}
