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
@NamedStoredProcedureQueries({ @NamedStoredProcedureQuery(name = "RESDISP", procedureName = "RESDISP", resultClasses = {
		Resdisp.class }, parameters = {
				@StoredProcedureParameter(name = "TPARTNO", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "THISTNO", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "TCASENO", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "TORDSEQ", type = Integer.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "RET", type = Integer.class, mode = ParameterMode.OUT) }) })
public class Resdisp implements Serializable {

	private String PARTNO;
	private String HISTNO;
	private String CASENO;
	private String ORDSEQ;
	private Integer RECNO;
	private String ORBGNDT;
	private String CONTENT;
}
