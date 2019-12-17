package deploy.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import deploy.model.CenterMonitor;

@Repository
public interface CenterMonitorRepository extends CrudRepository<CenterMonitor,String>{
	
}
