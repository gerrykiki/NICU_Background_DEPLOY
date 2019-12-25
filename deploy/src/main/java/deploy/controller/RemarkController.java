package deploy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import deploy.Repository.RemarkRepository;
import deploy.model.Remark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "註記")
@Controller
@RequestMapping("/Remark")
public class RemarkController {

	@Autowired
	RemarkRepository repository;

	private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
			.build();
	 //private Cluster cluster =
	 //Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
	 //.build();
	private Session session = cluster.connect("nicuspace");

	@ApiOperation("建立註記")
	@RequestMapping(value = "/createremark", method = RequestMethod.POST)
	public ResponseEntity<Remark> createAnnouncement(@Valid @RequestBody Remark remark) {

		Remark _remark = repository.save(remark);
		return new ResponseEntity<>(_remark, HttpStatus.OK);
	}

	@ApiOperation("取得全部註記")
	@RequestMapping(value = "/getAllremark", method = RequestMethod.GET)
	public ResponseEntity<Object> getRemark() {
		Iterable<Remark> result = repository.findAll();
		List<Remark> list = new ArrayList<Remark>();
		result.forEach(list::add);
		return ResponseEntity.ok(list);
	}

	@ApiOperation("取得某一類型註記")
	@RequestMapping(value = "/getOneremark/{transid}/{type}/{time}/{name}", method = RequestMethod.GET)
	public ResponseEntity<Object> getOneremark(@Valid @PathVariable String transid, @Valid @PathVariable String type,
			@Valid @PathVariable String time, @Valid @PathVariable String name) {
		StringBuilder sb = new StringBuilder("SELECT * FROM remark WHERE transid='").append(transid)
				.append("' and type='").append(type).append("' and time='").append(time).append("' and name='")
				.append(name).append("' ALLOW FILTERING;");
		String query = sb.toString();

		List<Remark> list = new ArrayList<Remark>();

		ResultSet rs = session.execute(query);
		rs.forEach(r -> {
			list.add(new Remark(r.getString("transid"), r.getString("type"), r.getTimestamp("time"),
					r.getString("name"), r.getString("remark"),r.getString("editor")));
		});

		return ResponseEntity.ok(list);
	}
}
