package deploy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import deploy.Repository.PatientRepository;
import deploy.model.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "病房列表")
@Controller
@RequestMapping("/Database")
public class DatabasemanageController {

    private Cluster cluster =
    Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
    .build();
     //private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").withPort(7777)
             //.build();
    private Session session = cluster.connect("nicuspace");
    private static final String TABLE_NAME = "patient";

    @Autowired
    PatientRepository patientrepository;

    @ApiOperation("資訊列表")
    @RequestMapping(value = "/GETWARDINDEXLIST/{DAYSTEXT}", method = RequestMethod.GET)
    public void getwardlistinfo(@PathVariable String DAYSTEXT) {

        TableController hbednow = new TableController();
        List<Map<Object, Object>> hblist = hbednow.HBED();
        Map<Object,Object> hbednumber = new HashMap<>();
        hbednumber.put("HBEDNUMBER", hblist.size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(DAYSTEXT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timestamp = date.getTime() - (7 * 24 * 60 * 60 * 1000);
        List<Map<Object,Object>> Maplist = new ArrayList<Map<Object,Object>>();
        for (int i = 0; i < 7; i++) {
            long time = timestamp + (i*24*60*60*1000);
            String dateString = sdformat.format(new Date(time));
            StringBuilder sb = new StringBuilder("SELECT * FROM patient WHERE transindays='").append(dateString)
            .append("'").append("' ALLOW FILTERING;");			
            String query = sb.toString();
            ResultSet rs = session.execute(query);
            List<Object> list = new ArrayList<Object>();		
            rs.forEach(r -> {
                list.add(r.getString("transinno"));
             });
            Map<Object,Object> datetransin = new HashMap<>();
            datetransin.put(sdf.format(new Date(time)), list.size());
            Maplist.add(datetransin);
        }
        

    }

}