package deploy.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//@Component
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
	@GetMapping("/QSPE/{caseno}/{st}/{et}") // 查詢特定時間特殊事件資訊
	public List<Map<Object, Object>> QSPE(@PathVariable String caseno, @PathVariable String st, @PathVariable String et)
			throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);
		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.97.235.18:9080/NPIWS/service/Nnis/QSPE/NIMA/" + caseno + "/" + st + "/" + et, HttpMethod.GET,
				entityNNIS, String.class);
		String nnis = resp.getBody();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		List<Map<Object, Object>> data = new ArrayList<Map<Object, Object>>();
		try {
			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(nnis));
			Document doc = builder.parse(is);
			NodeList rescont = doc.getElementsByTagName("rescont");

			for (int i = 0; i < rescont.getLength(); i++) {
				Node nNode = rescont.item(i);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					//if (eElement.getElementsByTagName("type").item(0).getTextContent().compareTo("TW") == 0) {
						Map<Object, Object> filter = new HashMap<>();
						filter.put("Special", eElement.getElementsByTagName("special").item(0).getTextContent());
						filter.put("Date", eElement.getElementsByTagName("rec_dat").item(0).getTextContent());
						data.add(filter);
					//}
				}
			}

		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
		
		return data;
	}

	@GetMapping("/QTWPER/{caseno}/{st}/{et}") // 查詢特定時間身高體重/體圍測量資訊
	public List<Map<Object, Object>> QTWPER(@PathVariable String caseno, @PathVariable String st,
			@PathVariable String et) throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);
		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.97.235.18:9080/NPIWS/service/Nnis/QTWPER/NIMA/" + caseno + "/" + st + "/" + et,
				HttpMethod.GET, entityNNIS, String.class);
		String nnis = resp.getBody();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		List<Map<Object, Object>> data = new ArrayList<Map<Object, Object>>();
		try {
			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(nnis));
			Document doc = builder.parse(is);
			NodeList rescont = doc.getElementsByTagName("rescont");

			for (int i = 0; i < rescont.getLength(); i++) {
				Node nNode = rescont.item(i);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					if (eElement.getElementsByTagName("type").item(0).getTextContent().compareTo("TW") == 0) {
						Map<Object, Object> filter = new HashMap<>();
						filter.put("Weight", eElement.getElementsByTagName("value2").item(0).getTextContent());
						filter.put("Date", eElement.getElementsByTagName("rec_dat").item(0).getTextContent());
						data.add(filter);
					}
				}
			}

		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}

		return data;
	}

		
	@GetMapping("/QPNOT/{caseno}/{st}/{et}") // 查詢特定時間護理紀錄資訊
	public List<Map<Object, Object>> QPNOT(@PathVariable String caseno, @PathVariable String st, @PathVariable String et)
			throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);
		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.97.235.18:9080/NPIWS/service/Nnis/QPNOT/NIMA/" + caseno + "/" + st + "/" + et,
				HttpMethod.GET, entityNNIS, String.class);
		String nnis = resp.getBody();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		List<Map<Object, Object>> data = new ArrayList<Map<Object, Object>>();
		try {
			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(nnis));
			Document doc = builder.parse(is);
			NodeList rescont = doc.getElementsByTagName("rescont");

			for (int i = 0; i < rescont.getLength(); i++) {
				Node nNode = rescont.item(i);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					//if (eElement.getElementsByTagName("type").item(0).getTextContent().compareTo("TW") == 0) {
						Map<Object, Object> filter = new HashMap<>();
						filter.put("Cre_Nam", eElement.getElementsByTagName("cre_nam").item(0).getTextContent());
						filter.put("Pro_Not", eElement.getElementsByTagName("pro_not").item(0).getTextContent());
						filter.put("Date", eElement.getElementsByTagName("rec_dat").item(0).getTextContent());
						data.add(filter);
					//}
				}
			}

		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
		
		return data;
	}

	@GetMapping("/QDVS/{caseno}/{st}/{et}") // 查詢特定時間生命徵象資訊
	public List<Map<Object, Object>> QDVS(@PathVariable String caseno, @PathVariable String st, @PathVariable String et)
			throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);
		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.97.235.18:9080/NPIWS/service/Nnis/QDVS/NIMA/" + caseno + "/" + st + "/" + et, HttpMethod.GET,
				entityNNIS, String.class);
		String nnis = resp.getBody();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		List<Map<Object, Object>> data = new ArrayList<Map<Object, Object>>();
		try {
			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(nnis));
			Document doc = builder.parse(is);
			NodeList rescont = doc.getElementsByTagName("rescont");

			for (int i = 0; i < rescont.getLength(); i++) {
				Node nNode = rescont.item(i);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					//if (eElement.getElementsByTagName("type").item(0).getTextContent().compareTo("TW") == 0) {
						Map<Object, Object> filter = new HashMap<>();
						filter.put("Breath", eElement.getElementsByTagName("breath").item(0).getTextContent());
						filter.put("Diastolic", eElement.getElementsByTagName("diastolic").item(0).getTextContent());
						filter.put("Pluse", eElement.getElementsByTagName("pluse").item(0).getTextContent());
						filter.put("Systolic", eElement.getElementsByTagName("systolic").item(0).getTextContent());
						filter.put("Temperature", eElement.getElementsByTagName("temperature").item(0).getTextContent());
						filter.put("Date", eElement.getElementsByTagName("rec_dat").item(0).getTextContent());
						data.add(filter);
					//}
				}
			}

		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}

		return data;
	}

	@GetMapping("/QOXY/{caseno}/{st}/{et}") // 查詢特定時間氧合紀錄資訊
	public List<Map<Object, Object>> QOXY(@PathVariable String caseno, @PathVariable String st, @PathVariable String et)
			throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);
		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.97.235.18:9080/NPIWS/service/Nnis/QOXY/NIMA/" + caseno + "/" + st + "/" + et, HttpMethod.GET,
				entityNNIS, String.class);
		String nnis = resp.getBody();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		List<Map<Object, Object>> data = new ArrayList<Map<Object, Object>>();
		try {
			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(nnis));
			Document doc = builder.parse(is);
			NodeList rescont = doc.getElementsByTagName("rescont");

			for (int i = 0; i < rescont.getLength(); i++) {
				Node nNode = rescont.item(i);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					//if (eElement.getElementsByTagName("type").item(0).getTextContent().compareTo("TW") == 0) {
						Map<Object, Object> filter = new HashMap<>();
						filter.put("SpO2", eElement.getElementsByTagName("concen").item(0).getTextContent());
						filter.put("Date", eElement.getElementsByTagName("rec_dat").item(0).getTextContent());
						data.add(filter);
					//}
				}
			}

		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}

		return data;
	}

	@GetMapping("/QDC/{caseno}/{st}/{et}") // 輸出入量(兒)
	public List<Map<Object, Object>> QDC(@PathVariable String caseno, @PathVariable String st, @PathVariable String et)
			throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);
		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.97.235.18:9080/NPIWS/service/Nnis/QDC/NIMA/" + caseno + "/" + st + "/" + et, HttpMethod.GET,
				entityNNIS, String.class);
		String nnis = resp.getBody();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		List<Map<Object, Object>> data = new ArrayList<Map<Object, Object>>();
		try {
			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(nnis));
			Document doc = builder.parse(is);
			NodeList rescont = doc.getElementsByTagName("rescont");

			for (int i = 0; i < rescont.getLength(); i++) {
				Node nNode = rescont.item(i);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					//if (eElement.getElementsByTagName("type").item(0).getTextContent().compareTo("TW") == 0) {
						Map<Object, Object> filter = new HashMap<>();
						filter.put("inp_val1", eElement.getElementsByTagName("inp_val1").item(0).getTextContent());
						filter.put("inp_val2", eElement.getElementsByTagName("inp_val2").item(0).getTextContent());
						filter.put("color", eElement.getElementsByTagName("color").item(0).getTextContent());
						filter.put("item", eElement.getElementsByTagName("item").item(0).getTextContent());
						filter.put("line", eElement.getElementsByTagName("line").item(0).getTextContent());
						filter.put("nam", eElement.getElementsByTagName("nam").item(0).getTextContent());
						filter.put("out_val1", eElement.getElementsByTagName("out_val1").item(0).getTextContent());
						filter.put("out_val2", eElement.getElementsByTagName("out_val2").item(0).getTextContent());
						filter.put("Date", eElement.getElementsByTagName("rec_dat").item(0).getTextContent());
						data.add(filter);
					//}
				}
			}

		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
		
		return data;
	}

	@GetMapping("/QMNC/{caseno}") // 查詢出生週數資訊
	public List<Map<Object, Object>> QMNC(@PathVariable String caseno) throws Exception {
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entityNNIS = new HttpEntity<String>(headers);
		ResponseEntity<String> resp = restTemplate.exchange(
				"http://10.97.235.18:9080/NPIWS/service/Nnis/QMNC/NIMA/" + caseno, HttpMethod.GET, entityNNIS,
				String.class);
		String nnis = resp.getBody();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		List<Map<Object, Object>> data = new ArrayList<Map<Object, Object>>();
		try {
			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(nnis));
			Document doc = builder.parse(is);
			NodeList rescont = doc.getElementsByTagName("rescont");

			for (int i = 0; i < rescont.getLength(); i++) {
				Node nNode = rescont.item(i);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					//if (eElement.getElementsByTagName("type").item(0).getTextContent().compareTo("TW") == 0) {
						Map<Object, Object> filter = new HashMap<>();
						filter.put("days", eElement.getElementsByTagName("days").item(0).getTextContent());
						filter.put("weeks", eElement.getElementsByTagName("weeks").item(0).getTextContent());
						data.add(filter);
					//}
				}
			}

		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
		
		return data;
	}

}
