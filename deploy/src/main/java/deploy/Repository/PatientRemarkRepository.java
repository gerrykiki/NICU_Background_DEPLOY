package deploy.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import deploy.model.PatientRemark;

@Repository
public interface PatientRemarkRepository extends CrudRepository<PatientRemark,String>{

}
