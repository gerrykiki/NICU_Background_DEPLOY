package deploy.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
//@Table(name = "STAFF")
@Data
@AllArgsConstructor
@NamedStoredProcedureQueries({ @NamedStoredProcedureQuery(name = "DISDISP", procedureName = "DISDISP", resultClasses = {
		Disdisp.class }, parameters = {
				@StoredProcedureParameter(name = "THISNO", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "TCASENO", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "RET", type = Integer.class, mode = ParameterMode.OUT),
				@StoredProcedureParameter(name = "RETMSG", type = String.class, mode = ParameterMode.OUT) }) })
public class Disdisp implements Serializable {

	//private static final long serialVersionUID = 1L;

	private String COL1;

}
