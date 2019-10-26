package deploy.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import com.ibm.db2.cmx.annotation.Column;
import com.ibm.db2.cmx.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "ADMDATA")
@Data
@AllArgsConstructor
@NamedStoredProcedureQueries({ @NamedStoredProcedureQuery(name = "ADMDISP", procedureName = "ADMDISP", resultClasses = {
		Admdisp.class }, parameters = {
				@StoredProcedureParameter(name = "THISNO", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "TCASENO", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "RET", type = Integer.class, mode = ParameterMode.OUT),
				@StoredProcedureParameter(name = "RETMSG", type = String.class, mode = ParameterMode.OUT) }) })
public class Admdisp implements Serializable {
	@Column(name = "ADMDATA")
	private String ADMDATA;
}
