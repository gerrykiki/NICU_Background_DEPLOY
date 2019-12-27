package deploy.controller;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import deploy.Repository.CenterMonitorRepository;
import deploy.model.CenterMonitor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "生命跡象(Ward/Patient View)")
@Controller
@RequestMapping("/wardvalue")
public class CenterMonitorController {
	@Autowired
	CenterMonitorRepository repository;
	
	private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
			.build();
	// private Cluster cluster =
	// Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
	// .build();
	private Session session = cluster.connect("nicuspace");


	@ApiOperation("建立生命跡象資訊")
	@RequestMapping(value = "/createwardvalue", method = RequestMethod.POST)
	public ResponseEntity<CenterMonitor> createWardValue(@Valid @RequestBody CenterMonitor wardvalue) {

		CenterMonitor _wardvalue = repository.save(wardvalue);
		return ResponseEntity.ok(_wardvalue);
	}

	@ApiOperation("取得全部資訊")
	@RequestMapping(value = "/getAllwardvalue", method = RequestMethod.GET)
	public ResponseEntity<Object> getWardValue() {
		Iterable<CenterMonitor> result = repository.findAll();

		List<CenterMonitor> list = new ArrayList<CenterMonitor>();
		result.forEach(list::add);
		return ResponseEntity.ok(list);
	}

	@ApiOperation("取得某一段時間某一個人生命跡象(原始資料)")
	@RequestMapping(value = "/getPatientwardvalue/{phistnum}/{starttime}/{endtime}", method = RequestMethod.GET)
	public ResponseEntity<Object> getPatientwardvalue(@Valid @PathVariable String phistnum,
			@Valid @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date starttime,
			@Valid @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date endtime) {

		StringBuilder sb = new StringBuilder("SELECT * FROM centermonitor WHERE phistnum='").append(phistnum)
				.append("' ALLOW FILTERING;");
		String query = sb.toString();

		ResultSet rs = session.execute(query);

		List<CenterMonitor> newlist = new ArrayList<CenterMonitor>();

		Calendar cal0 = Calendar.getInstance();
		cal0.setTime(starttime);
		cal0.add(Calendar.SECOND, -1);
		Date StartTime = cal0.getTime();

		Calendar cal = Calendar.getInstance();
		cal.setTime(endtime);
		cal.add(Calendar.SECOND, 1);
		Date EndTime = cal.getTime();

		rs.forEach(r -> {
			if (r.getTimestamp("time").after(StartTime) && r.getTimestamp("time").before(EndTime)) {
				CenterMonitor data = new CenterMonitor(r.getTimestamp("time"), r.getString("phistnum"), r.getInt("rr"), r.getInt("hr"),
						r.getInt("abpd"), r.getInt("abps"), r.getInt("abpm"), r.getInt("nbpd"),r.getInt("nbps"), r.getInt("nbpm"),
						r.getInt("spo2"), r.getFloat("bt"));
				newlist.add(data);
			}
		});

		return ResponseEntity.ok(newlist);
	}

}
