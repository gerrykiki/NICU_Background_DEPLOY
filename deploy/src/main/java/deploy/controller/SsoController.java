package deploy.controller;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.SerializationFeature;

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

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Bodyloginaction> entityLA = new HttpEntity<Bodyloginaction>(la, headers);

		ResponseEntity<String> resp = restTemplate.exchange("https://eipstage.vghtpe.gov.tw/login.php", HttpMethod.POST,
				entityLA, String.class);

		// String login_action = resp;

		resp.getHeaders().get("Set-Cookie").stream().forEach(System.out::println);

		/*
		 * CookieManager cm = new CookieManager();
		 * cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL); CookieHandler.setDefault(cm);
		 * 
		 * new
		 * URL("https://eipstage.vghtpe.gov.tw/login.php").openConnection().getContent()
		 * ;
		 * 
		 * List<java.net.HttpCookie> cookies = cm.getCookieStore().getCookies(); for
		 * (java.net.HttpCookie cookie : cookies) { System.out.println("Name = " +
		 * cookie.getName()); System.out.println("Value = " + cookie.getValue());
		 * System.out.println("Lifetime (seconds) = " + cookie.getMaxAge());
		 * System.out.println("Path = " + cookie.getPath()); System.out.println(); }
		 */

		return "";

	}

//	@GetMapping("/nicu/login_check")
//	public String login_check() throws Exception {
//		trustAllHttpsCertificates();
//		HttpsURLConnection.setDefaultHostnameVerifier(hv);
//
//		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
//		jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		HttpEntity<String> entityLC = new HttpEntity<String>(headers);
//
//		ResponseEntity<String> resp = restTemplate.exchange("https://eipstage.vghtpe.gov.tw/login_check.php",
//				HttpMethod.GET, entityLC, String.class);
//		String login_check = resp.getBody();
//
//		return login_check;
//	}
//
//	@GetMapping("/nicu/token_auth")
//	public String token_auth() throws Exception {
//		trustAllHttpsCertificates();
//		HttpsURLConnection.setDefaultHostnameVerifier(hv);
//
//		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
//		jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		HttpEntity<String> entityTA = new HttpEntity<String>(headers);
//
//		ResponseEntity<String> resp = restTemplate.exchange("https://eipstage.vghtpe.gov.tw/token_auth.php",
//				HttpMethod.GET, entityTA, String.class);
//		String token_auth = resp.getBody();
//
//		return token_auth;
//	}

	@PostMapping("/nicu/get_uuid")
	public String get_uuid(@RequestBody BodyGetUuid uuid) throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		// MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new
		// MappingJackson2HttpMessageConverter();
		// jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,
		// false);
		// restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

		Map<String, String> jsss = new HashMap<>();
		jsss.put("PRIVILEGED_APP_SSO_TOKEN", "b8ecabdf-711b-4b82-a22f-a8bafdcc8abe");
		jsss.put("PUBLIC_APP_USER_SSO_TOKEN_TO_QUERY", "7e40161d-2be3-4804-94eb-2799f63883b5");

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Map<String, String>> entityUUID = new HttpEntity<Map<String, String>>(jsss, headers);

		System.out.println(entityUUID + " _ " + uuid);

		ResponseEntity<String> resp = restTemplate.exchange("http://eipstage-api.vghtpe.gov.tw/app_user/get_node_uuid/",
				HttpMethod.POST, entityUUID, String.class);
		String uuidd = resp.getBody();

		return uuidd;
	}

	@PostMapping("/nicu/get_user_profile")
	public String get_user_profile(@RequestBody BodyGetUserProfile user) throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		// MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new
		// MappingJackson2HttpMessageConverter();
		// jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,
		// false);
		// restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
		
		Map<Object, Object> jssss = new HashMap<>();
		jssss.put("APP_USER_CHT_NAME","");

		Map<String, Object> jsss = new HashMap<>();
		jsss.put("PRIVILEGED_APP_SSO_TOKEN", "b8ecabdf-711b-4b82-a22f-a8bafdcc8abe");
		jsss.put("PUBLIC_APP_USER_SSO_TOKEN", "7e40161d-2be3-4804-94eb-2799f63883b5");
		jsss.put("APP_COMPANY_UUID", "5fbca345-d7f7-43cd-b324-955182d3b66e");
		jsss.put("APP_USER_NODE_UUID", "83bf85b2-d246-436d-a912-d2f8fa499b07");
		jsss.put( "APP_USER_BASIC_PROFILE",jssss);
		
		

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Map<String, Object>> entityUSER = new HttpEntity<Map<String, Object>>(jsss, headers);

		ResponseEntity<String> resp = restTemplate.exchange(
				"http://eipstage-api.vghtpe.gov.tw/org_tree_surrogate/get_user_node/", HttpMethod.POST, entityUSER,
				String.class);
		String uuidd = resp.getBody();

		return uuidd;
	}

}
