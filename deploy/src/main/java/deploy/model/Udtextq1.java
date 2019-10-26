package deploy.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
//@Table(name = "STAFF")
@Data
@AllArgsConstructor
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "UDTEXTQ1", procedureName = "UDTEXTQ1", resultClasses = { Udtextq1.class }) })
public class Udtextq1 implements Serializable {

}
