package deploy.model;

import lombok.AllArgsConstructor;
import lombok.Data;

// SSO request basic authentication
@Data
@AllArgsConstructor
public class BodyAuth {
	private String APP_PRIVATE_ID;
	private String APP_PRIVATE_PASSWD;
}
