package deploy.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BodyGetUuid {
	private String PRIVILEGED_APP_SSO_TOKEN;
	private String PUBLIC_APP_USER_SSO_TOKEN_TO_QUERY;
}
