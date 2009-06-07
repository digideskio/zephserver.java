package server;

import java.io.File;
import java.io.IOException;

public class FileHandler {

	private File root;
	private String root_name;
	
	public static void main(String[] args) {
		FileHandler ff=new FileHandler("C:\\Users\\Tom\\Music\\");
		try {
			File f=ff.get("/Oasis/The Masterplan/../Be Here Now/../../..");
			System.out.println(f.getCanonicalPath());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public FileHandler(String _root_name) {
		root_name=_root_name;
		root=new File(root_name);
		if (!root.exists() || !root.isDirectory()) throw new IllegalArgumentException();
	}
	
	public File get(String path) throws IOException {
		while (path.startsWith("\\") || path.startsWith("/")) path=path.substring(1);
		File f=new File(root_name+path);
		if (!f.exists()) throw new IllegalArgumentException();
		if (!f.getCanonicalPath().toLowerCase().startsWith(root.getAbsolutePath().toLowerCase())) throw new IllegalArgumentException();
		return f;
	}
}
