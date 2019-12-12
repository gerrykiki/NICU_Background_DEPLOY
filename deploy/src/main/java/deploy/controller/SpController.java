package deploy.controller;

import java.sql.DriverManager;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.db2.jcc.am.CallableStatement;
import com.ibm.db2.jcc.am.Connection;
import com.ibm.db2.jcc.am.ResultSet;

@RestController
public class SpController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	String driver = "com.ibm.db2.jcc.DB2Driver";
	String url = "jdbc:db2://dbconnt.vghtpe.gov.tw:50000/VGHDBP";
	String userName = "XVGH96";
	String passWord = "nicuteam";

	/*---SP---*/
	@GetMapping("/ERDISP/{hisid}/{caseno}")
	public List<Object> ERDISP(@PathVariable String hisid, @PathVariable String caseno) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.ERDISP(?,?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, caseno);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/DISDISP/{hisid}/{caseno}")
	public List<Object> DISDISP(@PathVariable String hisid, @PathVariable String caseno) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.DISDISP(?,?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, caseno);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/ADMDISP/{hisid}/{caseno}")
	public List<Object> ADMDISP(@PathVariable String hisid, @PathVariable String caseno) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.ADMDISP(?,?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, caseno);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/PRGTXQRY/{date}/{hisid}/{caseno}")
	public List<Object> PRGTXQRY(@PathVariable String date, @PathVariable String hisid, @PathVariable String caseno) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.PRGTXQRY(?,?,?,?,?)");

			cs.setString(1, date);
			cs.setString(2, hisid);
			cs.setString(3, caseno);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getString(4) + "_" + cs.getInt(5));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/RESSECT/{month}/{hisid}/{dpt}/{docid}")
	public List<Object> RESSECT(@PathVariable String month, @PathVariable String hisid, @PathVariable String dpt,
			@PathVariable String docid) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESSECT(?,?,?,?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, dpt);
			cs.setString(3, month);
			cs.setString(4, docid);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.registerOutParameter(6, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(5) + "_" + cs.getString(6));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/RESDISP/{month}/{hisid}/{caseno}/{seq}")
	public List<Object> RESDISP(@PathVariable String month, @PathVariable String hisid, @PathVariable String caseno,
			@PathVariable Integer seq) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESDISP(?,?,?,?,?)");

			cs.setString(1, month);
			cs.setString(2, hisid);
			cs.setString(3, caseno);
			cs.setInt(4, seq);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(5));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/ADMLIST/{hisid}/{docid}")
	public List<Object> ADMLIST(@PathVariable String hisid, @PathVariable String docid) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.ADMLIST(?,?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, docid);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/DISLIST/{hisid}/{docid}")
	public List<Object> DISLIST(@PathVariable String hisid, @PathVariable String docid) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.DISLIST(?,?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, docid);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/DTAROTQ4/{hisid}")
	public List<Object> DTAROTQ4(@PathVariable String hisid) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.DTAROTQ4(?,?)");

			cs.setString(1, hisid);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(2));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i),rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/DTASOAPQ/{hisid}/{date}/{dpt}")
	public List<Object> DTASOAPQ(@PathVariable String hisid, @PathVariable String date, @PathVariable String dpt) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.DTASOAPQ(?,?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, date);
			cs.setString(3, dpt);
			cs.registerOutParameter(4, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(4));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/ORDLIST/{hisid}/{date}/{dpt}/{docid}")
	public List<Object> ORDLIST(@PathVariable String hisid, @PathVariable String date, @PathVariable String dpt,
			@PathVariable String docid) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.ORDLIST(?,?,?,?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, dpt);
			cs.setString(3, date);
			cs.setString(4, docid);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.registerOutParameter(6, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(5) + "_" + cs.getString(6));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					//System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/RESLAB01/{hisid}/{date}")
	public List<Object> RESLAB01(@PathVariable String hisid, @PathVariable String date) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESLAB01(?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, date);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					//System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/RESLAB02/{hisid}/{date}")
	public List<Object> RESLAB02(@PathVariable String hisid, @PathVariable String date) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESLAB02(?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, date);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					//System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/RESDGLU1/{hisid}/{date}")
	public List<Object> RESDGLU1(@PathVariable String hisid, @PathVariable String date) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESDGLU1(?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, date);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					//System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/RESDBGAS/{hisid}/{date}")
	public List<Object> RESDBGAS(@PathVariable String hisid, @PathVariable String date) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESDBGAS(?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, date);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					//System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/RESLAB0C/{hisid}/{date}")
	public List<Object> RESLAB0C(@PathVariable String hisid, @PathVariable String date) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESLAB0C(?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, date);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					//System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/TRTMNTQ1/{hisid}/{caseno}")
	public List<Object> TRTMNTQ1(@PathVariable String hisid, @PathVariable String caseno) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.TRTMNTQ1(?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, caseno);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					//System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/UDORDER0/{hisid}/{caseno}/{seq}")
	public List<Object> UDORDER0(@PathVariable String hisid, @PathVariable String caseno, @PathVariable String seq) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.UDORDER0(?,?,?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, caseno);
			cs.setString(3, seq);
			cs.registerOutParameter(4, Types.INTEGER);
			cs.registerOutParameter(5, Types.VARCHAR);
			cs.execute();
			//System.out.println("Logger_info_1-->" + cs.getInt(4) + "_" + cs.getString(5));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					//System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/UDTEXTQ1/{caseno}/{seq}")
	public List<Object> UDTEXTQ1(@PathVariable String caseno, @PathVariable String seq) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.UDTEXTQ1(?,?,?)");

			cs.setString(1, caseno);
			cs.setString(2, seq);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			//System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					//System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/CPSLIST/{hisid}/{docid}")
	public List<Object> CPSLIST(@PathVariable String hisid, @PathVariable String docid) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.CPSLIST(?,?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, docid);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			//System.out.println("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					//System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) ,rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

	@GetMapping("/CPSDISP/{hisid}/{caseno}/{seq}")
	public List<Object> CPSDISP(@PathVariable String hisid, @PathVariable String caseno, @PathVariable String seq) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.CPSDISP(?,?,?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, caseno);
			cs.setString(3, seq);
			cs.registerOutParameter(4, Types.INTEGER);
			cs.registerOutParameter(5, Types.VARCHAR);
			cs.execute();
			//System.out.println("Logger_info_1-->" + cs.getInt(4) + "_" + cs.getString(5));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					//System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}
	
	@GetMapping("/PCASELIST/{hisid}/{docid}/{ttype}")
	public List<Object> PCASELIST(@PathVariable String hisid, @PathVariable String docid, @PathVariable String ttype) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;
		List<Object> data = new ArrayList<Object>();

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.PCASLIST(?,?,?,?,?)");

			cs.setString(1, hisid);
			cs.setString(2, docid);
			cs.setString(3, ttype);
			cs.registerOutParameter(4, Types.INTEGER);
			cs.registerOutParameter(5, Types.VARCHAR);
			cs.execute();
			//System.out.println("Logger_info_1-->" + cs.getInt(4) + "_" + cs.getString(5));

			rs = (ResultSet) cs.executeQuery();

			while (rs.next()) {// 1~60
				Map<Object, Object> filter = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					//System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					filter.put(rs.getMetaData().getColumnName(i) , rs.getString(i));
				}
				data.add(filter);
			}

			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
		}
		return data;
	}

}
