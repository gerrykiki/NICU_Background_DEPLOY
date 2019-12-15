package deploy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.datastax.driver.core.Cluster;

import com.datastax.driver.core.Session;

import deploy.Repository.ScheduleRepository;
import deploy.model.Schedule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "排程與事件(Ward/Patient View)")
@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	@Autowired
	ScheduleRepository repository;

	//private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
	//		.build();
	private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
			.build();
	private Session session = cluster.connect("nicu");

	@ApiOperation("建立排程資訊")
	@RequestMapping(value = "/createschedule", method = RequestMethod.POST)
	public ResponseEntity<Schedule> createSchedule(@Valid @RequestBody Schedule schedule) {

		Schedule _schedule = repository.save(schedule);
		return ResponseEntity.ok(_schedule);
	}

	@ApiOperation("取得全部資訊")
	@RequestMapping(value = "/getAllschedule", method = RequestMethod.GET)
	public ResponseEntity<Object> getSchedule() {
		Iterable<Schedule> result = repository.findAll();

		List<Schedule> list = new ArrayList<Schedule>();
		result.forEach(list::add);
		return ResponseEntity.ok(list);
	}

	@ApiOperation("刪除某人某天排程資訊")
	@RequestMapping(value = "/delschedule/{transinno}/{date}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delSchedule(@Valid @PathVariable String transinno, @Valid @PathVariable String date) {
		StringBuilder sb = new StringBuilder("DELETE FROM schedule WHERE transinno='").append(transinno)
				.append("' and time='").append(date).append("' ;");
		String query = sb.toString();

		session.execute(query);
		return ResponseEntity.ok("");
	}

	@ApiOperation("取得某位某時段病人資訊")
	@RequestMapping(value = "/getOnepatient/{transinno}/{starttime}/{endtime}", method = RequestMethod.GET)
	public ResponseEntity<Object> getOnepatient(@Valid @PathVariable String transinno,
			@Valid @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date starttime,
			@Valid @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date endtime) {
		Iterable<Schedule> result = repository.findAll();
		List<Schedule> list = new ArrayList<Schedule>();
		result.forEach(list::add);

		List<Schedule> newlist = new ArrayList<Schedule>();

		for (Schedule sch : list) {
			if (sch.getTransinno().equals(transinno) && sch.getTime().after(starttime)
					&& sch.getTime().before(endtime)) {
				Schedule date = new Schedule(sch.getTransinno(), sch.getTime(), sch.getBednumber(), sch.getContext(),
						sch.getType());
				newlist.add(date);
			}
		}

		return ResponseEntity.ok(newlist);
	}
}
