package deploy.model;



import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
//To Do List
import java.util.Date;
//待辦事項
@Table
@Data
@AllArgsConstructor
public class ToDo {
	@PrimaryKey
	private String transinno;
	private String type;
	private String caseid;
	private String context;
	private Date time;
}
