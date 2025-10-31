package nabi.comworker.dto;

import nabi.comworker.model.Employee;

public final class EmployeeMapper {

	  private EmployeeMapper() {}

	    public static Employee toEntity(EmployeeDTO dto) {
	        if (dto == null) return null;

	        Employee employee = new Employee();
	        employee.setId(dto.getId());  // <-- add this if you want to update existing entity (optional)
	        employee.setCandidateName(dto.getCandidateName());
	        employee.setMotherName(dto.getMotherName());
	        employee.setFatherName(dto.getFatherName());
	        employee.setDateOfBirth(dto.getDateOfBirth());  
	        employee.setMobileNumber(dto.getMobileNumber());
	        employee.setEmail(dto.getEmail());
	        return employee;
	    }

	    public static EmployeeDTO toDTO(Employee employee) {
	        if (employee == null) return null;

	        EmployeeDTO dto = new EmployeeDTO();
	        dto.setId(employee.getId());  // <-- map the generated ID here
	        dto.setCandidateName(employee.getCandidateName());
	        dto.setMotherName(employee.getMotherName());
	        dto.setFatherName(employee.getFatherName());
	        dto.setDateOfBirth(employee.getDateOfBirth()); 
	        dto.setMobileNumber(employee.getMobileNumber());
	        dto.setEmail(employee.getEmail());
	        dto.setCreatedDate(employee.getCreatedDate());
	        dto.setUpdatedDate(employee.getUpdatedDate());
	        return dto;
	    }
}
