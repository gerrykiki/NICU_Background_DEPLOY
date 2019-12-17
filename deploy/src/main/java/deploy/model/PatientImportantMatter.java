package deploy.model;



import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
//病人重要注意事項
import java.util.Date;
//病人重要注意事項
@Table
@Data
@AllArgsConstructor
public class PatientImportantMatter {
	@PrimaryKey
	private Date UpdateTime;
	private String hisid;
	private String Context;
	private String Editor;
}
