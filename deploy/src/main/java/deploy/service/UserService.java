package deploy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import deploy.Repository.UserRepository;
import deploy.model.User;
import deploy.model.UserDTO;


@Service
public class UserService implements UserDetailsService {

	private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
			.build();
	 //private Cluster cluster =
	 //Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
	 //.build();
	private Session session = cluster.connect("nicuspace");

	@Autowired
	UserRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		StringBuilder sb = new StringBuilder("SELECT * FROM user WHERE username='").append(username)
				.append("' ALLOW FILTERING;");
		String query = sb.toString();

		List<User> list = new ArrayList<User>();

		ResultSet rs = session.execute(query);
		rs.forEach(r -> {
			list.add(new User(r.getString("username"), r.getString("password"), r.getInt("role"), r.getString("name")));
		});

		if (list == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		String usr = "";
		String pwd = "";
		for (User user : list) {
			usr = user.getUsername();
			pwd = user.getPassword();
		}
		return new org.springframework.security.core.userdetails.User(usr, pwd, new ArrayList<>());
	}

	public User save(UserDTO user) {
		User newUser = new User(user.getUsername(), bcryptEncoder.encode(user.getPassword()), user.getRole(),
				user.getName());

		return userDao.save(newUser);
	}
}
