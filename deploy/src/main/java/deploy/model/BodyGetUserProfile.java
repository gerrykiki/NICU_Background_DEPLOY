package deploy.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
//SSO Get User Profile
@Data
@AllArgsConstructor
public class BodyGetUserProfile {
	private String PRIVILEGED_APP_SSO_TOKEN;
	private String PUBLIC_APP_USER_SSO_TOKEN_TO_QUERY;
	private String APP_COMPANY_UUID;
	private String APP_USER_NODE_UUID;
	private Map<String, Object> APP_USER_BASIC_PROFILE;
}
