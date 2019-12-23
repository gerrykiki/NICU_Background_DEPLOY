package deploy.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
	// private Cluster cluster =
	// Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
	// .build();
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

		ResultSet announcementRs = session.execute(announcementQuery);
		ResultSet scheduleRs = session.execute(scheduleQery);
		ResultSet noticeRs = session.execute(noticeQuery);
		ResultSet todoRs = session.execute(todoQuery);
		ResultSet patientRs = session.execute(patientQuery);
		ResultSet remarkRs = session.execute(remarkQuery);
		ResultSet patientremarkRs = session.execute(patientremarkQuery);
		ResultSet patientimportantmatterRs = session.execute(patientimportantmatterQuery);

		announcementRs.forEach(r -> {
			JSONObject announcementObj = new JSONObject();
			announcementObj.put("transinno", r.getString("transinno"));
			announcementObj.put("time", r.getTimestamp("time").toString());
			announcementObj.put("editor", r.getString("editor"));
			announcementObj.put("context", r.getString("context"));
			announcementlist.add(announcementObj);
		});
		scheduleRs.forEach(r -> {
			JSONObject scheduleObj = new JSONObject();
			scheduleObj.put("transinno", r.getString("transinno"));
			scheduleObj.put("time", r.getTimestamp("time").toString());
			scheduleObj.put("bednumber", r.getInt("bednumber"));
			scheduleObj.put("type", r.getString("type"));
			scheduleObj.put("context", r.getString("context"));
			schedulelist.add(scheduleObj);
		});
		noticeRs.forEach(r -> {
			JSONObject noticeObj = new JSONObject();
			noticeObj.put("transinno", r.getString("transinno"));
			noticeObj.put("time", r.getTimestamp("time").toString());
			noticeObj.put("caseid", r.getString("caseid"));
			noticeObj.put("context", r.getString("context"));
			noticelist.add(noticeObj);
		});
		todoRs.forEach(r -> {
			JSONObject todoObj = new JSONObject();
			todoObj.put("transinno", r.getString("transinno"));
			todoObj.put("type", r.getString("type"));
			todoObj.put("caseid", r.getString("caseid"));
			todoObj.put("context", r.getString("context"));
			todoObj.put("time", r.getTimestamp("time").toString());
			todolist.add(todoObj);
		});
		patientRs.forEach(r -> {
			JSONObject patientObj = new JSONObject();
			patientObj.put("caseid", r.getString("caseid"));
			patientObj.put("hisid", r.getString("hisid"));
			patientObj.put("pnamec", r.getString("pnamec"));
			patientObj.put("psex", r.getString("psex"));
			patientObj.put("transintime", r.getTimestamp("time").toString());
			patientObj.put("transinid", r.getString("transinid"));
			patientlist.add(patientObj);
		});
		remarkRs.forEach(r -> {
			JSONObject remarkObj = new JSONObject();
			remarkObj.put("transid", r.getString("transid"));
			remarkObj.put("type", r.getString("type"));
			remarkObj.put("time", r.getTimestamp("time").toString());
			remarkObj.put("name", r.getString("name"));
			remarkObj.put("remark", r.getString("remark"));
			remarklist.add(remarkObj);
		});
		patientremarkRs.forEach(r -> {
			JSONObject patientremarkObj = new JSONObject();
			patientremarkObj.put("transid", r.getString("transid"));
			patientremarkObj.put("hisid", r.getString("hisid"));
			patientremarkObj.put("context", r.getString("context"));
			patientremarklist.add(patientremarkObj);
		});
		patientimportantmatterRs.forEach(r -> {
			JSONObject patientimportantmatterObj = new JSONObject();
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/importJson/{transinno}", method = RequestMethod.GET)
	public ResponseEntity<?> importJson(@Valid @PathVariable String transinno) {
		String filename = "export_transinno_" + transinno + ".json";
		JSONParser jsonParser = new JSONParser();
		
		List<JSONObject> announcementList = new ArrayList<JSONObject>();
		List<JSONObject> scheduleList = new ArrayList<JSONObject>();
		List<JSONObject> noticeList = new ArrayList<JSONObject>();
		List<JSONObject> todoList = new ArrayList<JSONObject>();
		List<JSONObject> patientList = new ArrayList<JSONObject>();
		List<JSONObject> remarkList = new ArrayList<JSONObject>();
		List<JSONObject> patientremarkList = new ArrayList<JSONObject>();
		List<JSONObject> patientimportantmatterList = new ArrayList<JSONObject>();

		try {
			FileReader reader = new FileReader(filename);

			SimpleDateFormat parserDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH);
			SimpleDateFormat outputDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

			JSONObject obj = (JSONObject) jsonParser.parse(reader);

			JSONArray todolist = (JSONArray) obj.get("todo");
			todolist.forEach(todo -> {
				JSONObject dataTodo = (JSONObject) todo;

				String date = "";
				try {
					Date dateP = parserDate.parse(dataTodo.get("time").toString());
					Calendar cal = Calendar.getInstance();
					cal.setTime(dateP);
					cal.add(Calendar.HOUR, -8);
					date = outputDate.format(cal.getTime());
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}

				StringBuilder sb = new StringBuilder("INSERT INTO ").append("todo")
						.append("(time , transinno , caseid ,context , type )").append("VALUES('").append(date)
						.append("' ,'").append(dataTodo.get("transinno").toString()).append("','")
						.append(dataTodo.get("caseid").toString()).append("','")
						.append(dataTodo.get("context").toString()).append("','")
						.append(dataTodo.get("type").toString()).append("');");
				String query = sb.toString();
				session.execute(query);
				
				todoList.add(dataTodo);

			});

			JSONArray schedulelist = (JSONArray) obj.get("schedule");
			schedulelist.forEach(schedule -> {
				JSONObject dataSchedule = (JSONObject) schedule;

				String date = "";
				try {
					Date dateP = parserDate.parse(dataSchedule.get("time").toString());
					Calendar cal = Calendar.getInstance();
					cal.setTime(dateP);
					cal.add(Calendar.HOUR, -8);
					date = outputDate.format(cal.getTime());
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}

				StringBuilder sb = new StringBuilder("INSERT INTO ").append("schedule")
						.append("(time , transinno , bednumber ,context , type )").append("VALUES('").append(date)
						.append("' ,'").append(dataSchedule.get("transinno").toString()).append("',")
						.append(Integer.valueOf(dataSchedule.get("bednumber").toString())).append(",'")
						.append(dataSchedule.get("context").toString()).append("','")
						.append(dataSchedule.get("type").toString()).append("');");
				String query = sb.toString();
				session.execute(query);
				
				scheduleList.add(dataSchedule);
			});

			JSONArray patientlist = (JSONArray) obj.get("patient");
			patientlist.forEach(patient -> {
				JSONObject dataPatient = (JSONObject) patient;

				String date = "";
				try {
					Date dateP = parserDate.parse(dataPatient.get("transintime").toString());
					Calendar cal = Calendar.getInstance();
					cal.setTime(dateP);
					cal.add(Calendar.HOUR, -8);
					date = outputDate.format(cal.getTime());
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}

				StringBuilder sb = new StringBuilder("INSERT INTO ").append("patientdata")
						.append("(transintime , transinid , psex ,pnamec , hisid,caseid )").append("VALUES('")
						.append(date).append("' ,'").append(dataPatient.get("transinid").toString()).append("','")
						.append(dataPatient.get("psex").toString()).append("','")
						.append(dataPatient.get("pnamec").toString()).append("','")
						.append(dataPatient.get("hisid").toString()).append("','")
						.append(dataPatient.get("caseid").toString()).append("');");
				String query = sb.toString();
				session.execute(query);
				
				patientList.add(dataPatient);
			});

			JSONArray announcementlist = (JSONArray) obj.get("announcement");
			announcementlist.forEach(announcement -> {
				JSONObject dataAnnouncement = (JSONObject) announcement;

				String date = "";
				try {
					Date dateP = parserDate.parse(dataAnnouncement.get("time").toString());
					Calendar cal = Calendar.getInstance();
					cal.setTime(dateP);
					cal.add(Calendar.HOUR, -8);
					date = outputDate.format(cal.getTime());
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}

				StringBuilder sb = new StringBuilder("INSERT INTO ").append("announcementboard")
						.append("(time , transinno , context ,editor )").append("VALUES('").append(date).append("' ,'")
						.append(dataAnnouncement.get("transinno").toString()).append("','")
						.append(dataAnnouncement.get("context").toString()).append("','")
						.append(dataAnnouncement.get("editor").toString()).append("');");
				String query = sb.toString();
				session.execute(query);
				
				announcementList.add(dataAnnouncement);

			});

			JSONArray noticelist = (JSONArray) obj.get("notice");
			noticelist.forEach(notice -> {
				JSONObject dataNotice = (JSONObject) notice;

				String date = "";
				try {
					Date dateP = parserDate.parse(dataNotice.get("time").toString());
					Calendar cal = Calendar.getInstance();
					cal.setTime(dateP);
					cal.add(Calendar.HOUR, -8);
					date = outputDate.format(cal.getTime());
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}

				StringBuilder sb = new StringBuilder("INSERT INTO ").append("notice")
						.append("(time , transinno , caseid ,context )").append("VALUES('").append(date).append("' ,'")
						.append(dataNotice.get("transinno").toString()).append("','")
						.append(dataNotice.get("caseid").toString()).append("','")
						.append(dataNotice.get("context").toString()).append("');");
				String query = sb.toString();
				session.execute(query);
				
				noticeList.add(dataNotice);

			});

			JSONArray remarklist = (JSONArray) obj.get("remark");
			remarklist.forEach(remark -> {
				JSONObject dataRemark = (JSONObject) remark;

				String date = "";
				try {
					Date dateP = parserDate.parse(dataRemark.get("time").toString());
					Calendar cal = Calendar.getInstance();
					cal.setTime(dateP);
					cal.add(Calendar.HOUR, -8);
					date = outputDate.format(cal.getTime());
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}

				StringBuilder sb = new StringBuilder("INSERT INTO ").append("remark")
						.append("(time , transid , type ,remark , name )").append("VALUES('").append(date)
						.append("' ,'").append(dataRemark.get("transid").toString()).append("','")
						.append(dataRemark.get("type").toString()).append("','")
						.append(dataRemark.get("remark").toString()).append("','")
						.append(dataRemark.get("name").toString()).append("');");
				String query = sb.toString();
				session.execute(query);
				
				remarkList.add(dataRemark);

			});

			JSONArray patientremarklist = (JSONArray) obj.get("patientremark");
			patientremarklist.forEach(patientremark -> {
				JSONObject dataPatientremark = (JSONObject) patientremark;

				StringBuilder sb = new StringBuilder("INSERT INTO ").append("patientremark")
						.append("(transid , hisid ,context  )").append("VALUES('")
						.append(dataPatientremark.get("transid").toString()).append("' ,'")
						.append(dataPatientremark.get("hisid").toString()).append("','")
						.append(dataPatientremark.get("context").toString()).append("');");
				String query = sb.toString();
				session.execute(query);
				
				patientremarkList.add(dataPatientremark);

			});

			JSONArray patientimportantmatterlist = (JSONArray) obj.get("patientimportant");
			patientimportantmatterlist.forEach(patientimportant -> {
				JSONObject dataPatientimportantmatter = (JSONObject) patientimportant;

				String date = "";
				try {
					Date dateP = parserDate.parse(dataPatientimportantmatter.get("time").toString());
					Calendar cal = Calendar.getInstance();
					cal.setTime(dateP);
					cal.add(Calendar.HOUR, -8);
					date = outputDate.format(cal.getTime());
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}

				StringBuilder sb = new StringBuilder("INSERT INTO ").append("patientimportant")
						.append("(time , transinid ,hisid,context,editor )").append("VALUES('").append(date)
						.append("' ,'").append(dataPatientimportantmatter.get("transinid").toString()).append("','")
						.append(dataPatientimportantmatter.get("hisid").toString()).append("','")
						.append(dataPatientimportantmatter.get("context").toString()).append("','")
						.append(dataPatientimportantmatter.get("editor").toString()).append("');");
				String query = sb.toString();
				session.execute(query);
				
				patientimportantmatterList.add(dataPatientimportantmatter);

			});

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("announcement", announcementList);
		jsonObj.put("schedule", scheduleList);
		jsonObj.put("notice", noticeList);
		jsonObj.put("todo", todoList);
		jsonObj.put("patient", patientList);
		jsonObj.put("remark", remarkList);
		jsonObj.put("patientremark", patientremarkList);
		jsonObj.put("patientimportant", patientimportantmatterList);

		return ResponseEntity.ok(jsonObj.toJSONString());
	}
}
