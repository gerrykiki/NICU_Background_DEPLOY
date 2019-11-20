package deploy.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTest extends Thread {

	int SOCKET_PORT = 9000;
	String SERVER = "10.100.83.150";
	String FILE = "CenterM3150.txt";

	Socket client = null;
	Socket socket = null;
	ServerSocket server;

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

		byte[] mybytearray = new byte[1024];
		int len = 0;
		String data = "";

		try {
			// socket = server.accept();
			// logger.info("取得Frontend連線 ： " + socket);

			FileOutputStream file = new FileOutputStream(FILE);
			ToMe = new BufferedOutputStream(file);

			// ToFrontend = new BufferedOutputStream(socket.getOutputStream());

			in = new BufferedInputStream(client.getInputStream());

			while ((len = in.read(mybytearray, 0, mybytearray.length)) > 0) {
				data = new String(mybytearray, 0, len);

				// ToFrontend.write(mybytearray, 0, len);
				// ToFrontend.flush();

				ToMe.write(mybytearray, 0, len);
				ToMe.flush();

				System.out.println("我取得的值:\n" + data);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}