package deploy.controller;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.SerializationFeature;

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

		String resp = restTemplate.exchange("https://eip-api.vghtpe.gov.tw/app/request_basic_authentication/",
				HttpMethod.POST, entity, String.class).getBody();

		return resp;
	}

	@PostMapping("/nicu/get_user")
	public Map<String, Object> get_user_profile(@RequestBody BodyAuth auth) throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		Map<String, String> mapB = new HashMap<>();
		mapB.put("APP_PRIVATE_ID", auth.getAPP_PRIVATE_ID());
		mapB.put("APP_PRIVATE_PASSWD", auth.getAPP_PRIVATE_PASSWD());
		
		HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(mapB, headers);

		ResponseEntity<String> resp = restTemplate.exchange(
				"https://eipstage-api.vghtpe.gov.tw/app/request_basic_authentication/", HttpMethod.POST, entity,
				String.class);

		String token = resp.getBody();
		logger.info("Token = " + token);
		
		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

		Map<String, Object> mapuser = null;

		JSONObject tokenn = new JSONObject(token);

		JSONObject inputJObj = new JSONObject();
		inputJObj.put("PRIVILEGED_APP_SSO_TOKEN", tokenn.get("PRIVILEGED_APP_SSO_TOKEN"));
		inputJObj.put("PUBLIC_APP_USER_SSO_TOKEN_TO_QUERY", tokenn.get("PUBLIC_APP_SSO_TOKEN"));
		
		logger.info(inputJObj.toString());

		if (resp.getStatusCode() == HttpStatus.OK) {
			HttpEntity<JSONObject> entityUUID = new HttpEntity<JSONObject>(inputJObj, headers);

			ResponseEntity<String> resp2 = restTemplate.exchange(
					"https://eipstage-api.vghtpe.gov.tw/app_user/get_node_uuid/", HttpMethod.POST, entityUUID, String.class);
			String uuid = resp2.getBody();
			logger.info("UUID = " + uuid);

			/*Map<String, Object> mapuuid = parser.parseMap(uuid);

			inputJObj = new JSONObject();
			inputJObj.put("PRIVILEGED_APP_SSO_TOKEN", map.get("PRIVILEGED_APP_SSO_TOKEN"));
			inputJObj.put("PUBLIC_APP_USER_SSO_TOKEN_TO_QUERY", map.get("PUBLIC_APP_SSO_TOKEN"));
			inputJObj.put("APP_COMPANY_UUID", mapuuid.get("APP_COMPANY_UUID"));
			inputJObj.put("APP_USER_NODE_UUID", mapuuid.get("APP_USER_NODE_UUID"));

			inputJObj.put("APP_USER_BASIC_PROFILE", new JSONObject());
			inputJObj.getJSONObject("APP_USER_BASIC_PROFILE").put("APP_USER_LOGIN_ID", "");
			inputJObj.getJSONObject("APP_USER_BASIC_PROFILE").put("APP_USER_EMPNO", "");
			inputJObj.getJSONObject("APP_USER_BASIC_PROFILE").put("APP_USER_CHT_NAME", "");
			inputJObj.getJSONObject("APP_USER_BASIC_PROFILE").put("APP_USER_ENG_NAME", "");
			inputJObj.getJSONObject("APP_USER_BASIC_PROFILE").put("APP_USER_OFFICE_PHONE_NO", "");
			inputJObj.getJSONObject("APP_USER_BASIC_PROFILE").put("APP_USER_MOBILE_PHONE_NO", "");
			inputJObj.getJSONObject("APP_USER_BASIC_PROFILE").put("APP_USER_EMAIL", "");
			inputJObj.getJSONObject("APP_USER_BASIC_PROFILE").put("APP_USER_OFFICE_FAX_NO", "");
			inputJObj.getJSONObject("APP_USER_BASIC_PROFILE").put("APP_USER_MFP_CARD_NO", "");
			inputJObj.getJSONObject("APP_USER_BASIC_PROFILE").put("APP_USER_OFFICE_ADDRESS", "");
			inputJObj.getJSONObject("APP_USER_BASIC_PROFILE").put("APP_USER_OFFICE_ZIP_CODE", "");
			inputJObj.getJSONObject("APP_USER_BASIC_PROFILE").put("APP_USER_STATUS", "");

			if (resp2.getStatusCode() == HttpStatus.OK) {
				HttpEntity<JSONObject> entityUSER = new HttpEntity<JSONObject>(inputJObj, headers);

				ResponseEntity<String> resp3 = restTemplate.exchange(
						"https://eip-api.vghtpe.gov.tw/org_tree/get_user_basic_profile/", HttpMethod.POST, entityUSER,
						String.class);
				String user = resp3.getBody();
				logger.info("User Profile = " + user);

				mapuser = parser.parseMap(user);
				mapuser.put("JWT", "12345");
			}*/
		}
		return mapuser;
	}

