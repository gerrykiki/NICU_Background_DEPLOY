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

import deploy.Repository.ToDoRepository;
import deploy.model.ToDo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "待辦事項To Do List(Ward/Patient View)")
@Controller
@RequestMapping("/todo")
public class ToDoController {
	@Autowired
	ToDoRepository repository;

	private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
			.build();
	//private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
			//.build();
	private Session session = cluster.connect("nicuspace");

	@ApiOperation("建立待辦資訊")
	@RequestMapping(value = "/createtodo", method = RequestMethod.POST)
	public ResponseEntity<ToDo> createToDo(@Valid @RequestBody ToDo todo) {

		ToDo _todo = repository.save(todo);
		return ResponseEntity.ok(_todo);
	}

	@ApiOperation("取得全部資訊")
	@RequestMapping(value = "/getAlltodo", method = RequestMethod.GET)
	public ResponseEntity<Object> getToDo() {
		Iterable<ToDo> result = repository.findAll();

		List<ToDo> list = new ArrayList<ToDo>();
		result.forEach(list::add);
		return ResponseEntity.ok(list);
	}

	@ApiOperation("刪除某人某件待辦資訊")
	@RequestMapping(value = "/deltodo/{transinno}/{type}/{date}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delSchedule(@Valid @PathVariable String transinno, @Valid @PathVariable String type,
			@Valid @PathVariable String date) {
		StringBuilder sb = new StringBuilder("DELETE FROM todo WHERE transinno='").append(transinno)
				.append("' and type='").append(type).append("' and time='").append(date).append("' ;");
		String query = sb.toString();

		session.execute(query);

		return ResponseEntity.ok("");
	}

	@ApiOperation("取得某位病人資訊")
	@RequestMapping(value = "/getOnepatient/{transinno}/{caseid}", method = RequestMethod.GET)
	public ResponseEntity<Object> getOnepatient(@Valid @PathVariable String transinno,
			@Valid @PathVariable String caseid) {
		StringBuilder sb = new StringBuilder("SELECT * FROM todo WHERE transinno='").append(transinno)
				.append("' and caseid='").append(caseid).append("' ALLOW FILTERING;");
		String query = sb.toString();

		List<ToDo> list = new ArrayList<ToDo>();

		ResultSet rs = session.execute(query);
		rs.forEach(r -> {
			list.add(new ToDo(r.getString("transinno"), r.getString("type"), r.getString("caseid"),
					r.getString("context"), r.getTimestamp("time")));
		});

		return ResponseEntity.ok(list);
	}
}
