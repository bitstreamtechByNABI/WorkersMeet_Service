package nabi.comworker.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nabi.comworker.dto.AddressDTO;
import nabi.comworker.model.Address;
import nabi.comworker.model.Employee;
import nabi.comworker.registration.repository.AddressRepository;
import nabi.comworker.registration.repository.EmployeeRepository;
import nabi.comworker.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	  private static final Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);

	  @Autowired
	  private AddressRepository addressRepository;

	  
	  @Autowired
	  private EmployeeRepository employeeRepository;

	  @Override
	  public Long saveAddress(AddressDTO dto) {
	      Address address = new Address();
	      address.setAddress(dto.getAddress());
	      address.setPoliceStation(dto.getPoliceStation());
	      address.setPostOffice(dto.getPostOffice());
	      address.setDistrict(dto.getDistrict());
	      address.setState(dto.getState());
	      address.setPinCode(dto.getPinCode());

	      if (dto.getEmployeeId() != null) {
	          Employee employee = employeeRepository.findById(dto.getEmployeeId())
	              .orElseThrow(() -> new RuntimeException("Employee not found"));
	          address.setEmployee(employee);
	      }

	      Address saved = addressRepository.save(address);
	      return saved.getId();
	  }



}
