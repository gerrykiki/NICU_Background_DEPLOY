package deploy.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

//import com.ibm.db2.cmx.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
//@Table(name = "PSECTION")
@Data
@AllArgsConstructor
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "PSECTION", procedureName = "PSECTION", resultClasses = { Psection.class }) })
public class Psection implements Serializable {

}
