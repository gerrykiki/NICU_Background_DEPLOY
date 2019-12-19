package deploy.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
//病人重要注意事項
@Table
@Data
@AllArgsConstructor
public class PatientImportantMatter {
	@PrimaryKey
	private String transinid;
	private Date time;
	private String hisid;
	private String context;
	private String editor;
}
