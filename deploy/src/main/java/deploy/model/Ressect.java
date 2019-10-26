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
@NamedStoredProcedureQueries({ @NamedStoredProcedureQuery(name = "RESSECT", procedureName = "RESSECT", resultClasses = {
		Ressect.class }, parameters = {
				@StoredProcedureParameter(name = "THISNO", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "TDEPT", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "TMONTH", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "TSIGNID", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "RET", type = Integer.class, mode = ParameterMode.OUT),
				@StoredProcedureParameter(name = "RETMSG", type = String.class, mode = ParameterMode.OUT) }) })
public class Ressect implements Serializable {

	private String ORDSEQNO;
	private String ORPROCED;
	private String ORSPCN1;
	private String ORBGNDT;
	private String ORBGNTM;
	private Integer ORREQNO;
	private String ORFRPDT;
	private String ORFRPTM;
	private String ORENTRY;
	private String ORMM;
	private String ORHISTNO;
	private String ORDSEQCN;
	private String ORRCPDT;
	private String ORRCPTM;

}