//	@PostMapping("/test")
//	public Map<String, Object> test(@RequestBody BodyAuth auth) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		HttpEntity<BodyAuth> entity = new HttpEntity<BodyAuth>(auth, headers);
//
//		ResponseEntity<String> resp = restTemplate.exchange("https://jsonplaceholder.typicode.com/posts",
//				HttpMethod.POST, entity, String.class);
//
//		JsonParser parser = JsonParserFactory.getJsonParser();
//		Map<String, Object> map = parser.parseMap(resp.getBody());
//		map.put("JWT", "12345");
//
//		return map;
//	}

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
	}*/

	@GetMapping("/admdisp")
	public List<String> Admdisp() {
		StoredProcedureQuery admdisp = em.createStoredProcedureQuery("ADMDISP");
		admdisp.registerStoredProcedureParameter("THISTNO", Character.class, ParameterMode.IN);
		admdisp.registerStoredProcedureParameter("TCASENO", Character.class, ParameterMode.IN);
		admdisp.setParameter("THISTNO", "45655310");
		admdisp.setParameter("TCASENO", "25026885");
		admdisp.execute();

		logger.info("RET = " + admdisp.getOutputParameterValue("RET"));
		logger.info("RETMSG" + admdisp.getOutputParameterValue("RETMSG"));

		return admdisp.getResultList();
	}

	/*@GetMapping("/prgtxqry")
	public List<Prgtxqry> Prgtxqry() {
		StoredProcedureQuery prgtxqry = em.createStoredProcedureQuery("PRGTXQRY");
		prgtxqry.setParameter("HISTNO", "");
		prgtxqry.setParameter("CASENO", "");
		prgtxqry.setParameter("PRGPART", "");
		prgtxqry.execute();

		logger.info("RET = " + prgtxqry.getOutputParameterValue("RET"));
		logger.info("RETMSG" + prgtxqry.getOutputParameterValue("RETMSG"));

		List<Prgtxqry> prg = prgtxqry.getResultList();

		return prg;
	}

	@GetMapping("/ressect")
	public List<Ressect> Ressect() {
		StoredProcedureQuery ressect = em.createStoredProcedureQuery("RESSECT");
		ressect.setParameter("THISTNO", "");
		ressect.setParameter("TDEPT", "");
		ressect.setParameter("TMONTH", "");
		ressect.setParameter("TSIGNID", "");
		ressect.execute();

		logger.info("RET = " + ressect.getOutputParameterValue("RET"));
		logger.info("RETMSG" + ressect.getOutputParameterValue("RETMSG"));

		List<Ressect> res = ressect.getResultList();

		return res;
	}

	@GetMapping("/resdisp")
	public List<Resdisp> Resdisp() {
		StoredProcedureQuery resdisp = em.createStoredProcedureQuery("RESDISP");
		resdisp.setParameter("TPARTNO", "");
		resdisp.setParameter("THISTNO", "");
		resdisp.setParameter("TCASENO", "");
		resdisp.setParameter("TORDSEQ", 0);
		resdisp.execute();

		logger.info("RET = " + resdisp.getOutputParameterValue("RET"));

		List<Resdisp> rsd = resdisp.getResultList();

		return rsd;
	}

	@GetMapping("/pcaselist")
	public List<Pcaselist> Rcaselist() {
		StoredProcedureQuery rcaselist = em.createStoredProcedureQuery("PCASELIST");
		rcaselist.execute();

		List<Pcaselist> rclist = rcaselist.getResultList();

		return rclist;
	}

	@GetMapping("/pbasinfo")
	public List<Pbasinfo> Pbasinfo() {
		StoredProcedureQuery pbasinfo = em.createStoredProcedureQuery("PBASINFO");
		pbasinfo.execute();

		List<Pbasinfo> list = pbasinfo.getResultList();

		return list;
	}

	@GetMapping("/hbed")
	public List<Hbed> Hbed() {
		StoredProcedureQuery hbed = em.createStoredProcedureQuery("HBED");
		hbed.execute();

		List<Hbed> list = hbed.getResultList();

		return list;
	}

	@GetMapping("/psection")
	public List<Psection> Psection() {
		StoredProcedureQuery psection = em.createStoredProcedureQuery("PSECTION");
		psection.execute();

		List<Psection> list = psection.getResultList();

		return list;
	}

	@GetMapping("/ploc")
	public List<Ploc> Ploc() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("PLOC");
		sp.execute();

		List<Ploc> list = sp.getResultList();

		return list;
	}

	@GetMapping("/admlist")
	public List<Admlist> Admlist() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("ADMLIST");
		sp.execute();

		List<Admlist> list = sp.getResultList();

		return list;
	}

	@GetMapping("/dislist")
	public List<Dislist> Dislist() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("DISLIST");
		sp.execute();

		List<Dislist> list = sp.getResultList();

		return list;
	}

	@GetMapping("/dtarotq4")
	public List<Dtarotq4> Dtarotq4() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("DTAROTQ4");
		sp.execute();

		List<Dtarotq4> list = sp.getResultList();

		return list;
	}

	@GetMapping("/dtasoapq")
	public List<Dtasoapq> Dtasoapq() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("DTASOAPQ");
		sp.execute();

		List<Dtasoapq> list = sp.getResultList();

		return list;
	}

	@GetMapping("/orlist")
	public List<Orlist> Orlist() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("ORLIST");
		sp.execute();

		List<Orlist> list = sp.getResultList();

		return list;
	}

	@GetMapping("/reslab01")
	public List<Reslab01> Reslab01() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("RESLAB01");
		sp.execute();

		List<Reslab01> list = sp.getResultList();

		return list;
	}

	@GetMapping("/reslab02")
	public List<Reslab02> Reslab02() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("RESLAB02");
		sp.execute();

		List<Reslab02> list = sp.getResultList();

		return list;
	}

	@GetMapping("/resurin")
	public List<Resurin> Resurin() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("RESURIN");
		sp.execute();

		List<Resurin> list = sp.getResultList();

		return list;
	}

	@GetMapping("/resdbgas")
	public List<Resdbgas> Resdbgas() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("RESDBGAS");
		sp.execute();

		List<Resdbgas> list = sp.getResultList();

		return list;
	}

	@GetMapping("/reslaboc")
	public List<Reslaboc> Reslaboc() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("RESLABOC");
		sp.execute();

		List<Reslaboc> list = sp.getResultList();

		return list;
	}

	@GetMapping("/trtmnt01")
	public List<Trtmnt01> Trtmnt01() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("TRTMNT01");
		sp.execute();

		List<Trtmnt01> list = sp.getResultList();

		return list;
	}

	@GetMapping("/resoxwd")
	public List<Resoxwd> Resoxwd() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("RESOXWD");
		sp.execute();

		List<Resoxwd> list = sp.getResultList();

		return list;
	}

	@GetMapping("/udordero")
	public List<Udordero> Udordero() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("UDORDERO");
		sp.execute();

		List<Udordero> list = sp.getResultList();

		return list;
	}

	@GetMapping("/udtextq1")
	public List<Udtextq1> Udtextq1() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("UDTEXTQ1");
		sp.execute();

		List<Udtextq1> list = sp.getResultList();

		return list;
	}

	@GetMapping("/cpslist")
	public List<Cpslist> Cpslist() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("CPSLIST");
		sp.execute();

		List<Cpslist> list = sp.getResultList();

		return list;
	}

	@GetMapping("/cpsdisp")
	public List<Cpsdisp> Cpsdisp() {
		StoredProcedureQuery sp = em.createStoredProcedureQuery("CPSDISP");
		sp.execute();

		List<Cpsdisp> list = sp.getResultList();

		return list;
	}*/

}
