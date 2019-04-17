package wi.nicu.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data //Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@AllArgsConstructor //Generates an all-args constructor. An all-args constructor requires one argument for every field in the class
@Table //Mapping Database's table
public class Employee {
	@PrimaryKey
	private @NonNull String id;
	private @NonNull String firstName;
	private @NonNull String lastName;
	private @NonNull String email;
}
