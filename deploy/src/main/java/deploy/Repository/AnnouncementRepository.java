package deploy.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import deploy.model.*;

@Repository
public interface AnnouncementRepository extends CrudRepository<Announcementboard, String> {

}
