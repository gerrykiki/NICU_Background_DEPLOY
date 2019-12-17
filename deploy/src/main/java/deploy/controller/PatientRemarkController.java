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

import deploy.Repository.PatientRemarkRepository;
import deploy.model.PatientRemark;
import io.swagger.annotations.Api;

@Api(description = "病人註記(Patient View)")
@Controller
@RequestMapping("/patientremark")
public class PatientRemarkController {
	private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
			.build();
	 //private Cluster cluster =
	 //Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
	 //.build();
	private Session session = cluster.connect("nicuspace");

	@Autowired
	PatientRemarkRepository repository;

	@RequestMapping(value = "/createpatientremark", method = RequestMethod.POST)
	public ResponseEntity<PatientRemark> createPatientRemark(@Valid @RequestBody PatientRemark patientremark) {
		System.out.println("Create patientimportantmatter: " + patientremark.getHisid() + "...");
		PatientRemark _patientremark = repository.save(patientremark);
		return ResponseEntity.ok(_patientremark);
	}

	@RequestMapping(value = "/getAllpatientremark", method = RequestMethod.GET)
	public ResponseEntity<Object> getPatientRemark() {
		Iterable<PatientRemark> result = repository.findAll();
		System.out.println("getAllpatientremark" + result);
		List<PatientRemark> list = new ArrayList<PatientRemark>();
		result.forEach(list::add);
		return ResponseEntity.ok(list);
	}

	@RequestMapping(value = "/delpatientremark/{transid}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delPatientremark(@Valid @PathVariable String transid) {
		StringBuilder sb = new StringBuilder("DELETE FROM patientremark WHERE transid='").append(transid).append("' ;");
		String query = sb.toString();
		session.execute(query);
		return ResponseEntity.ok("");
	}

	@RequestMapping(value = "/getOnepatientremark/{transid}", method = RequestMethod.GET)
	public ResponseEntity<Object> getOnePatientRemark(@Valid @PathVariable String transid) {
		StringBuilder sb = new StringBuilder("SELECT * FROM patientremark WHERE transid='").append(transid)
				.append("' ALLOW FILTERING;");
		String query = sb.toString();

		List<PatientRemark> list = new ArrayList<PatientRemark>();

		ResultSet rs = session.execute(query);
		rs.forEach(r -> {
			list.add(new PatientRemark(r.getString("transid"), r.getString("hisid"), r.getString("context")));
		});

		return ResponseEntity.ok(list);
	}
}
