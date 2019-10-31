package deploy.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

//import com.ibm.db2.cmx.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
//@Table(name = "PLOC")
@Data
@AllArgsConstructor
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "PLOC", procedureName = "PLOC", resultClasses = { Ploc.class }) })
public class Ploc implements Serializable {

}
