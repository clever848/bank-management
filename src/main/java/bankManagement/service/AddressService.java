package bankManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bankManagement.dto.Response;
import bankManagement.entity.Address;
import bankManagement.exception.InvalidException;
import bankManagement.exception.NotFoundException;
import bankManagement.repository.AddressRepo;

@Service
public class AddressService {
	@Autowired
	AddressRepo addRepo;
	//get address by id
	public Response<Address> getAddressById(int id)
	{
		Address address = addRepo.findById(id).orElseThrow(()->
		new NotFoundException("not address with id : "+id));
		Response<Address> res = new Response<Address>();
		return res.fetchResponse(address);
	}
	//get address by city
	public Response<List<Address>> getAddressByCity(String city)
	{
		List<Address> l = addRepo.findByCity(city);
		if(l.size()==0)
			throw new NotFoundException("no address found in city : "+city);
		Response<List<Address>> res = new Response<List<Address>>();
		return res.fetchResponse(l);
	}
	//find address by city and street
	public Response<List<Address>> getAddressByCityAndStreet(String city,String street)
	{
		List<Address> l = addRepo.findByCityAndStreet(city, street);
		if(l.size()==0)
			throw new NotFoundException("no address");
		Response<List<Address>> res = new Response<List<Address>>();
		return res.fetchResponse(l);
	}
	//find address by bank id
	public Response<Address> getAddressByBankId(int id)
	{
		Address address = addRepo.findByBankId(id).orElseThrow(()->
		new NotFoundException("no address found with bank id "+id));
		Response<Address> res = new Response<Address>();
		return res.fetchResponse(address);
	}
	//update address
	public Response<Address> updateAddress(int id,Map<String,Object> m)
	{
		Address address = addRepo.findById(id).orElseThrow(()->
		new NotFoundException("no address with id : "+id));
		for(Map.Entry<String,Object> e:m.entrySet())
		{
			switch (e.getKey()) {
				case "street":
					address.setStreet((String)e.getValue());
					break;
				case "city":
					address.setCity((String)e.getValue());
					break;
				case "pinCode":
					String val =e.getValue()+"";
					if(val.length()!=6) throw new InvalidException("pincode must be 6 dig");
					address.setPinCode(Integer.parseInt(val));
					break;
			}
		}
		Response<Address> res = new Response<Address>();
		return res.updateResponse(addRepo.save(address));
	}
	
}
