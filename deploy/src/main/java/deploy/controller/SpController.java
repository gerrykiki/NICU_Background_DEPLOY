package deploy.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.Types;

import org.springframework.web.bind.annotation.GetMapping;
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
	@GetMapping("/ERDISP")
	public void ERDISP() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.ERDISP(?,?,?,?)");

			cs.setString(1, "45655310");
			cs.setString(2, "25026885");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));
			
			rs = (ResultSet) cs.executeQuery();

			File writename = new File("ERDISP.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			// System.out.println("Meta--> " + rs.getMetaData());

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/DISDISP")
	public void DISDISP() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.DISDISP(?,?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "25026885");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("DISDISP.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/ADMDISP")
	public void ADMDISP() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.ADMDISP(?,?,?,?)");

			cs.setString(1, "45655310");
			cs.setString(2, "25026885");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("ADMDISP.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			// System.out.println("Logger_info_1-->" + rs.getMetaData());

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/PRGTXQRY")
	public void PRGTXQRY() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.PRGTXQRY(?,?,?,?,?)");

			cs.setString(1, "606");
			cs.setString(2, "41974941");
			cs.setString(3, "25026885");
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getString(4) + "_" + cs.getInt(5));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("PRGTXQRY.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/RESSECT")
	public void RESSECT() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESSECT(?,?,?,?,?,?)");

			cs.setString(1, "45655310");
			cs.setString(2, "ALL");
			cs.setString(3, "12");
			cs.setString(4, "DOC3924B");
			cs.registerOutParameter(5, Types.INTEGER);
			cs.registerOutParameter(6, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(5) + "_" + cs.getString(6));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("RESSECT.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			//System.out.println("Meta --> " + rs.getMetaData());

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}

				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/RESDISP")
	public void RESDISP() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESDISP(?,?,?,?,?)");

			cs.setString(1, "10");
			cs.setString(2, "45655310");
			cs.setString(3, "25026885");
			cs.setInt(4, 855);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(5));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("RESDISP.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/ADMLIST")
	public void ADMLIST() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.ADMLIST(?,?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "DOC3924B");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("ADMLIST.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/DISLIST")
	public void DISLIST() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.DISLIST(?,?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "DOC3924B");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("DISLIST.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/DTAROTQ4")
	public void DTAROTQ4() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.DTAROTQ4(?,?)");

			cs.setString(1, "41974941");
			cs.registerOutParameter(2, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(2));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("DTAROTQ4.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/DTASOAPQ")
	public void DTASOAPQ() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.DTASOAPQ(?,?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "2019-08-13");
			cs.setString(3, "ALL");
			cs.registerOutParameter(4, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(4));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("DTASOAPQ.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/ORDLIST")
	public void ORDLIST() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.ORDLIST(?,?,?,?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "ALL");
			cs.setString(3, "2019-01-05");
			cs.setString(4, "DOC3924B");
			cs.registerOutParameter(5, Types.INTEGER);
			cs.registerOutParameter(6, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(5) + "_" + cs.getString(6));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("ORDLIST.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/RESLAB01")
	public void RESLAB01() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESLAB01(?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "2019-01-05");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("RESLAB01.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/RESLAB02")
	public void RESLAB02() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESLAB02(?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "2019-01-05");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("RESLAB02.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/RESDGLU1")
	public void RESDGLU1() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESDGLU1(?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "2019-01-05");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("RESDGLU1.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/RESDBGAS")
	public void RESDBGAS() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESDBGAS(?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "2019-01-05");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("RESDBGAS.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/RESLAB0C")
	public void RESLAB0C() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.RESLAB0C(?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "2019-01-05");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("RESLAB0C.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/TRTMNTQ1")
	public void TRTMNTQ1() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.TRTMNTQ1(?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "25026885");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("TRTMNTQ1.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/UDORDER0")
	public void UDORDER0() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.UDORDER0(?,?,?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "25026885");
			cs.setString(3, "001");
			cs.registerOutParameter(4, Types.INTEGER);
			cs.registerOutParameter(5, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(4) + "_" + cs.getString(5));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("UDORDER0.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/UDTEXTQ1")
	public void UDTEXTQ1() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.UDTEXTQ1(?,?,?)");

			cs.setString(1, "25026885");
			cs.setString(2, "0001");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("UDTEXTQ1.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/CPSLIST")
	public void CPSLIST() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.CPSLIST(?,?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "DOC3924B");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("CPSLIST.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/CPSDISP")
	public void CPSDISP() {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			cs = (CallableStatement) conn.prepareCall("CALL VGHTPEVG.CPSDISP(?,?,?,?,?)");

			cs.setString(1, "41974941");
			cs.setString(2, "25026885");
			cs.setString(3, "0001");
			cs.registerOutParameter(4, Types.INTEGER);
			cs.registerOutParameter(5, Types.VARCHAR);
			cs.execute();
			System.out.println("Logger_info_1-->" + cs.getInt(4) + "_" + cs.getString(5));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("CPSDISP.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Col --> " + rs.getMetaData().getColumnName(i) + " : " + rs.getString(i));
					out.write(rs.getMetaData().getColumnName(i) + " : " + rs.getString(i) + "\r\n");
				}
				out.flush();
			}

			out.close();
			cs.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

}
