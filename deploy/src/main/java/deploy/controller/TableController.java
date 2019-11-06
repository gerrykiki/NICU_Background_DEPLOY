package deploy.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.db2.jcc.am.Connection;
import com.ibm.db2.jcc.am.ResultSet;

@RestController
public class TableController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	String driver = "com.ibm.db2.jcc.DB2Driver";
	String url = "jdbc:db2://dbconnt.vghtpe.gov.tw:50000/VGHDBP";
	String userName = "XVGH96";
	String passWord = "nicuteam";
	
	/*-Get DB table data-*/
	@GetMapping("/PBASINFO")
	public void PBASINFO() {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.PBASINFO");

			File writename = new File("PBASINFO.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				logger.info("Logger_info -->" + rs.getString(1) + "\r\n");
				out.write(rs.getString(1) + "\r\n");
				out.flush();
			}

			out.close();
			st.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/HBED")
	public void HBED() {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			rs = (ResultSet) st.executeQuery("SELECT COLUMN_NAME,ORDINAL_POSITION,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME=HBED");

			File writename = new File("HBED.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				logger.info("Logger_info -->" + rs.getString(1) + "\r\n");
				out.write(rs.getString(1) + "\r\n");
				out.flush();
			}

			out.close();
			st.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/PSECTION")
	public void PSECTION() {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.PSECTION");

			File writename = new File("PSECTION.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				logger.info("Logger_info -->" + rs.getString(1) + "\r\n");
				out.write(rs.getString(1) + "\r\n");
				out.flush();
			}

			out.close();
			st.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}

	@GetMapping("/PLOC")
	public void PLOC() {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.PLOC");

			File writename = new File("PLOC.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				logger.info("Logger_info -->" + rs.getString(1) + "\r\n");
				out.write(rs.getString(1) + "\r\n");
				out.flush();
			}

			out.close();
			st.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}
	
	@GetMapping("/PDOCNEW")
	public void PDOCNEW() {

		Connection conn = null;
		ResultSet rs = null;
		Statement st;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName, passWord);

			st = (Statement) conn.createStatement();

			rs = (ResultSet) st.executeQuery("SELECT * FROM VGHTPEVG.PDOCNEW");

			File writename = new File("PDOCNEW.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			while (rs.next()) {
				logger.info("Logger_info -->" + rs.getString(1) + "\r\n");
				out.write(rs.getString(1) + "\r\n");
				out.flush();
			}

			out.close();
			st.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			System.out.println(e.toString());
			logger.info("error:" + e.getMessage());
			logger.info(e.toString());
		}
	}
}
