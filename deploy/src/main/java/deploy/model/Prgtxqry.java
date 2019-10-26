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
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "PRGTXQRY", procedureName = "PRGTXQRY", resultClasses = {
				Prgtxqry.class }, parameters = {
						@StoredProcedureParameter(name = "PRGPART", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "HISNO", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "CASENO", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "RET", type = Integer.class, mode = ParameterMode.OUT),
						@StoredProcedureParameter(name = "RETMSG", type = String.class, mode = ParameterMode.OUT) }) })
public class Prgtxqry implements Serializable {
	
	private String PRGPART;    
	private String HISTNO;
	private String CASENO;     
	private String PRGDT;     
	private String PRGTM;    
	private String PRGSECT;    
	private String PRGWARD;   
	private String PRGBEDNO;  
	private String PRGENTNM; 
	private String PRGTYPE;   
	private String PRGTX;       
	
}
