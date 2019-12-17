package deploy.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table
@Data
@AllArgsConstructor
public class User {
	
	@PrimaryKey
	@Column
	private String username;
	@Column
	@JsonIgnore
	private String password;
	@Column
	private int role;
	@Column
	private String name;

}
