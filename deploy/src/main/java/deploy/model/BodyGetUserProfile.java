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
	private Map<String, Object> APP_USER_BASIC_PROFILE;
}
