package deploy.controller;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import deploy.Repository.PatientImportantMatterRepository;
import deploy.model.PatientImportantMatter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "病人重要注意事項(Patient View)")
@Controller
@RequestMapping("/patientimportantmatter")
public class PatientImportantMatterController {

	@Autowired
	PatientImportantMatterRepository repository;
	
	@ApiOperation("建立事項資訊")
    @RequestMapping(value = "/createpatientimportantmatter", method = RequestMethod.POST)
	public ResponseEntity<PatientImportantMatter> createPatientImportantMatter(@Valid @RequestBody PatientImportantMatter patientimportantmatter) {
		System.out.println("Create patientimportantmatter: " + patientimportantmatter.getHisid() + "...");
		PatientImportantMatter _patientimportantmatter = repository.save(patientimportantmatter);
		return ResponseEntity.ok(_patientimportantmatter);
    }
    
	@ApiOperation("取得全部資訊")
    @RequestMapping(value = "/getAllpatientimportantmatter", method = RequestMethod.GET)
	public ResponseEntity<Object> getPatientImportantMatter() {
        Iterable<PatientImportantMatter> result = repository.findAll();
        System.out.println("getAllpatientimportantmatter" + result);
		List<PatientImportantMatter> list = new ArrayList<PatientImportantMatter>();
		result.forEach(list::add);
		return ResponseEntity.ok(list);
	}
    
	@ApiOperation("刪除某人資訊")
    @RequestMapping(value = "/delpatientimportantmatter/{transinid}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delPatientimportantmatter(@Valid @PathVariable String transinid) {	 		 
    	 Boolean result = repository.existsById(transinid);
		 if(result) {
			 repository.deleteById(transinid);
		 }	 		
		return ResponseEntity.ok("{ \"success\" : "+ (result ? "true" : "false") +" }" );
	}
	
	@ApiOperation("取得某位病人資訊")
    @RequestMapping(value = "/getOnepatient/{HISID}", method = RequestMethod.GET)
	public ResponseEntity<Object> getOnepatient(@Valid @PathVariable String transinid) {	 		 
		Optional<PatientImportantMatter> list = repository.findById(transinid);
		 	 		
		return ResponseEntity.ok(list);
	}
}
