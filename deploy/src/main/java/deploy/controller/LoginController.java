package deploy.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import deploy.Repository.LoginlogRepository;
import deploy.model.BodyUser;
import deploy.model.LoginLog;
import deploy.model.UserDTO;
import deploy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "登入與權限設定")
@RestController
public class LoginController {

	private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
			.build();
	// private Cluster cluster =
	// Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
	// .build();
	private Session session = cluster.connect("nicuspace");

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	LoginlogRepository loginlogRepository;

	@Autowired
	private UserService userDetailsService;

	@ApiOperation("登入")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody BodyUser user) throws Exception {
		if (user.getUsername() == null || user.getPassword() == null || user.getUsername().equals("")
				|| user.getPassword().equals("")) {
			throw new UsernameNotFoundException("Username or Password can not empty !");
		}
		authenticate(user.getUsername(), user.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

		insertLoginlog(userDetails.getUsername());

		return ResponseEntity.ok(loginInfo(userDetails.getUsername()));
	}

	public List<Map<Object, Object>> loginInfo(String username) {
		StringBuilder sb = new StringBuilder("SELECT * FROM user WHERE username='").append(username)
				.append("' ALLOW FILTERING;");
		String query = sb.toString();

		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();

		ResultSet rs = session.execute(query);
		rs.forEach(r -> {
			Map<Object, Object> usr = new HashMap<Object, Object>();
			usr.put("username", r.getString("username"));
			usr.put("name", r.getString("name"));
			usr.put("role", r.getInt("role"));

			list.add(usr);
		});

		return list;
	}

	public void insertLoginlog(String username) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date now = new Date();
		String dateF = df.format(now);
		Date date = df.parse(dateF);

		UUID uuid = UUID.randomUUID();
		String indexid = uuid.toString();

		LoginLog loginlog = new LoginLog(indexid, date, username);
		loginlogRepository.save(loginlog);
	}

	@ApiOperation("新增帳號與權限")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		if (user.getUsername() == null || user.getUsername().equals("")) {
			throw new UsernameNotFoundException("Username can not empty !");
		}
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	@ApiOperation("刪除帳號")
	@RequestMapping(value = "/delUser/{name}/{role}/{username}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delUser(@Valid @PathVariable String name, @Valid @PathVariable int role,
			@Valid @PathVariable String username) {
		StringBuilder sb = new StringBuilder("DELETE FROM user WHERE name='").append(name).append("' and role=")
				.append(role).append(" and username='").append(username).append("' ;");
		String query = sb.toString();
		session.execute(query);
		return ResponseEntity.ok("");
	}

	@ApiOperation("查詢某帳號權限：1->read only 2->general user 3->general manager 4->system manager 5->super user")
	@RequestMapping(value = "/getAuth/{username}", method = RequestMethod.GET)
	public ResponseEntity<?> getAuthUser(@Valid @PathVariable String username) {
		StringBuilder sb = new StringBuilder("SELECT * FROM user WHERE username='").append(username)
				.append("' ALLOW FILTERING;");
		String query = sb.toString();

		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();

		ResultSet rs = session.execute(query);
		rs.forEach(r -> {
			Map<Object, Object> usr = new HashMap<Object, Object>();
			usr.put("username", r.getString("username"));
			usr.put("name", r.getString("name"));
			usr.put("role", r.getInt("role"));

			list.add(usr);
		});

		return ResponseEntity.ok(list);
	}

	@ApiOperation("角色、姓名搜尋其內容")
	@RequestMapping(value = "/searchUser/{name}/{role}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@Valid @PathVariable String name, @Valid @PathVariable Integer role) {
		StringBuilder sb = new StringBuilder("SELECT * FROM user WHERE name='").append(name).append("' and role=")
				.append(role).append(" ALLOW FILTERING;");
		String query = sb.toString();

		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();

		ResultSet rs = session.execute(query);
		rs.forEach(r -> {
			Map<Object, Object> usr = new HashMap<Object, Object>();
			usr.put("username", r.getString("username"));
			usr.put("name", r.getString("name"));
			usr.put("role", r.getInt("role"));

			list.add(usr);
		});

		return ResponseEntity.ok(list);
	}

	/*
	 * @ApiOperation("取得全部資訊")
	 * 
	 * @RequestMapping(value = "/getAlluser", method = RequestMethod.GET) public
	 * ResponseEntity<?> getAllUser() { StringBuilder sb = new
	 * StringBuilder("SELECT * FROM user ;"); String query = sb.toString();
	 * 
	 * List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
	 * 
	 * ResultSet rs = session.execute(query); rs.forEach(r -> { Map<Object, Object>
	 * usr = new HashMap<Object, Object>(); usr.put("username",
	 * r.getString("username")); usr.put("name", r.getString("name"));
	 * usr.put("role", r.getInt("role"));
	 * 
	 * list.add(usr); });
	 * 
	 * return ResponseEntity.ok(list); }
	 */

	@ApiOperation("取得系統空間(unit:bytes)")
	@RequestMapping(value = "/getSpace", method = RequestMethod.GET)
	public ResponseEntity<?> getSpace() {
		File[] roots = File.listRoots(); // 取得硬碟分區

		Long total = 0L;
		Long unuse = 0L;
		Long folder = 0L;

		File data = new File("/usr/local/tomcat");

		for (File file : roots) {
			file.getPath();
			unuse = (file.getFreeSpace());
			// us = " 可用：" + (file.getUsableSpace() / 1024 / 1024 / 1024) + " GB \t";
			total = (file.getTotalSpace());
		}
		folder = (FileUtils.sizeOfDirectory(data));

		Map<Object, Object> space = new HashMap<Object, Object>();
		space.put("unuse", unuse);
		space.put("total", total);
		space.put("data", folder);
		return ResponseEntity.ok(space);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
