package server;

import java.util.Vector;

public class HTTPRequest {
	
	public String get="/";
	public String user_agent="";
	public String host="";
	
	/** Attempt to parse an input */
	public HTTPRequest(Vector<String> req) throws IllegalArgumentException {
		for (String s : req) {
			if (s.startsWith("GET ")) get=s.split(" ")[1];
			else {
				String[] ss=s.split(": ");
				if (ss[0].equals("User-Agent")) 	user_agent=ss[1];
				if (ss[0].equals("Host")) 			host=ss[1];
			}
		}
	}

	public String toString() {
		return ("HTTPRequest GET="+get+"  \n\tuser_agent="+user_agent+"\n\thost="+host);
	}
}
