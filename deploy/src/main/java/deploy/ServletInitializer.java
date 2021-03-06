package deploy;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import deploy.tcp.SocketTest;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		Thread th = new SocketTest();
		th.start();
		return application.sources(DeployApplication.class);
	}

}
