package deploy.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import deploy.model.LoginLog;
@Repository
public interface LoginlogRepository extends CrudRepository<LoginLog,String>{

}
