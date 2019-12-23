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

import deploy.model.Announcementboard;
import deploy.Repository.AnnouncementRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "病房公告(Ward View)")
@Controller
@RequestMapping("/Announcement")
public class AnnouncementController {

	@Autowired
	AnnouncementRepository announcementRepository;

	private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
			.build();
	// private Cluster cluster =
	// Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
	// .build();
	private Session session = cluster.connect("nicuspace");

	@ApiOperation("建立公告資訊")
	@RequestMapping(value = "/createAnnouncement", method = RequestMethod.POST)
	public ResponseEntity<?> createAnnouncement(@Valid @RequestBody List<Announcementboard> announcement) {
		announcementRepository.deleteAll();
		
		List<Announcementboard> annList = new ArrayList<Announcementboard>();
		
		announcement.forEach(ann -> {
			Announcementboard _announcement = new Announcementboard(ann.getTransinno(), ann.getTime(), ann.getContext(),
					ann.getEditor());
			annList.add(_announcement);
			announcementRepository.save(_announcement);
		});

		return new ResponseEntity<>(annList, HttpStatus.OK);
	}

	@ApiOperation("取得全部資訊")
	@RequestMapping(value = "/getAllannounce", method = RequestMethod.GET)
	public ResponseEntity<Object> getAnnouncement() {
		Iterable<Announcementboard> result = announcementRepository.findAll();
		List<Announcementboard> annList = new ArrayList<Announcementboard>();
		result.forEach(annList::add);
		return ResponseEntity.ok(annList);
	}

	/*
	 * @ApiOperation("刪除某人某天公告資訊")
	 * 
	 * @RequestMapping(value = "/delannouncement/{transinno}/{date}", method =
	 * RequestMethod.DELETE) public ResponseEntity<Object>
	 * delAnnouncement(@Valid @PathVariable String transinno,
	 * 
	 * @Valid @PathVariable String date) { StringBuilder sb = new
	 * StringBuilder("DELETE FROM announcementboard WHERE transinno='").append(
	 * transinno) .append("' and time='").append(date).append("' ;"); String query =
	 * sb.toString();
	 * 
	 * session.execute(query);
	 * 
	 * return ResponseEntity.ok(""); }
	 */

	@ApiOperation("取得某位病人某天資訊")
	@RequestMapping(value = "/getOneannouncement/{transinno}/{date}", method = RequestMethod.GET)
	public ResponseEntity<Object> getOnepatient(@Valid @PathVariable String transinno,
			@Valid @PathVariable String date) {
		List<Announcementboard> list = new ArrayList<Announcementboard>();

		StringBuilder sb = new StringBuilder("SELECT * FROM announcementboard WHERE transinno='").append(transinno)
				.append("' and time='").append(date).append("' ALLOW FILTERING;");
		String query = sb.toString();

		ResultSet rs = session.execute(query);
		rs.forEach(r -> {
			list.add(new Announcementboard(r.getString("transinno"), r.getTimestamp("time"), r.getString("editor"),
					r.getString("context")));
		});

		return ResponseEntity.ok(list);
	}
}