package wi.nicu.repository;

import org.springframework.data.repository.CrudRepository;

import wi.nicu.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

}
//Provide default implementations of actions possible with database