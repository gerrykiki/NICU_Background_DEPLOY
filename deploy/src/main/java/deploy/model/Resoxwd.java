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
		@NamedStoredProcedureQuery(name = "RESOXWD", procedureName = "RESOXWD", resultClasses = { Resoxwd.class }) })
public class Resoxwd implements Serializable {

}
