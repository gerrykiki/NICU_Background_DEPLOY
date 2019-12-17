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

import deploy.Repository.NoticeRepository;
import deploy.model.Notice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "待辦事項 Notice (Ward/Patient View)")
@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	NoticeRepository noticeRepository;

	private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
			.build();
	//private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
			//.build();
	private Session session = cluster.connect("nicuspace");

	@ApiOperation("建立資訊")
	@RequestMapping(value = "/createNotice", method = RequestMethod.POST)
	public ResponseEntity<Notice> createNotice(@Valid @RequestBody Notice notice) {
		Notice _notice = noticeRepository.save(notice);
		return new ResponseEntity<>(_notice, HttpStatus.OK);
	}

	@ApiOperation("取得全部資訊")
	@RequestMapping(value = "/getAllnotice", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllnotice() {
		Iterable<Notice> result = noticeRepository.findAll();
		List<Notice> list = new ArrayList<Notice>();
		result.forEach(list::add);
		return ResponseEntity.ok(list);
	}

	@ApiOperation("刪除某人某件資訊")
	@RequestMapping(value = "/delnotice/{transinno}/{date}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delSchedule(@Valid @PathVariable String transinno, @Valid @PathVariable String date) {
		StringBuilder sb = new StringBuilder("DELETE FROM notice WHERE transinno='").append(transinno)
				.append("' and time='").append(date).append("' ;");
		String query = sb.toString();

		session.execute(query);

		return ResponseEntity.ok("");
	}

	@ApiOperation("取得某位病人資訊")
	@RequestMapping(value = "/getOnepatient/{transinno}/{caseid}", method = RequestMethod.GET)
	public ResponseEntity<Object> getOnepatient(@Valid @PathVariable String transinno,
			@Valid @PathVariable String caseid) {
		StringBuilder sb = new StringBuilder("SELECT * FROM notice WHERE transinno='").append(transinno)
				.append("' and caseid='").append(caseid).append("' ALLOW FILTERING;");
		String query = sb.toString();

		List<Notice> list = new ArrayList<Notice>();

		ResultSet rs = session.execute(query);
		rs.forEach(r -> {
			list.add(new Notice(r.getString("transinno"), r.getTimestamp("time"), r.getString("caseid"),
					r.getString("context")));
		});

		return ResponseEntity.ok(list);
	}
}
