package deploy.model;

import java.io.Serializable;

import javax.persistence.*;

//import com.ibm.db2.cmx.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
//@Table(name = "STAFF")
@Data
@AllArgsConstructor
@NamedStoredProcedureQueries({ @NamedStoredProcedureQuery(name = "ERDISP", procedureName = "ERDISP", resultClasses = {
		Erdisp.class }, parameters = {
				@StoredProcedureParameter(name = "THISNO", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "TCASENO", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "RET", type = Integer.class, mode = ParameterMode.OUT),
				@StoredProcedureParameter(name = "RETMSG", type = String.class, mode = ParameterMode.OUT) }) })
public class Erdisp implements Serializable {

	//private static final long serialVersionUID = 1L;

	private String ERSBKEY;
	private String ERDNAMEC;
	private String ERDHIST;
	private String ERDFIL2;
	private String ERDDATE;
	private String ERDTIME;
	private Integer ERDITKG1;
	private Integer ERDITKG2;
	private String ERDIA01;
	private String ERDIA02;
	private String ERDIA03;
	private String ERDIA04;
	private String ERDIA05;
	private String ERDIA06;
	private String ERDIA07;
	private String ERDIA08;
	private String ERDIA09;
	private String ERDIA10;
	private String ERDTA01;
	private String ERDTA02;
	private Integer ERDTA03;
	private Integer ERDTA031;
	private Integer ERDTA032;
	private Integer ERDTA04;
	private Integer ERDTA05;
	private Integer ERDTA06;
	private String ERDIT04;
	private String ERSDINDT;
	private String ERSDINTM;
	private String ERSDINPN;
	private String ERDUPDDT;
	private String ERDUPDTM;
	private String ERDUPDPN;
	private String ERDIB01;
	private String ERDIB02;
	private String ERDIB03;
	private String ERDIB04;
	private String ERDIB05;
	private String ERDIB06;
	private String ERDIB07;
	private String ERDIB08;
	private String ERDIB09;
	private String ERDIB10;
	private String ERDIB11;
	private String ERDIB12;
	private String ERDIB13;
	private String ERDIB14;
	private String ERDIB15;
	private String ERDIC01;
	private String ERDIC02;
	private String ERDIC03;
	private String ERDIC04;
	private String ERDID01;
	private String ERDID02;
	private String ERDID03;
	private String ERDID04;
	private String ERDDOC2;
	private String ERDDOC3;
	private String ERAPPEND;

}
