package deploy.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import deploy.Repository.LoginlogRepository;
import deploy.model.LoginLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "顯示Log日誌")
@RestController
public class LogController {

	@Autowired
	LoginlogRepository loginlogRepository;

	@ApiOperation("Show login log(根據起始時間查詢成功登入記錄)(time格式為yyyy-MM-ddTHH:mm:ss)")
	@RequestMapping(value = "/getLoginlog/{starttime}/{endtime}", method = RequestMethod.GET)
	public ResponseEntity<?> getLoginlog(
			@Valid @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date starttime,
			@Valid @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date endtime) {
		Iterable<LoginLog> result = loginlogRepository.findAll();
		List<LoginLog> loginloglist = new ArrayList<LoginLog>();
		result.forEach(loginloglist::add);

		Calendar cal0 = Calendar.getInstance();
		cal0.setTime(starttime);
		cal0.add(Calendar.DATE, -1);
		Date StartTime = cal0.getTime();

		Calendar cal = Calendar.getInstance();
		cal.setTime(endtime);
		cal.add(Calendar.DATE, 1);
		Date EndTime = cal.getTime();

		List<LoginLog> newloginloglist = new ArrayList<LoginLog>();
		for (LoginLog llog : loginloglist) {
			if (llog.getTime().after(StartTime) && llog.getTime().before(EndTime)) {
				LoginLog datalog = new LoginLog(llog.getUuid(), llog.getTime(), llog.getUsername());
				newloginloglist.add(datalog);
			}
		}

		return ResponseEntity.ok(newloginloglist);
	}

	@ApiOperation("Show API access log (date格式為yyyy-MM-dd)")
	@RequestMapping(value = "/getApilog/{date}", method = RequestMethod.GET)
	public ResponseEntity<?> getAuthUser(@Valid @PathVariable String date) {

		String filename = "logs/localhost_access_log." + date + ".txt";
		List<Object> apilog = new ArrayList<Object>();

		try {
			FileReader reader = new FileReader(filename);
			BufferedReader br = new BufferedReader(reader);
			while (br.ready()) {
				apilog.add(br.readLine());
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(apilog);
	}

	@ApiOperation("Show debug log(date格式為yyyy-MM-dd)")
	@RequestMapping(value = "/getDebuglog/{date}", method = RequestMethod.GET)
	public ResponseEntity<?> getDebuglog(@Valid @PathVariable String date) {

		String filename = "logs/catalina." + date + ".log";
		List<Object> apilog = new ArrayList<Object>();

		try {
			FileReader reader = new FileReader(filename);
			BufferedReader br = new BufferedReader(reader);
			while (br.ready()) {
				apilog.add(br.readLine());
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(apilog);
	}
}
