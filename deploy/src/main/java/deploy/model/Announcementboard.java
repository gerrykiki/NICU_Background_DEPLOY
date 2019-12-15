package deploy.model;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Table("Announcementboard")
@AllArgsConstructor
public class Announcementboard {

	@PrimaryKey 
	private String transinno;
	private Date Time;
	private String Context;
	private String Editor;

}
