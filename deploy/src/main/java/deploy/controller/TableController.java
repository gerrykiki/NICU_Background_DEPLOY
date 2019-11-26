package deploy.controller;

import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.db2.jcc.am.Connection;
import com.ibm.db2.jcc.am.ResultSet;

import deploy.model.Hbed;
import deploy.model.HbedRepository;

@RestController
public class TableController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	String driver = "com.ibm.db2.jcc.DB2Driver";
	String url = "jdbc:db2://dbconnt.vghtpe.gov.tw:50000/VGHDBP";
	String userName = "XVGH96";
	String passWord = "nicuteam";

	/*-Get DB table data-*/
	@GetMapping("/PBASINFO/{PHISTNUM}")
	public List<String> PBASINFO(@PathVariable String PHISTNUM) {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;
		List<String> data = new ArrayList<String>();

		System.setProperty("db2.jcc.charsetDecoderEncoder", "3");

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.PBASINFO WHERE PHISTNUM='" + PHISTNUM + "'");

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					data.add(rs.getMetaData().getColumnName(i) + ":" + rs.getString(i));
				}
			}

			st.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@Autowired
	HbedRepository hbedRepository;

	@GetMapping("/HBED")
	public List<String> HBED() {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;
		List<String> data = new ArrayList<String>();
		Map<Object, Object> m2 = new HashMap<>();
		Map<Object, Object> mm = new HashMap<>();

		System.setProperty("db2.jcc.charsetDecoderEncoder", "3");

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.HBED WHERE HBNURSTA='NICU'");

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// data.add(rs.getMetaData().getColumnName(i) + ":" + rs.getString(i);
					mm.put(rs.getMetaData().getColumnName(i), rs.getString(i));
				}

				data.add("HBNURSTA:" + mm.get("HBNURSTA").toString());
				data.add("HBEDNO:" + mm.get("HBEDNO").toString());
				data.add("PCASENO:" + mm.get("PCASENO").toString());
				data.add("PHISTNUM" + mm.get("PHISTNUM").toString());
				data.add("PNAMEC:" + mm.get("PNAMEC").toString());
				data.add("PSEX:" + mm.get("PSEX").toString());
				data.add(" ");
				Hbed h = new Hbed(mm.get("HBNURSTA").toString(), mm.get("HBEDNO").toString(),
						mm.get("PCASENO").toString(), mm.get("PHISTNUM").toString(), mm.get("PNAMEC").toString(),
						mm.get("PSEX").toString());
				hbedRepository.save(h);
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
	public List<String> PLOC(@PathVariable String PCASENO) {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;
		List<String> data = new ArrayList<String>();

		System.setProperty("db2.jcc.charsetDecoderEncoder", "3");

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.PLOC WHERE PCASENO='" + PCASENO + "'");

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					data.add(rs.getMetaData().getColumnName(i) + ":" + rs.getString(i));
				}
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
	public List<String> PDOCNEW(@PathVariable String PCASENO) {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;
		List<String> data = new ArrayList<String>();

		System.setProperty("db2.jcc.charsetDecoderEncoder", "3");

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.PDOCNEW WHERE PCASENO='" + PCASENO + "'");

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					data.add(rs.getMetaData().getColumnName(i) + ":" + rs.getString(i));
				}
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
