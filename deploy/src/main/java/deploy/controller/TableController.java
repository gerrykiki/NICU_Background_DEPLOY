package deploy.controller;

import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.db2.jcc.am.Connection;
import com.ibm.db2.jcc.am.ResultSet;

import deploy.model.Patient;
import deploy.Repository.PatientRepository;

@RestController
public class TableController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	String driver = "com.ibm.db2.jcc.DB2Driver";
	String url = "jdbc:db2://dbconnt.vghtpe.gov.tw:50000/VGHDBP";
	String userName = "XVGH96";
	String passWord = "nicuteam";

	@Autowired
	PatientRepository patientrepository;

	/*-Get DB table data-*/
	@GetMapping("/PBASINFO/{PHISTNUM}")
	public Map<Object, Object> PBASINFO(@PathVariable String PHISTNUM) {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;
		// List<Object> data = new ArrayList<Object>();
		Map<Object, Object> mm = new HashMap<>();
		System.setProperty("db2.jcc.charsetDecoderEncoder", "3");

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.PBASINFO WHERE PHISTNUM='" + PHISTNUM + "'");

			while (rs.next()) {

				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (rs.getMetaData().getColumnName(i).compareTo("PBIRTHDT") == 0) {
						mm.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
					if (rs.getMetaData().getColumnName(i).compareTo("PPBLOOD") == 0) {
						mm.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
				}
				// data.add(mm);
			}

			st.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return mm;
	}

	@GetMapping("/Table/TEST") // 查詢特定時間身高體重/體圍測量資訊
	public List<Map<Object, Object>> NISTEST() throws Exception {

		NisController nis = new NisController();
		List<Map<Object, Object>> data = nis.NISTEST();
		return data;
	}

	@GetMapping("/HBEDALL")
	public List<Map<Object, Object>> HBEDALL() {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;
		List<Map<Object, Object>> data = new ArrayList<Map<Object, Object>>();

		System.setProperty("db2.jcc.charsetDecoderEncoder", "3");

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			// rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.HBED WHERE
			// HBNURSTA='NICU'");
			rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.HBED");
			while (rs.next()) {
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// if (rs.getMetaData().getColumnName(i).compareTo("HBEDNO") == 0) {
					// filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					// }
					// if (rs.getMetaData().getColumnName(i).compareTo("PCASENO") == 0) {
					// filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					// }
					// if (rs.getMetaData().getColumnName(i).compareTo("PHISTNUM") == 0) {
					// filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					// }
					// if (rs.getMetaData().getColumnName(i).compareTo("PNAMEC") == 0) {
					// filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					// }
					// if (rs.getMetaData().getColumnName(i).compareTo("PSEX") == 0) {
					// filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					// }
					filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
				}
				Map<Object, Object> pbasigninfo = PBASINFO((String) filter.get("PHISTNUM"));
				String birthday = (String) pbasigninfo.get("PBIRTHDT");
				String ppblood = (String) pbasigninfo.get("PPBLOOD");
				filter.put("PBIRTHDT", birthday);
				filter.put("PPBLOOD", ppblood);

				NisController nis = new NisController();
				List<Map<Object, Object>> birweeks = nis.QMNC((String) filter.get("PCASENO"));
				Map<Object, Object> birweeksmap = birweeks.get(0);
				String days_birth = (String) birweeksmap.get("days");
				String weeks_birth = (String) birweeksmap.get("weeks");
				String weeks_days_birth = "[" + weeks_birth + "+" + days_birth + "]";
				filter.put("weeks_days_birth", weeks_days_birth);

				List<Map<Object, Object>> plocobject = PLOC(filter.get("PCASENO"));
				Collections.reverse(plocobject);
				Map<Object, Object> plocdata = plocobject.get(0);
				String transintime = (String) plocdata.get("PLOCDT");
				String transindays = (String) plocdata.get("PLOCTM");
				String transinid = "NICU" + (String) plocdata.get("PLOCDT") + (String) plocdata.get("PLOCTM");
				Patient h = new Patient(filter.get("PCASENO").toString(), filter.get("PHISTNUM").toString(),
						filter.get("PNAMEC").toString(), filter.get("PSEX").toString(), transindays, transintime,
						transinid);
				patientrepository.save(h);
				data.add(filter);

			}

			st.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/HBED")
	public List<Map<Object, Object>> HBED() {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;
		List<Map<Object, Object>> data = new ArrayList<Map<Object, Object>>();

		System.setProperty("db2.jcc.charsetDecoderEncoder", "3");

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.HBED WHERE HBNURSTA='NICU'");
			// rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.HBED");
			while (rs.next()) {
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (rs.getMetaData().getColumnName(i).compareTo("HBEDNO") == 0) {
						filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
					if (rs.getMetaData().getColumnName(i).compareTo("PCASENO") == 0) {
						filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
					if (rs.getMetaData().getColumnName(i).compareTo("PHISTNUM") == 0) {
						filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
					if (rs.getMetaData().getColumnName(i).compareTo("PNAMEC") == 0) {
						filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
					if (rs.getMetaData().getColumnName(i).compareTo("PSEX") == 0) {
						filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
				}
				Map<Object, Object> pbasigninfo = PBASINFO((String) filter.get("PHISTNUM"));
				String birthday = (String) pbasigninfo.get("PBIRTHDT");
				String ppblood = (String) pbasigninfo.get("PPBLOOD");
				filter.put("PBIRTHDT", birthday);
				filter.put("PPBLOOD", ppblood);

				NisController nis = new NisController();
				List<Map<Object, Object>> birweeks = nis.QMNC((String) filter.get("PCASENO"));
				Map<Object, Object> birweeksmap = birweeks.get(0);
				String days_birth = (String) birweeksmap.get("days");
				String weeks_birth = (String) birweeksmap.get("weeks");
				String weeks_days_birth = "[" + weeks_birth + "+" + days_birth + "]";
				filter.put("weeks_days_birth", weeks_days_birth);

				// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				// Date date = sdf.parse(birthday);
				// int timediff = (int) ((new Date().getTime() - date.getTime()) / (24 * 60 * 60 * 1000)) + parseInt(days_birth);
				// int w = (timediff / 7) + parseInt(weeks_birth);
				// int d = timediff % 7;
				// String weeks_local_birth = "[" + w + "+" + d + "]";
				// filter.put("weeks_local_birth", weeks_local_birth);



				List<Map<Object, Object>> plocobject = PLOC(filter.get("PCASENO"));
				Collections.reverse(plocobject);
				Map<Object, Object> plocdata = plocobject.get(0);
				String transintime = (String) plocdata.get("PLOCDT");
				String transindays = (String) plocdata.get("PLOCTM");
				String transinid = "NICU" + (String) plocdata.get("PLOCDT") + (String) plocdata.get("PLOCTM");
				Patient h = new Patient(filter.get("PCASENO").toString(), filter.get("PHISTNUM").toString(),
						filter.get("PNAMEC").toString(), filter.get("PSEX").toString(), transindays, transintime,
						transinid);
				patientrepository.save(h);
				data.add(filter);

			}

			st.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}


	@GetMapping("/PLOC/{PCASENO}")
	public List<Map<Object, Object>> PLOC(@PathVariable Object PCASENO) {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;
		List<Map<Object, Object>> data = new ArrayList<Map<Object, Object>>();

		System.setProperty("db2.jcc.charsetDecoderEncoder", "3");

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.PLOC WHERE PCASENO='" + PCASENO + "'");

			while (rs.next()) {
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// data.add(rs.getMetaData().getColumnName(i) + ":" + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
				}
				data.add(filter);
			}

			st.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/PDOCNEW/{PCASENO}")
	public List<Object> PDOCNEW(@PathVariable String PCASENO) {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;
		List<Object> data = new ArrayList<Object>();

		System.setProperty("db2.jcc.charsetDecoderEncoder", "3");

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.PDOCNEW WHERE PCASENO='" + PCASENO + "'");

			while (rs.next()) {
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (rs.getMetaData().getColumnName(i).compareTo("PDTYPE") == 0) {
						filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
					if (rs.getMetaData().getColumnName(i).compareTo("PDMDNO") == 0) {
						filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
					if (rs.getMetaData().getColumnName(i).compareTo("PDDOCNMC") == 0) {
						filter.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
				}
				data.add(filter);
			}

			st.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}
}
