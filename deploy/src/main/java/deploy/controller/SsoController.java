package deploy.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.Statement;
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
public class SsoController {
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

	@PostMapping("/nicu/login_a")
	public String login_action(@RequestBody Bodyloginaction la) throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
		
		logger.info(la.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Bodyloginaction> entityLA = new HttpEntity<Bodyloginaction>(la, headers);
		
		logger.info(entityLA.toString());
		
		ResponseEntity<String> resp = restTemplate.exchange("https://eipstage.vghtpe.gov.tw/login.php",
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

	

	

}
