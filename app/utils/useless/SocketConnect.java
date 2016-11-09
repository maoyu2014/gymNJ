package utils.useless;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketConnect {
	
	private SocketConnect() {}
	
	private static Socket s = null;
	private static PrintWriter out = null;
	
	public static Socket getSocketInstance() {
		if (s == null) {
			try {
				s = new Socket("127.0.0.1", 9999);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return s;
	}
	
	public static PrintWriter getPrintInstance() {
		if (out == null) {
			try {
				s = new Socket("127.0.0.1", 9999);
				out = new PrintWriter(s.getOutputStream(),true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return out;
	}
	
	
}
