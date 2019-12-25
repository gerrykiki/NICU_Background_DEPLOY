package deploy.model;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table
@Data
@AllArgsConstructor
public class History {
	@PrimaryKey
	private String uuid;
	private Integer bednumber;
	private String hisid;
	private String name;
	private Integer psex;
	private Integer weight;
	private Integer weeks;
	private Integer transinage;
	private String caseid;
	private String transinid;
	private Date transintime;
	private String doctor;
}
