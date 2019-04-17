package wi.nicu.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import wi.nicu.repository.EmployeeRepository;

@ComponentScan("wi.nicu") //fetch available source
@EnableCassandraRepositories(basePackageClasses = EmployeeRepository.class) //fetch repository class
@SpringBootApplication
public class NicuApplication {

	public static void main(String[] args) {
		SpringApplication.run(NicuApplication.class, args);
	}

}
