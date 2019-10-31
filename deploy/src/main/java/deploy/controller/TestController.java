package deploy.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.persistence.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.ibm.db2.jcc.am.CallableStatement;
import com.ibm.db2.jcc.am.Connection;
import com.ibm.db2.jcc.am.ResultSet;

import deploy.model.*;

@RestController
public class TestController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	RestTemplate restTemplate = new RestTemplate();

	/*--ignore SSL--*/
	HostnameVerifier hv = new HostnameVerifier() {

		@Override
		public boolean verify(String hostname, SSLSession session) {
			System.out.println("Warning: URL Host: " + hostname + " vs. " + session.getPeerHost());
			return true;
		}
	};

	private static void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
	}
	/*--ignore SSL--*/

	@PersistenceContext
	private EntityManager em;

	@GetMapping("/hello")
	public String hello2() {
		return "Hello Spring!";
	}

	/*---SSO---*/
	@PostMapping("/nicu/login")
	public String Login(@RequestBody BodyAuth auth) throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		Map<String, String> map = new HashMap<>();
		map.put("APP_PRIVATE_ID", auth.getAPP_PRIVATE_ID());
		map.put("APP_PRIVATE_PASSWD", auth.getAPP_PRIVATE_PASSWD());

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(map, headers);

		String resp = restTemplate.exchange("https://eipstage-api.vghtpe.gov.tw/app/request_basic_authentication/",
				HttpMethod.POST, entity, String.class).getBody();

		return resp;
	}

	@PostMapping("/nicu/login_action")
	public String login_action(@RequestBody Bodyloginaction la) throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Bodyloginaction> entityLA = new HttpEntity<Bodyloginaction>(la, headers);

		ResponseEntity<String> resp = restTemplate.exchange("https://eipstage.vghtpe.gov.tw/login_action.php",
				HttpMethod.POST, entityLA, String.class);
		String login_action = resp.getBody();

		return login_action;
	}

	@GetMapping("/nicu/login_check")
	public String login_check() throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entityLC = new HttpEntity<String>(headers);

		ResponseEntity<String> resp = restTemplate.exchange("https://eipstage.vghtpe.gov.tw/login_check.php",
				HttpMethod.GET, entityLC, String.class);
		String login_check = resp.getBody();

		return login_check;
	}

	@GetMapping("/nicu/token_auth")
	public String token_auth() throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entityTA = new HttpEntity<String>(headers);

		ResponseEntity<String> resp = restTemplate.exchange("https://eipstage.vghtpe.gov.tw/token_auth.php",
				HttpMethod.GET, entityTA, String.class);
		String token_auth = resp.getBody();

		return token_auth;
	}

	@PostMapping("/nicu/get_uuid")
	public String get_uuid(@RequestBody BodyGetUuid uuid) throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<BodyGetUuid> entityUUID = new HttpEntity<BodyGetUuid>(uuid, headers);

		ResponseEntity<String> resp = restTemplate.exchange(
				"https://eipstage-api.vghtpe.gov.tw/app_user/get_node_uuid/", HttpMethod.POST, entityUUID,
				String.class);
		String uuidd = resp.getBody();

		return uuidd;
	}

	@PostMapping("/nicu/get_user_profile")
	public String get_user_profile(@RequestBody BodyGetUserProfile user) throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<BodyGetUserProfile> entityUSER = new HttpEntity<BodyGetUserProfile>(user, headers);

		ResponseEntity<String> resp = restTemplate.exchange(
				"https://eipstage-api.vghtpe.gov.tw/org_tree_surrogate/get_user_node/", HttpMethod.POST, entityUSER,
				String.class);
		String uuidd = resp.getBody();

		return uuidd;
	}

	@GetMapping("/nicu/nnis")
	public String nnis() throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		// MappingJackson2XmlHttpMessageConverter jsonHttpMessageConverter = new
		// MappingJackson2XmlHttpMessageConverter();
		// jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,
		// false);
		// restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);

		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.121.11.180:8080/NPIWS/service/Nnis/QOXY/maya/25026885/201901010000/201901020000",
				HttpMethod.GET, entityNNIS, String.class);
		String nnis = resp.getBody();

		return nnis;
	}

	@GetMapping("/nicu/nnis2")
	public String nnis2() throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		// MappingJackson2XmlHttpMessageConverter jsonHttpMessageConverter = new
		// MappingJackson2XmlHttpMessageConverter();
		// jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,
		// false);
		// restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS2 = new HttpEntity<String>(headers);

		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.121.11.180:8080/NPIWS/service/Nnis/QOXY/nima/25026885/201901010000/201901020000",
				HttpMethod.GET, entityNNIS2, String.class);
		String nnis2 = resp.getBody();

		return nnis2;
	}

	String driver = "com.ibm.db2.jcc.DB2Driver";
	String url = "jdbc:db2://dbconnt.vghtpe.gov.tw:50000/VGHDBP";
	String userName = "XVGH96";
	String passWord = "nicuteam";

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

