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
		@NamedStoredProcedureQuery(name = "TRTMNT01", procedureName = "TRTMNT01", resultClasses = { Trtmnt01.class }) })
public class Trtmnt01 implements Serializable {

}
