package deploy.model;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table
@Data
@AllArgsConstructor
public class LoginLog {
	@PrimaryKey
	private String uuid;
	private Date time;
	private String username;
	private String name;
	private Integer role;

}
