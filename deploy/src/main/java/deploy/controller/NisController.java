package deploy.controller;

import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class NisController {

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

	/*---NIS---*/
	@GetMapping("/QSPE/{caseno}/{st}/{et}")//查詢特定時間特殊事件資訊
	public String QSPE(@PathVariable String caseno, @PathVariable String st, @PathVariable String et)
			throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);

		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.121.11.180:8080/NPIWS/service/Nnis/QSPE/NIMA/" + caseno + "/" + st + "/" + et,
				HttpMethod.GET, entityNNIS, String.class);
		String nnis = resp.getBody();

		return nnis;
	}
	
	@GetMapping("/QTWPER/{caseno}/{st}/{et}")//查詢特定時間身高體重/體圍測量資訊
	public String QTWPER(@PathVariable String caseno, @PathVariable String st, @PathVariable String et)
			throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);

		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.121.11.180:8080/NPIWS/service/Nnis/QTWPER/NIMA/" + caseno + "/" + st + "/" + et,
				HttpMethod.GET, entityNNIS, String.class);
		String nnis = resp.getBody();

		return nnis;
	}
	
	@GetMapping("/QPNOT/{caseno}/{st}/{et}")//查詢特定時間護理紀錄資訊
	public String QPNOT(@PathVariable String caseno, @PathVariable String st, @PathVariable String et)
			throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);

		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.121.11.180:8080/NPIWS/service/Nnis/QPNOT/NIMA/" + caseno + "/" + st + "/" + et,
				HttpMethod.GET, entityNNIS, String.class);
		String nnis = resp.getBody();

		return nnis;
	}
	
	@GetMapping("/QDVS/{caseno}/{st}/{et}")//查詢特定時間生命徵象資訊
	public String QDVS(@PathVariable String caseno, @PathVariable String st, @PathVariable String et)
			throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);

		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.121.11.180:8080/NPIWS/service/Nnis/QDVS/NIMA/" + caseno + "/" + st + "/" + et,
				HttpMethod.GET, entityNNIS, String.class);
		String nnis = resp.getBody();

		return nnis;
	}
	
	@GetMapping("/QOXY/{caseno}/{st}/{et}")//查詢特定時間氧合紀錄資訊
	public String QOXY(@PathVariable String caseno, @PathVariable String st, @PathVariable String et)
			throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);

		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.121.11.180:8080/NPIWS/service/Nnis/QOXY/NIMA/" + caseno + "/" + st + "/" + et,
				HttpMethod.GET, entityNNIS, String.class);
		String nnis = resp.getBody();

		return nnis;
	}
	
	@GetMapping("/QDC/{caseno}/{st}/{et}")//輸出入量(兒)
	public String QDC(@PathVariable String caseno, @PathVariable String st, @PathVariable String et)
			throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);

		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.121.11.180:8080/NPIWS/service/Nnis/QDC/NIMA/" + caseno + "/" + st + "/" + et,
				HttpMethod.GET, entityNNIS, String.class);
		String nnis = resp.getBody();

		return nnis;
	}

}
