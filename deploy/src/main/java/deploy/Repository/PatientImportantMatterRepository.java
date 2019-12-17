package deploy.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import deploy.model.PatientImportantMatter;

@Repository
public interface PatientImportantMatterRepository extends CrudRepository<PatientImportantMatter,Date>{
	@AllowFiltering
	List<PatientImportantMatter> findByHisid(String hisid);
}
