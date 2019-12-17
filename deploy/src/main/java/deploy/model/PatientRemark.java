package deploy.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

//病人註記
@Table
@Data
@AllArgsConstructor
public class PatientRemark {
	@PrimaryKey
	private String transid;
	private String hisid;
	private String Context;
}
