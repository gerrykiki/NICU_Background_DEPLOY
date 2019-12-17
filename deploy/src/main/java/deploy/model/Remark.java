package deploy.model;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table
@Data
@AllArgsConstructor
public class Remark {
	 @PrimaryKey
	 private String transid;
	 private String type;
	 private Date time;
	 private String name;
	 private String remark;
}
