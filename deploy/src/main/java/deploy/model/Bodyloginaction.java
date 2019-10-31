package deploy.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bodyloginaction {
	private String loginCheck;
	private String login_name;
	private String password;
	private String fromAjax;
}
