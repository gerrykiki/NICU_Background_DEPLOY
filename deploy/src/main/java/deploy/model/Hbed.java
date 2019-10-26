package deploy.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

import com.ibm.db2.cmx.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "HBED")
@Data
@AllArgsConstructor
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "HBED", procedureName = "HBED", resultClasses = { Hbed.class }) })
public class Hbed implements Serializable {

}