//		JsonParser parser = JsonParserFactory.getJsonParser();
//		Map<String, Object> map = parser.parseMap(resp.getBody());
//		map.put("JWT", "12345");
//
//		return map;

	/*--SP--
	@GetMapping("/erdisp")
	public List<Erdisp> Erdisp() {
		StoredProcedureQuery erdisp = em.createStoredProcedureQuery("ERDISP");
		erdisp.setParameter("THISTNO", "");
		erdisp.setParameter("TCASENO", "");
		erdisp.execute();
	
		logger.info("RET = " + erdisp.getOutputParameterValue("RET"));
		logger.info("RETMSG" + erdisp.getOutputParameterValue("RETMSG"));
	
		List<Erdisp> erd = erdisp.getResultList();
	
		return erd;
	}
	
	@GetMapping("/disdisp")
	public List<Disdisp> Disdisp() {
		StoredProcedureQuery disdisp = em.createStoredProcedureQuery("DISDISP");
		disdisp.setParameter("THISTNO", "");
		disdisp.setParameter("TCASENO", "");
		disdisp.execute();
	
		logger.info("RET = " + disdisp.getOutputParameterValue("RET"));
		logger.info("RETMSG" + disdisp.getOutputParameterValue("RETMSG"));
	
		List<Disdisp> dis = disdisp.getResultList();
	
		return dis;
	}
	
	@GetMapping("/admdisp")
	public List<String> Admdisp() {
		StoredProcedureQuery admdisp = em.createStoredProcedureQuery("ADMDISP");
		admdisp.registerStoredProcedureParameter("THISTNO", String.class, ParameterMode.IN);
		admdisp.registerStoredProcedureParameter("TCASENO", String.class, ParameterMode.IN);
		admdisp.setParameter("THISTNO", "45655310");// '4'+'5'+'6'+'5'+'5'+'3'+'1'+'0'
		admdisp.setParameter("TCASENO", "25026885");// '2'+'5'+'0'+'2'+'6'+'8'+'8'+'5'
		admdisp.execute();
	
		logger.info("RET = " + admdisp.getOutputParameterValue("RET"));
		logger.info("RETMSG" + admdisp.getOutputParameterValue("RETMSG"));
	
		return admdisp.getResultList();
	}
	
	
	 * @GetMapping("/prgtxqry") public List<Prgtxqry> Prgtxqry() {
	 * StoredProcedureQuery prgtxqry = em.createStoredProcedureQuery("PRGTXQRY");
	 * prgtxqry.setParameter("HISTNO", ""); prgtxqry.setParameter("CASENO", "");
	 * prgtxqry.setParameter("PRGPART", ""); prgtxqry.execute();
	 * 
	 * logger.info("RET = " + prgtxqry.getOutputParameterValue("RET"));
	 * logger.info("RETMSG" + prgtxqry.getOutputParameterValue("RETMSG"));
	 * 
	 * List<Prgtxqry> prg = prgtxqry.getResultList();
	 * 
	 * return prg; }
	 * 
	 * @GetMapping("/ressect") public List<Ressect> Ressect() { StoredProcedureQuery
	 * ressect = em.createStoredProcedureQuery("RESSECT");
	 * ressect.setParameter("THISTNO", ""); ressect.setParameter("TDEPT", "");
	 * ressect.setParameter("TMONTH", ""); ressect.setParameter("TSIGNID", "");
	 * ressect.execute();
	 * 
	 * logger.info("RET = " + ressect.getOutputParameterValue("RET"));
	 * logger.info("RETMSG" + ressect.getOutputParameterValue("RETMSG"));
	 * 
	 * List<Ressect> res = ressect.getResultList();
	 * 
	 * return res; }
	 * 
	 * @GetMapping("/resdisp") public List<Resdisp> Resdisp() { StoredProcedureQuery
	 * resdisp = em.createStoredProcedureQuery("RESDISP");
	 * resdisp.setParameter("TPARTNO", ""); resdisp.setParameter("THISTNO", "");
	 * resdisp.setParameter("TCASENO", ""); resdisp.setParameter("TORDSEQ", 0);
	 * resdisp.execute();
	 * 
	 * logger.info("RET = " + resdisp.getOutputParameterValue("RET"));
	 * 
	 * List<Resdisp> rsd = resdisp.getResultList();
	 * 
	 * return rsd; }
	 * 
	 * @GetMapping("/pcaselist") public List<Pcaselist> Rcaselist() {
	 * StoredProcedureQuery rcaselist = em.createStoredProcedureQuery("PCASELIST");
	 * rcaselist.execute();
	 * 
	 * List<Pcaselist> rclist = rcaselist.getResultList();
	 * 
	 * return rclist; }
	 * 
	 * @GetMapping("/pbasinfo") public List<Pbasinfo> Pbasinfo() {
	 * StoredProcedureQuery pbasinfo = em.createStoredProcedureQuery("PBASINFO");
	 * pbasinfo.execute();
	 * 
	 * List<Pbasinfo> list = pbasinfo.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/hbed") public List<Hbed> Hbed() { StoredProcedureQuery hbed =
	 * em.createStoredProcedureQuery("HBED"); hbed.execute();
	 * 
	 * List<Hbed> list = hbed.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/psection") public List<Psection> Psection() {
	 * StoredProcedureQuery psection = em.createStoredProcedureQuery("PSECTION");
	 * psection.execute();
	 * 
	 * List<Psection> list = psection.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/ploc") public List<Ploc> Ploc() { StoredProcedureQuery sp =
	 * em.createStoredProcedureQuery("PLOC"); sp.execute();
	 * 
	 * List<Ploc> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/admlist") public List<Admlist> Admlist() { StoredProcedureQuery
	 * sp = em.createStoredProcedureQuery("ADMLIST"); sp.execute();
	 * 
	 * List<Admlist> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/dislist") public List<Dislist> Dislist() { StoredProcedureQuery
	 * sp = em.createStoredProcedureQuery("DISLIST"); sp.execute();
	 * 
	 * List<Dislist> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/dtarotq4") public List<Dtarotq4> Dtarotq4() {
	 * StoredProcedureQuery sp = em.createStoredProcedureQuery("DTAROTQ4");
	 * sp.execute();
	 * 
	 * List<Dtarotq4> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/dtasoapq") public List<Dtasoapq> Dtasoapq() {
	 * StoredProcedureQuery sp = em.createStoredProcedureQuery("DTASOAPQ");
	 * sp.execute();
	 * 
	 * List<Dtasoapq> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/orlist") public List<Orlist> Orlist() { StoredProcedureQuery sp
	 * = em.createStoredProcedureQuery("ORLIST"); sp.execute();
	 * 
	 * List<Orlist> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/reslab01") public List<Reslab01> Reslab01() {
	 * StoredProcedureQuery sp = em.createStoredProcedureQuery("RESLAB01");
	 * sp.execute();
	 * 
	 * List<Reslab01> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/reslab02") public List<Reslab02> Reslab02() {
	 * StoredProcedureQuery sp = em.createStoredProcedureQuery("RESLAB02");
	 * sp.execute();
	 * 
	 * List<Reslab02> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/resurin") public List<Resurin> Resurin() { StoredProcedureQuery
	 * sp = em.createStoredProcedureQuery("RESURIN"); sp.execute();
	 * 
	 * List<Resurin> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/resdbgas") public List<Resdbgas> Resdbgas() {
	 * StoredProcedureQuery sp = em.createStoredProcedureQuery("RESDBGAS");
	 * sp.execute();
	 * 
	 * List<Resdbgas> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/reslaboc") public List<Reslaboc> Reslaboc() {
	 * StoredProcedureQuery sp = em.createStoredProcedureQuery("RESLABOC");
	 * sp.execute();
	 * 
	 * List<Reslaboc> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/trtmnt01") public List<Trtmnt01> Trtmnt01() {
	 * StoredProcedureQuery sp = em.createStoredProcedureQuery("TRTMNT01");
	 * sp.execute();
	 * 
	 * List<Trtmnt01> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/resoxwd") public List<Resoxwd> Resoxwd() { StoredProcedureQuery
	 * sp = em.createStoredProcedureQuery("RESOXWD"); sp.execute();
	 * 
	 * List<Resoxwd> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/udordero") public List<Udordero> Udordero() {
	 * StoredProcedureQuery sp = em.createStoredProcedureQuery("UDORDERO");
	 * sp.execute();
	 * 
	 * List<Udordero> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/udtextq1") public List<Udtextq1> Udtextq1() {
	 * StoredProcedureQuery sp = em.createStoredProcedureQuery("UDTEXTQ1");
	 * sp.execute();
	 * 
	 * List<Udtextq1> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/cpslist") public List<Cpslist> Cpslist() { StoredProcedureQuery
	 * sp = em.createStoredProcedureQuery("CPSLIST"); sp.execute();
	 * 
	 * List<Cpslist> list = sp.getResultList();
	 * 
	 * return list; }
	 * 
	 * @GetMapping("/cpsdisp") public List<Cpsdisp> Cpsdisp() { StoredProcedureQuery
	 * sp = em.createStoredProcedureQuery("CPSDISP"); sp.execute();
	 * 
	 * List<Cpsdisp> list = sp.getResultList();
	 * 
	 * return list; }
	 */

}
