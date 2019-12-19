package deploy.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import deploy.model.PatientImportantMatter;

@Repository
public interface PatientImportantMatterRepository extends CrudRepository<PatientImportantMatter,String>{

}
