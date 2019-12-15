package deploy.model;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

//排程
@Table
@Data
@AllArgsConstructor
public class Schedule {
	
	@PrimaryKey 
	private String transinno;
	private Date Time;
	private int bednumber;
	private String Context;
	private String type;
}
