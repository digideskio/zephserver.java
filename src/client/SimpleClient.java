package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import server.SimpleServer;

public class SimpleClient {
	// Debug log
	private static final Log log = LogFactory.getLog(SimpleServer.class);

	private static String[] request=new String[] {
		"GET / HTTP/1.1",
		"User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US) AppleWebKit/525.19 (KHTML, like Gecko) Chrome/1.0.154.65 Safari/525.19",
		"Accept: text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5",
		"Accept-Language: en-GB,en-US,en",
		"Accept-Charset: ISO-8859-1,*,utf-8",
		"Host: localhost",
		"Connection: Keep-Alive",
		"",
	};
	
	
	public static void main(String[] args) {
		String address="localhost";
		final int portId=80;
		
		try {
			log.info("Client starting with address="+address + " port="+ portId);
			Socket s=new Socket(address,portId);
			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter output=new PrintWriter(s.getOutputStream(),false);
			log.info("Connection: " + s);
			new SimpleClient(input,output);
		}
		catch (Exception e) {
			log.error(null,e);
		}
	}
	
	private BufferedReader in;
	private PrintWriter out;
	
	public SimpleClient(BufferedReader _in, PrintWriter _out) throws Exception {
		in=_in;
		out=_out;
		// Write the request
		log.info("Submitting request.");
		String end="\r\n";
		for (int i=0;i<request.length;i++) out.print(request[i]+end);
		out.flush();
		System.out.println("== REPLY CONTENT ==");
		String s;
		while ((s=in.readLine())!=null) {
			System.out.println(s);
		}
		System.out.println("== END OF MESSAGE ==");
	}
}
