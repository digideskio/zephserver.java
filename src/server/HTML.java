package server;

import java.io.File;
import java.io.FileFilter;

public class HTML {

	private static final String background="#000022";
	
	private static final String content_tag="<CONTENT>";
	private static final String basis=
		"<html><head>" +
		"<title>Tom's Test McPage</title>" +
		"</head><body style=\"" +
			"background: "+background+"; " +
			"color: #ffffff;" +
			"font-family: tahoma,helvetica;" +
			"font-size:11px;" +
			"line-height: 17px;" +
			"\">" +	content_tag +"</body></html>";
	
	public static byte[] testPage() {
		return buildPage("<span style=\"color: #ffffff; font-family: verdana; font-size: 30px;\">Hooray, it works!</span>");
	}
	
	public static byte[] buildPage(String content) {
		return basis.replace(content_tag, content).getBytes();
	}
	
	public static byte[] get_directory_list(String current_root, File f) {
		if (!current_root.endsWith("/")) current_root+='/';
		// Get a list of directories
		File[] dirs=f.listFiles(
				new FileFilter() { 
					public boolean accept(File file) { 
						return file.isDirectory(); 
					}
				});
		StringBuilder reply=new StringBuilder();
		final String br="<br>\n";
		for (int i=0;i<dirs.length;i++) {
			String filename=dirs[i].getName();
			String url=current_root+filename;
			reply.append("&gt; <a href=\""+url+"\">"+filename + "</a>");
			reply.append(br);
		}
		
		return buildPage(reply.toString());
	}
}
