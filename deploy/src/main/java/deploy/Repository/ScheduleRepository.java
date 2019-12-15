package deploy.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import deploy.model.Schedule;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, String> {
	
}
	
