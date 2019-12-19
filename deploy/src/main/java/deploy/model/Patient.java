package deploy.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table("patientdata")
@Data
@AllArgsConstructor
public class Patient {
	
	@PrimaryKey
	private String caseid;
	private String hisid;
	private String pnamec;	
	private String psex;
	private String transindays;
	private String transintime;
	private String transinid;
}
