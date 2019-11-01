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
			logger.info("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("ERDISP.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {// 1~60
				for (int i = 1; i <= 60; i++) {
					logger.info("Logger_info_2-->" + rs.getString(i) + "\r\n");
					out.write(rs.getString(i) + "\r\n");
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

			cs.setString(1, "45655310");
			cs.setString(2, "25026885");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			logger.info("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("DISDISP.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				logger.info("Logger_info_2-->" + rs.getString(1));
				out.write(rs.getString(1));
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
			logger.info("Logger_info_1-->" + cs.getInt(3) + "_" + cs.getString(4));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("ADMDISP.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				logger.info("Logger_info_2-->" + rs.getString(1));
				out.write(rs.getString(1));
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
			cs.setString(2, "45655310");
			cs.setString(3, "25026885");
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.execute();
			logger.info("Logger_info_1-->" + cs.getString(4) + "_" + cs.getInt(5));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("PRGTXQRY.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				logger.info("Logger_info_2-->" + rs.getString(1) + "\r\n" + rs.getString(2) + "\r\n" + rs.getString(3)
						+ "\r\n" + rs.getString(4) + "\r\n" + rs.getString(5) + "\r\n" + rs.getString(6) + "\r\n"
						+ rs.getString(7) + "\r\n" + rs.getString(8) + "\r\n" + rs.getString(9) + "\r\n"
						+ rs.getString(10) + "\r\n" + rs.getString(11) + "\r\n");
				out.write(rs.getString(1) + "\r\n" + rs.getString(2) + "\r\n" + rs.getString(3) + "\r\n"
						+ rs.getString(4) + "\r\n" + rs.getString(5) + "\r\n" + rs.getString(6) + "\r\n"
						+ rs.getString(7) + "\r\n" + rs.getString(8) + "\r\n" + rs.getString(9) + "\r\n"
						+ rs.getString(10) + "\r\n" + rs.getString(11) + "\r\n");
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
			logger.info("Logger_info_1-->" + cs.getInt(5) + "_" + cs.getString(6));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("RESSECT.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				logger.info("Logger_info_2-->" + rs.getString(1) + "\r\n" + rs.getString(2) + "\r\n" + rs.getString(3)
						+ "\r\n" + rs.getString(4) + "\r\n" + rs.getString(5) + "\r\n" + rs.getString(6) + "\r\n"
						+ rs.getString(7) + "\r\n" + rs.getString(8) + "\r\n" + rs.getString(9) + "\r\n"
						+ rs.getString(10) + "\r\n" + rs.getString(11) + "\r\n" + rs.getString(12) + "\r\n"
						+ rs.getString(13) + "\r\n" + rs.getString(14) + "\r\n");
				out.write(rs.getString(1) + "\r\n" + rs.getString(2) + "\r\n" + rs.getString(3) + "\r\n"
						+ rs.getString(4) + "\r\n" + rs.getString(5) + "\r\n" + rs.getString(6) + "\r\n"
						+ rs.getString(7) + "\r\n" + rs.getString(8) + "\r\n" + rs.getString(9) + "\r\n"
						+ rs.getString(10) + "\r\n" + rs.getString(11) + "\r\n" + rs.getString(12) + "\r\n"
						+ rs.getString(13) + "\r\n" + rs.getString(14) + "\r\n");
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
			logger.info("Logger_info_1-->" + cs.getInt(5));

			rs = (ResultSet) cs.executeQuery();

			File writename = new File("RESDISP.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				logger.info("Logger_info_2-->" + rs.getString(1) + "\r\n" + rs.getString(2) + "\r\n" + rs.getString(3)
						+ "\r\n" + rs.getString(4) + "\r\n" + rs.getString(5) + "\r\n" + rs.getString(6) + "\r\n"
						+ rs.getString(7));
				out.write(
						rs.getString(1) + "\r\n" + rs.getString(2) + "\r\n" + rs.getString(3) + "\r\n" + rs.getString(4)
								+ "\r\n" + rs.getString(5) + "\r\n" + rs.getString(6) + "\r\n" + rs.getString(7));
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

//	@GetMapping("/PBASINFO")
//	public void PBASINFO() {
//
//		Connection conn = null;
//		ResultSet rs = null;
//		Statement st;
//
//		try {
//			Class.forName(driver).newInstance();
//
//			conn = (Connection) DriverManager.getConnection(url, userName, passWord);
//
//			st = (Statement) conn.createStatement();
//
//			cs.setString(1, "10");
//			cs.setString(2, "45655310");
//			cs.setString(3, "25026885");
//			cs.setInt(4, 855);
//			cs.registerOutParameter(5, Types.INTEGER);
//			cs.execute();
//			logger.info("Logger_info_1-->" + cs.getInt(5));
//
//			rs = (ResultSet) cs.executeQuery();
//
//			File writename = new File("RESDISP.txt");
//			writename.createNewFile();
//			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
//
//			while (rs.next()) {
//				logger.info("Logger_info_2-->" + rs.getString(1) + "\r\n" + rs.getString(2) + "\r\n" + rs.getString(3)
//						+ "\r\n" + rs.getString(4) + "\r\n" + rs.getString(5) + "\r\n" + rs.getString(6) + "\r\n"
//						+ rs.getString(7));
//				out.write(
//						rs.getString(1) + "\r\n" + rs.getString(2) + "\r\n" + rs.getString(3) + "\r\n" + rs.getString(4)
//								+ "\r\n" + rs.getString(5) + "\r\n" + rs.getString(6) + "\r\n" + rs.getString(7));
//				out.flush();
//			}
//
//			out.close();
//			cs.close();
//			conn.close();
//
//		} catch (Exception e) {
//			System.out.println("error:" + e.getMessage());
//			System.out.println(e.toString());
//			logger.info("error:" + e.getMessage());
//			logger.info(e.toString());
//		}
//	}
}
