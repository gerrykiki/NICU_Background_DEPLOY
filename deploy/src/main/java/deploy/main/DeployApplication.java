package deploy.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import deploy.tcp.SocketTest;

import java.sql.*;

@ComponentScan("deploy")
@SpringBootApplication
public class DeployApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeployApplication.class, args);
		//Thread t = new SocketTest();
		//t.start();
    }

}
