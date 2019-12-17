package deploy.model;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

//待辦事項
@Table
@Data
@AllArgsConstructor
public class Notice {
	@PrimaryKey
	private String transinno;
	private Date time;
	private String caseid;
	private String context;	
}
