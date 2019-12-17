package deploy.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import deploy.model.Remark;
@Repository
public interface RemarkRepository extends CrudRepository<Remark,String>{

}
