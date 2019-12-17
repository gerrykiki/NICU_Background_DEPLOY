package deploy.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import deploy.model.ToDo;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo,String>{
	
}
