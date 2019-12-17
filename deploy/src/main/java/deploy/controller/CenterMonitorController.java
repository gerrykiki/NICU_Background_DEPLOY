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
		Iterable<CenterMonitor> result = repository.findAll();
		List<CenterMonitor> list = new ArrayList<CenterMonitor>();
		result.forEach(list::add);

		List<CenterMonitor> newlist = new ArrayList<CenterMonitor>();
		
		Calendar cal0 = Calendar.getInstance();
		cal0.setTime(starttime);
		cal0.add(Calendar.DATE, -1);
		Date StartTime = cal0.getTime();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(endtime);
		cal.add(Calendar.DATE, 1);
		Date EndTime = cal.getTime();


		for (CenterMonitor wv : list) {
			if (wv.getPhistnum().equals(phistnum) && wv.getTime().before(EndTime) && wv.getTime().after(StartTime)) {
				CenterMonitor data = new CenterMonitor(wv.getTime(), wv.getPhistnum(), wv.getRR(), wv.getHR(),
						wv.getABPd(), wv.getABPs(), wv.getABPm(), wv.getNBPd(), wv.getNBPs(), wv.getNBPm(),
						wv.getSpO2(), wv.getBT());
				newlist.add(data);
			}
		}

		return ResponseEntity.ok(newlist);
	}
}
