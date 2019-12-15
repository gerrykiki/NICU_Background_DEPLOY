package deploy.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import deploy.model.Patient;
@Repository
public interface PatientRepository extends CrudRepository<Patient,String>{

}
