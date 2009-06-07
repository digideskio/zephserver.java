package server;

import java.io.*;
import java.net.Socket;
import java.util.Vector;

public class Handler extends Thread {

	private static final String root="c:\\Users\\Tom\\Music\\";
	
	/** 
	 * Input-output-connection mechanisms 
	 */
	private Socket sock;
	private BufferedReader in;
	private OutputStream out;
	
	private FileHandler disk;
	
	/** 
	 * Create a handler thread to deal with an incoming connection 
	 */
	public Handler(Socket _sock) throws IOException {
		sock=_sock;
		// Instantiate the input and output mechanisms from the socket
		out=sock.getOutputStream();
		in=new BufferedReader(new InputStreamReader(sock.getInputStream()));
		disk=new FileHandler(root);
	}
	
	/** 
	 * Main thread: Read a request and deal with it.
	 */
	public void run() {
		try {
			// Read the request from the wire
			Vector<String> request_string=new Vector<String>(5);
			String s;
			while ((s=in.readLine())!=null) {
				request_string.add(s);
				if (s.length()==0) break;
			}
			HTTPRequest req=new HTTPRequest(request_string);
			

			try {
				File file=disk.get(req.get);
				if (file.isDirectory()) {
					// Return directory contents
					byte[] reply=HTML.get_directory_list(req.get, file);
					respond(reply);
				}
				else {
					// Return file contents
					FileInputStream input=new FileInputStream(file);
					byte[] b=new byte[(int)file.length()];
					input.read(b);
					input.close();
					respond(b);
				}
			}
			catch (Exception e) {
				System.err.println(e+ " handling " + req.get);
				respond_err(e instanceof IllegalArgumentException? 404 : 403);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				sock.close();
			}
			catch (Exception e) {}
		}
	}
	
	private void respond(byte[] reply) throws IOException {
		out.write("HTTP/1.1 200 OK".getBytes());
		out.write(("Content-length: "+reply.length).getBytes());
		out.write(10);
		out.write(10);
		out.write(reply);
		out.flush();
	}
	
	private void respond_err(int errorcode) throws IOException {
		out.write(("HTTP/1.1 "+errorcode).getBytes());
		out.flush();
	}
}
