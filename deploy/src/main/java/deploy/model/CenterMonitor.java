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
    private Integer ABP_d;
    private Integer ABP_s;
    private Integer ABP_m;
    private Integer NBP_d;
    private Integer NBP_s;
    private Integer NBP_m;
    private Integer sp;
    private Float BT;
  //private Integer Wardvaluuid;   
    //private String typeString;
    
}
