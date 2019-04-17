package wi.nicu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wi.nicu.model.Employee;
import wi.nicu.repository.EmployeeRepository;

@RestController //RESTful Web Service's controller
public class EmployeeController {
	@Autowired //Auto dependency injection
	EmployeeRepository employeeRepository;

	@GetMapping("/employees") //Annotation for mapping HTTP GET requests onto specific handler methods
	public List<Employee> getEmployees() {
		Iterable<Employee> result = employeeRepository.findAll(); //Return all data
		List<Employee> employeesList = new ArrayList<Employee>();
		result.forEach(employeesList::add);
		return employeesList;
	}

	@GetMapping("/employee/{id}") 
	public Optional<Employee> getEmployee(@PathVariable String id) { //Method parameter should be bound to a URI template variable
		Optional<Employee> emp = employeeRepository.findById(id);	//Find data by ID
		return emp;
	}

	@PutMapping("/employee/{id}") 
	public Optional<Employee> updateEmployee(@RequestBody Employee newEmployee, @PathVariable String id) { //Method parameter should be bound to the body of the web request
		Optional<Employee> optionalEmp = employeeRepository.findById(id);
		if (optionalEmp.isPresent()) { //If there is a value present, return true
			Employee emp = optionalEmp.get();
			emp.setFirstName(newEmployee.getFirstName());
			emp.setLastName(newEmployee.getLastName());
			emp.setEmail(newEmployee.getEmail());
			employeeRepository.save(emp); //Update data
		}
		return optionalEmp;
	}

	@DeleteMapping(value = "/employee/{id}", produces = "application/json;charset=utf-8") 
	public String deleteEmployee(@PathVariable String id) {
		Boolean result = employeeRepository.existsById(id);	//Whether an entity with the given id exists
		employeeRepository.deleteById(id);	//Delete data by ID
		return "{ \"success\" : " + (result ? "true" : "false") + " }";
	}

	@PostMapping("/employee") 
	public Employee addEmployee(@RequestBody Employee newEmployee) {
		// String id = String.valueOf(new Random().nextInt());
		Employee emp = new Employee(newEmployee.getId(), newEmployee.getFirstName(), newEmployee.getLastName(),
				newEmployee.getEmail());
		employeeRepository.save(emp);	//Create data
		return emp;
	}
}
