package deploy.controller;

import java.io.FileReader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import io.swagger.annotations.Api;

@Api(description = "匯入/匯出")
@Controller
public class ExportImportController {

	private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
			.build();;
	 //private Cluster cluster =
	 //Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
	 //.build();
	private Session session = cluster.connect("nicuspace");

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportJson/{transinno}", method = RequestMethod.GET)
	public ResponseEntity<Object> exportJson(@Valid @PathVariable String transinno) throws IOException {

		StringBuilder announcementSb = new StringBuilder("SELECT * FROM announcementboard WHERE transinno='")
				.append(transinno).append("' ALLOW FILTERING;");
		String announcementQuery = announcementSb.toString();
		StringBuilder scheduleSb = new StringBuilder("SELECT * FROM schedule WHERE transinno='").append(transinno)
				.append("' ALLOW FILTERING;");
		String scheduleQery = scheduleSb.toString();
		StringBuilder noticeSb = new StringBuilder("SELECT * FROM notice WHERE transinno='").append(transinno)
				.append("' ALLOW FILTERING;");
		String noticeQuery = noticeSb.toString();
		StringBuilder todoSb = new StringBuilder("SELECT * FROM todo WHERE transinno='").append(transinno)
				.append("' ALLOW FILTERING;");
		String todoQuery = todoSb.toString();
		StringBuilder patientSb = new StringBuilder("SELECT * FROM patient WHERE transinid='").append(transinno)
				.append("' ALLOW FILTERING;");
		String patientQuery = patientSb.toString();
		StringBuilder remarkSb = new StringBuilder("SELECT * FROM remark WHERE transid='").append(transinno)
				.append("' ALLOW FILTERING;");
		String remarkQuery = remarkSb.toString();
		StringBuilder patientremarkSb = new StringBuilder("SELECT * FROM patientremark WHERE transid='")
				.append(transinno).append("' ALLOW FILTERING;");
		String patientremarkQuery = patientremarkSb.toString();
		StringBuilder patientimportantmatterSb = new StringBuilder(
				"SELECT * FROM patientimportantmatter WHERE transinid='").append(transinno)
						.append("' ALLOW FILTERING;");
		String patientimportantmatterQuery = patientimportantmatterSb.toString();

		List<JSONObject> announcementlist = new ArrayList<JSONObject>();
		List<JSONObject> schedulelist = new ArrayList<JSONObject>();
		List<JSONObject> noticelist = new ArrayList<JSONObject>();
		List<JSONObject> todolist = new ArrayList<JSONObject>();
		List<JSONObject> patientlist = new ArrayList<JSONObject>();
		List<JSONObject> remarklist = new ArrayList<JSONObject>();
		List<JSONObject> patientremarklist = new ArrayList<JSONObject>();
		List<JSONObject> patientimportantmatterlist = new ArrayList<JSONObject>();

		JSONObject announcementObj = new JSONObject();
		JSONObject scheduleObj = new JSONObject();
		JSONObject noticeObj = new JSONObject();
		JSONObject todoObj = new JSONObject();
		JSONObject patientObj = new JSONObject();
		JSONObject remarkObj = new JSONObject();
		JSONObject patientremarkObj = new JSONObject();
		JSONObject patientimportantmatterObj = new JSONObject();

		ResultSet announcementRs = session.execute(announcementQuery);
		ResultSet scheduleRs = session.execute(scheduleQery);
		ResultSet noticeRs = session.execute(noticeQuery);
		ResultSet todoRs = session.execute(todoQuery);
		ResultSet patientRs = session.execute(patientQuery);
		ResultSet remarkRs = session.execute(remarkQuery);
		ResultSet patientremarkRs = session.execute(patientremarkQuery);
		ResultSet patientimportantmatterRs = session.execute(patientimportantmatterQuery);

		announcementRs.forEach(r -> {
			announcementObj.put("transinno", r.getString("transinno"));
			announcementObj.put("time", r.getTimestamp("time").toString());
			announcementObj.put("editor", r.getString("editor"));
			announcementObj.put("context", r.getString("context"));
			announcementlist.add(announcementObj);
		});
		scheduleRs.forEach(r -> {
			scheduleObj.put("transinno", r.getString("transinno"));
			scheduleObj.put("time", r.getTimestamp("time").toString());
			scheduleObj.put("bednumber", r.getInt("bednumber"));
			scheduleObj.put("type", r.getString("type"));
			scheduleObj.put("context", r.getString("context"));
			schedulelist.add(scheduleObj);
		});
		noticeRs.forEach(r -> {
			noticeObj.put("transinno", r.getString("transinno"));
			noticeObj.put("time", r.getTimestamp("time").toString());
			noticeObj.put("caseid", r.getString("caseid"));
			noticeObj.put("context", r.getString("context"));
			noticelist.add(noticeObj);
		});
		todoRs.forEach(r -> {
			todoObj.put("transinno", r.getString("transinno"));
			todoObj.put("type", r.getString("type"));
			todoObj.put("caseid", r.getString("caseid"));
			todoObj.put("context", r.getString("context"));
			todoObj.put("time", r.getTimestamp("time").toString());
			todolist.add(todoObj);
		});
		patientRs.forEach(r -> {
			patientObj.put("caseid", r.getString("caseid"));
			patientObj.put("hisid", r.getString("hisid"));
			patientObj.put("pnamec", r.getString("pnamec"));
			patientObj.put("psex", r.getString("psex"));
			patientObj.put("transintime", r.getTimestamp("time").toString());
			patientObj.put("transinid", r.getString("transinid"));
			patientlist.add(patientObj);
		});
		remarkRs.forEach(r -> {
			remarkObj.put("transid", r.getString("transid"));
			remarkObj.put("type", r.getString("type"));
			remarkObj.put("time", r.getTimestamp("time").toString());
			remarkObj.put("name", r.getString("name"));
			remarkObj.put("remark", r.getString("remark"));
			remarklist.add(remarkObj);
		});
		patientremarkRs.forEach(r -> {
			patientremarkObj.put("transid", r.getString("transid"));
			patientremarkObj.put("hisid", r.getString("hisid"));
			patientremarkObj.put("context", r.getString("context"));
			patientremarklist.add(patientremarkObj);
		});
		patientimportantmatterRs.forEach(r -> {
			patientimportantmatterObj.put("transinid", r.getString("transinid"));
			patientimportantmatterObj.put("hisid", r.getString("hisid"));
			patientimportantmatterObj.put("context", r.getString("context"));
			patientimportantmatterObj.put("time", r.getTimestamp("time").toString());
			patientimportantmatterObj.put("editor", r.getString("editor"));
			patientimportantmatterlist.add(patientimportantmatterObj);
		});

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("announcement", announcementlist);
		jsonObj.put("schedule", schedulelist);
		jsonObj.put("notice", noticelist);
		jsonObj.put("todo", todolist);
		jsonObj.put("patient", patientlist);
		jsonObj.put("remark", remarklist);
		jsonObj.put("patientremark", patientremarklist);
		jsonObj.put("patientimportant", patientimportantmatterlist);

		String filename = "export_transinno_" + transinno + ".json";

		Files.write(Paths.get(filename), jsonObj.toJSONString().getBytes());

		return ResponseEntity.ok(jsonObj.toJSONString());
	}

	@RequestMapping(value = "/importJson/{transinno}", method = RequestMethod.GET)
	public ResponseEntity<Object> importJson(@Valid @PathVariable String transinno) throws IOException, ParseException {

		String filename = "export_transinno_" + transinno + ".json";
		FileReader reader = new FileReader(filename);
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

		return ResponseEntity.ok(jsonObject.get("todo"));
	}
}
