package deploy.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import deploy.model.History;

@Repository
public interface HistoryRepository extends CrudRepository<History, String> {

}
