package deploy.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import ca.uhn.hl7v2.util.Hl7InputStreamMessageIterator;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v23.group.*;
import ca.uhn.hl7v2.model.v23.message.ORU_R01;
import ca.uhn.hl7v2.model.v23.segment.MSH;
import ca.uhn.hl7v2.model.v23.segment.OBR;
import ca.uhn.hl7v2.model.v23.segment.OBX;
import ca.uhn.hl7v2.model.v23.segment.PID;
import ca.uhn.hl7v2.model.v23.segment.PV1;
import ca.uhn.hl7v2.parser.Parser;


public class SocketTest extends Thread {

	private static HapiContext context = new DefaultHapiContext();
	int SOCKET_PORT = 9000;
	String SERVER = "10.100.83.150";
	String FILE = "CenterM3150.txt";

	Socket client = null;
	Socket socket = null;
	ServerSocket server;

	private Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("cassandra").withPort(9042)
			.build();
	private Session session = cluster.connect("nicu");

	public SocketTest() {
		try {
			client = new Socket(SERVER, SOCKET_PORT);
			// server = new ServerSocket(5556);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		BufferedInputStream in;
		BufferedOutputStream ToMe;
		BufferedOutputStream ToFrontend;

		System.out.println("TCP已連線 !");



		try {
			// socket = server.accept();
			// logger.info("取得Frontend連線 ： " + socket);

			FileOutputStream file = new FileOutputStream(FILE);
			ToMe = new BufferedOutputStream(file);

			// ToFrontend = new BufferedOutputStream(socket.getOutputStream());

			in = new BufferedInputStream(client.getInputStream());
			int numByte = in.available();
			byte[] mybytearray = new byte[10240];
			
			int len = 0;
			String data = "";
			while ((len = in.read(mybytearray, 0, mybytearray.length)) > 0) {
				data = new String(mybytearray);
//				Parser parser = context.getPipeParser();
//				string responseString = parser.encode(data);
//				data = data.replaceAll("\+", "\\");
//				String newdata = data.replace("", "\\");

				// ToFrontend.write(mybytearray, 0, len);
				// ToFrontend.flush();
				try {
					parser(data);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// ToMe.write(mybytearray, 0, len);
				// ToMe.flush();

				System.out.println("我取得的值:\n" + data);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	
	public void parser(String msg) throws Exception {
		HapiContext context = new DefaultHapiContext();

		Parser p = context.getPipeParser();

		Message hapiMsg;
		try {
			hapiMsg = p.parse(msg);
			try {
				ORU_R01 oruMsg = (ORU_R01) hapiMsg;

				ORU_R01_RESPONSE oruResp = oruMsg.getRESPONSE();
				ORU_R01_PATIENT oruPat = oruResp.getPATIENT();
				ORU_R01_VISIT oruVis = oruPat.getVISIT();

				MSH msh = oruMsg.getMSH();
				PID pid = oruPat.getPID();
				PV1 pv1 = oruVis.getPV1();

				ORU_R01_ORDER_OBSERVATION oruOrd = oruResp.getORDER_OBSERVATION();
				ORU_R01_OBSERVATION oruObs0 = oruOrd.getOBSERVATION(0);
				ORU_R01_OBSERVATION oruObs1 = oruOrd.getOBSERVATION(1);
				ORU_R01_OBSERVATION oruObs2 = oruOrd.getOBSERVATION(2);
				ORU_R01_OBSERVATION oruObs3 = oruOrd.getOBSERVATION(3);
				ORU_R01_OBSERVATION oruObs4 = oruOrd.getOBSERVATION(4);
				ORU_R01_OBSERVATION oruObs5 = oruOrd.getOBSERVATION(5);
				ORU_R01_OBSERVATION oruObs6 = oruOrd.getOBSERVATION(6);
				ORU_R01_OBSERVATION oruObs7 = oruOrd.getOBSERVATION(7);
				ORU_R01_OBSERVATION oruObs8 = oruOrd.getOBSERVATION(8);
				ORU_R01_OBSERVATION oruObs9 = oruOrd.getOBSERVATION(9);
				ORU_R01_OBSERVATION oruObs10 = oruOrd.getOBSERVATION(10);
				ORU_R01_OBSERVATION oruObs11 = oruOrd.getOBSERVATION(11);
				ORU_R01_OBSERVATION oruObs12 = oruOrd.getOBSERVATION(12);
				ORU_R01_OBSERVATION oruObs13 = oruOrd.getOBSERVATION(13);
				ORU_R01_OBSERVATION oruObs14 = oruOrd.getOBSERVATION(14);

				OBX obx0 = oruObs0.getOBX();
				OBX obx1 = oruObs1.getOBX();
				OBX obx2 = oruObs2.getOBX();
				OBX obx3 = oruObs3.getOBX();
				OBX obx4 = oruObs4.getOBX();
				OBX obx5 = oruObs5.getOBX();
				OBX obx6 = oruObs6.getOBX();
				OBX obx7 = oruObs7.getOBX();
				OBX obx8 = oruObs8.getOBX();
				OBX obx9 = oruObs9.getOBX();
				OBX obx10 = oruObs10.getOBX();
				OBX obx11 = oruObs11.getOBX();
				OBX obx12 = oruObs12.getOBX();
				OBX obx13 = oruObs13.getOBX();
				OBX obx14 = oruObs14.getOBX();

				OBR obr0 = oruOrd.getOBR();

				String pn = pid.getPatientName(0).encode();// PatientName
				String HISID = "";
				int j = pn.indexOf("^");
				HISID = pn.substring(0, j);

				String date = obr0.getObservationDateTime().encode();// date

				String obsId_col1 = obx1.getObservationIdentifier().getComponent(1).encode();
				String obx1_value = obx1.getObservationValue(0).encode();

				String obsId_col2 = obx2.getObservationIdentifier().getComponent(1).encode();
				String obx2_value = obx2.getObservationValue(0).encode();

				String obsId_col3 = obx3.getObservationIdentifier().getComponent(1).encode();
				String obx3_value = obx3.getObservationValue(0).encode();

				String obsId_col4 = obx4.getObservationIdentifier().getComponent(1).encode();
				String obx4_value = obx4.getObservationValue(0).encode();

				String obsId_col5 = obx5.getObservationIdentifier().getComponent(1).encode();
				String obx5_value = obx5.getObservationValue(0).encode();

				String obsId_col6 = obx6.getObservationIdentifier().getComponent(1).encode();
				String obx6_value = obx6.getObservationValue(0).encode();

				String obsId_col7 = obx7.getObservationIdentifier().getComponent(1).encode();
				String obx7_value = obx7.getObservationValue(0).encode();

				String obsId_col8 = obx8.getObservationIdentifier().getComponent(1).encode();
				String obx8_value = obx8.getObservationValue(0).encode();

				String obsId_col9 = obx9.getObservationIdentifier().getComponent(1).encode();
				String obx9_value = obx9.getObservationValue(0).encode();

				String obsId_col10 = obx10.getObservationIdentifier().getComponent(1).encode();
				String obx10_value = obx10.getObservationValue(0).encode();

				String obsId_col11 = obx11.getObservationIdentifier().getComponent(1).encode();
				String obx11_value = obx11.getObservationValue(0).encode();

				String obsId_col12 = obx12.getObservationIdentifier().getComponent(1).encode();
				String obx12_value = obx12.getObservationValue(0).encode();

				String obsId_col13 = obx13.getObservationIdentifier().getComponent(1).encode();
				String obx13_value = obx13.getObservationValue(0).encode();

				String obsId_col14 = obx14.getObservationIdentifier().getComponent(1).encode();
				String obx14_value = obx14.getObservationValue(0).encode();

				HashMap<String, String> m3150 = new HashMap<String, String>();

				m3150.put(obsId_col1, obx1_value);
				m3150.put(obsId_col2, obx2_value);
				m3150.put(obsId_col3, obx3_value);
				m3150.put(obsId_col4, obx4_value);
				m3150.put(obsId_col5, obx5_value);
				m3150.put(obsId_col6, obx6_value);
				m3150.put(obsId_col7, obx7_value);
				m3150.put(obsId_col8, obx8_value);
				m3150.put(obsId_col9, obx9_value);
				m3150.put(obsId_col10, obx10_value);
				m3150.put(obsId_col11, obx11_value);
				m3150.put(obsId_col12, obx12_value);
				m3150.put(obsId_col13, obx13_value);
				m3150.put(obsId_col14, obx14_value);

				Integer HR = (m3150.get("HR") != null ? Integer.parseInt(m3150.get("HR")) : 0);
				Integer SpO2 = (m3150.get("SpO2") != null ? Integer.parseInt(m3150.get("SpO2")) : 0);
				Integer RR = (m3150.get("RR") != null ? Integer.parseInt(m3150.get("RR")) : 0);
				Float BT = (m3150.get("Temp") != null ? Float.parseFloat((m3150.get("Temp"))) : 0.0f);
				Integer ABPd = (m3150.get("ABPd") != null ? Integer.parseInt(m3150.get("ABPd")) : 0);
				Integer ABPs = (m3150.get("ABPs") != null ? Integer.parseInt(m3150.get("ABPs")) : 0);
				Integer ABPm = (m3150.get("ABPm") != null ? Integer.parseInt(m3150.get("ABPm")) : 0);
				Integer NBPd = (m3150.get("NBPd") != null ? Integer.parseInt(m3150.get("NBPd")) : 0);
				Integer NBPs = (m3150.get("NBPs") != null ? Integer.parseInt(m3150.get("NBPs")) : 0);
				Integer NBPm = (m3150.get("NBPm") != null ? Integer.parseInt(m3150.get("NBPm")) : 0);

				DateTimeFormatter originalFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
				LocalDateTime ldt = LocalDateTime.parse(date, originalFormat);
                System.out.println(ldt);
                System.out.println(HISID);
				StringBuilder sb = new StringBuilder("INSERT INTO ").append("centermonitor")
						.append("(time,phistnum,hr,sp,rr,bt,abp_s,abp_d,abp_m,nbp_s,nbp_d,nbp_m) ").append("VALUES('")
						.append(ldt).append("', '").append(HISID).append("',").append(HR).append(", ").append(SpO2)
						.append(", ").append(RR).append(", ").append(BT).append(", ").append(ABPs).append(", ")
						.append(ABPd).append(", ").append(ABPm).append(",").append(NBPs).append(", ").append(NBPd)
						.append(",").append(NBPm).append(");");

				String query = sb.toString();
				session.execute(query);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}