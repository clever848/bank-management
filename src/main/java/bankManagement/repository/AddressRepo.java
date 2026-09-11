package bankManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bankManagement.entity.Address;

public interface AddressRepo extends JpaRepository<Address,Integer>{
	public List<Address> findByCity(String city);
	public List<Address> findByCityAndStreet(String city,String street);
	public Optional<Address> findByBankId(int id);
}

