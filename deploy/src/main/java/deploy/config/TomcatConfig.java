package deploy.config;

import java.sql.DriverManager;

import java.sql.Statement;
import java.sql.Types;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;

import com.ibm.db2.jcc.am.CallableStatement;
import com.ibm.db2.jcc.am.Connection;
import com.ibm.db2.jcc.am.ResultSet;

@Configuration
public class TomcatConfig {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	// @Bean
	public TomcatServletWebServerFactory servletContainer() {
		return new TomcatServletWebServerFactory() {
			@Override
			protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {

				tomcat.enableNaming();
				logger.info("Enter getTomcatWebServer");
				return super.getTomcatWebServer(tomcat);
			}

			@Override
			protected void postProcessContext(Context context) {
				ContextResource resource = new ContextResource();
				// Connect DB2 with JNDI
				resource.setType(DataSource.class.getName());
				resource.setName("db2");
				resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
				resource.setProperty("driverClassName", "com.ibm.db2.jcc.DB2Driver");
				resource.setProperty("url",
						"jdbc:db2://dbconnt.vghtpe.gov.tw:50000/VGHDBT:emulateParameterMetaDataForZCalls=1;");
				resource.setProperty("username", "XVGH96");
				resource.setProperty("password", "nicuteam");

				context.getNamingResources().addResource(resource);

				logger.info("Enter Connect DB2 with JNDI");
			}
		};
	}

	// @Bean // JNDI Configuration
	public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:/comp/env/db2");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.afterPropertiesSet();

		logger.info("Enter JNDI Config");

		return (DataSource) bean.getObject();
	}

}
