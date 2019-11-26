package deploy.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table
@Data
@AllArgsConstructor
public class Hbed {
	private String HBNURSTA;
	private String  HBEDNO;
	private String  PCASENO;
	@PrimaryKey
	private String PHISTNUM;
	private String PNAMEC;
	private String PSEX;
}
