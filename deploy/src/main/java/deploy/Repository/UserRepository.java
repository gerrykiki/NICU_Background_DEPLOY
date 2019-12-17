package deploy.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import deploy.model.User;


@Repository
public interface UserRepository extends CrudRepository<User,String>{

}
