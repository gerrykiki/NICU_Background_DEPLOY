package deploy.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import deploy.model.Notice;

@Repository
public interface NoticeRepository extends CrudRepository<Notice,String>{

}
