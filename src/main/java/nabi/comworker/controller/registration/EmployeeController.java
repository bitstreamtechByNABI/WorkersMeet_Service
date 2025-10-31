package nabi.comworker.controller.registration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import nabi.comworker.dto.EmployeeDTO;
import nabi.comworker.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody EmployeeDTO employeeDTO) {
        Long employeeId = employeeService.registerEmployee(employeeDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User created successfully");
        response.put("employeeId", employeeId);

        String formattedTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        response.put("timestamp", formattedTimestamp);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
