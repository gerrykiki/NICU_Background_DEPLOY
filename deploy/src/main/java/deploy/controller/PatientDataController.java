package deploy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import deploy.Repository.PatientRepository;
import deploy.model.Patient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "基本資料(Ward View)")
@Controller
@RequestMapping("/patient")
public class PatientDataController {

	@Autowired
	PatientRepository patientRepository;

	private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
			.build();
	 //private Cluster cluster =
	 //Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
	 //.build();
	private Session session = cluster.connect("nicuspace");

	@ApiOperation("填寫基本資料")
	@RequestMapping(value = "/createpatient", method = RequestMethod.POST)
	public ResponseEntity<Patient> createAnnouncement(@Valid @RequestBody Patient patientinformation) {

		Patient _patientinformation = patientRepository.save(patientinformation);
		return ResponseEntity.ok(_patientinformation);
	}

	@ApiOperation("取得全部資訊")
	@RequestMapping(value = "/getAllpatient", method = RequestMethod.GET)
	public ResponseEntity<Object> getAnnouncement() {
		Iterable<Patient> result = patientRepository.findAll();

		List<Patient> annList = new ArrayList<Patient>();
		result.forEach(annList::add);
		return ResponseEntity.ok(annList);
	}

	@ApiOperation("刪除某位病人")
	@RequestMapping(value = "/delpatient/{transinid}/{pacseno}/{phistnum}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delAnnouncement(@Valid @PathVariable String transinid,
			@Valid @PathVariable String pacseno, @Valid @PathVariable String phistnum) {

		StringBuilder sb = new StringBuilder("DELETE FROM patientdata WHERE transinid='").append(transinid)
				.append("' and caseid='").append(pacseno).append("' and hisid='").append(phistnum).append("' ;");
		String query = sb.toString();

		session.execute(query);

		return ResponseEntity.ok("");
	}

	@ApiOperation("取得某位病人資訊")
	@RequestMapping(value = "/getOnepatient/{transinid}", method = RequestMethod.GET)
	public ResponseEntity<Object> getOnepatient(@Valid @PathVariable String transinid,
			@Valid @PathVariable String pacseno, @Valid @PathVariable String phistnum) {
		StringBuilder sb = new StringBuilder("SELECT * FROM patient WHERE transinid='").append(transinid)
				.append("' ALLOW FILTERING;");
		String query = sb.toString();

		List<Patient> list = new ArrayList<Patient>();

		ResultSet rs = session.execute(query);
		rs.forEach(r -> {
			list.add(new Patient(r.getString("caseid"), r.getString("hisid"), r.getString("pnamec"),
					r.getInt("psex"), r.getTimestamp("transintime"), r.getString("transinid")));
		});

		return ResponseEntity.ok(list);
	}

}