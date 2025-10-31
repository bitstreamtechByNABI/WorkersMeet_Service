package nabi.comworker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nabi.comworker.dto.EmployeeDTO;
import nabi.comworker.dto.EmployeeMapper;
import nabi.comworker.exception.EmailAlreadyExistsException;
import nabi.comworker.model.Employee;
import nabi.comworker.registration.repository.EmployeeRepository;
import nabi.comworker.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	 @Autowired
	    private EmployeeRepository employeeRepository;

	   @Override
	    public Long registerEmployee(EmployeeDTO employeeDTO) {
	        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
	            throw new EmailAlreadyExistsException("Email already registered");
	        }

	        Employee employee = EmployeeMapper.toEntity(employeeDTO);
	        Employee saved = employeeRepository.save(employee);
	        return saved.getId();  // Return generated ID after save
	    }
}
