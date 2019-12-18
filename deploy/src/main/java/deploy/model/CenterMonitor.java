package deploy.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
//生命徵象
@Table
@Data
@AllArgsConstructor
public class CenterMonitor{
    @PrimaryKey
    private Date time;   
    //private String caseid;
    private String phistnum;
    private Integer RR;
    private Integer HR;
    private Integer ABPd;
    private Integer ABPs;
    private Integer ABPm;
    private Integer NBPd;
    private Integer NBPs;
    private Integer NBPm;
    private Integer spo2;
    private Float BT;
  //private Integer Wardvaluuid;   
    //private String typeString;
    
}
