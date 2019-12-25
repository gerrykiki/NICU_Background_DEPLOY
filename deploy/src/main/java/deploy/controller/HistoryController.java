package deploy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import deploy.Repository.HistoryRepository;
import deploy.model.History;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "歷史資料")
@Controller
@RequestMapping("/history")
public class HistoryController {
	@Autowired
	HistoryRepository historyRepository;

	private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
			.build();
	// private Cluster cluster =
	// Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
	// .build();
	private Session session = cluster.connect("nicuspace");

	@ApiOperation("建立某人歷史資訊")
	@RequestMapping(value = "/createHistory", method = RequestMethod.POST)
	public ResponseEntity<?> createHistory(@Valid @RequestBody History history) {
		History _history = historyRepository.save(history);
		return new ResponseEntity<>(_history, HttpStatus.OK);
	}

	@ApiOperation("取得全部資訊")
	@RequestMapping(value = "/getAllhistory", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllhistory() {
		Iterable<History> result = historyRepository.findAll();
		List<History> list = new ArrayList<History>();
		result.forEach(list::add);
		return ResponseEntity.ok(list);
	}

	@ApiOperation("取得某人歷史資訊")
	@RequestMapping(value = "/getOnehistory/{hisid}/{name}/{transouttime}/{doctor}", method = RequestMethod.GET)
	public ResponseEntity<Object> getOnehistory(@Valid @PathVariable String hisid, @Valid @PathVariable String name,
			@Valid @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date transouttime,
			@Valid @PathVariable String doctor) {
		StringBuilder sb = new StringBuilder("SELECT * FROM todo WHERE hisid='").append(hisid).append("' and name='")
				.append(name).append("', ane transouttime='").append(transouttime).append("', and doctor='")
				.append(doctor).append("' ALLOW FILTERING;");
		String query = sb.toString();

		List<History> list = new ArrayList<History>();

		ResultSet rs = session.execute(query);
		rs.forEach(r -> {
			if (r.getString("hisid").equals(hisid) && r.getString("name").equals(name)
					&& r.getTimestamp("transouttime").equals(transouttime) && r.getString("doctor").equals(doctor)) {
				History _history = new History(r.getString("uuid"), r.getInt("bednumber"), r.getString("hisid"),
						r.getString("name"), r.getInt("psex"), r.getInt("weight"), r.getInt("weeks"),
						r.getInt("transinage"), r.getString("caseid"), r.getString("transinid"),
						r.getTimestamp("transintime"), r.getString("doctor"));
				list.add(_history);
			}
		});

		return ResponseEntity.ok(list);
	}
}
