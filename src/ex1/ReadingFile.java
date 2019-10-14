package ex1;

import java.io.*;

public class ReadingFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				
		File fs = new File("/Users/marinasantana/eclipse-workspace/Prog2/src/ex1/test.txt");
		String fileContent = readingFile(fs);
		System.out.println(fileContent);
	}

	private static String readingFile(File file) {
		String fileContent = null;
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bf = new BufferedReader(fr);
			try {
				String content = bf.readLine();
				return content;
			} catch(IOException e) {

			}
			
		} catch(FileNotFoundException e) {
			
		}
		return "";
	}
//		
//		try {
//			FileInputStream fis = new FileInputStream(file);
//			try {
//				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
//				try {
//					BufferedReader bf = new BufferedReader(isr);
//					//while(bf.readLine() != null) { 
//						fileContent = bf.readLine();	
//					//}
//					return fileContent;
//				} catch(IOException e) {
//					System.err.println("file could not be read");
//					return "";
//				}
//			} catch(UnsupportedEncodingException e) {
//				System.err.print("format not supported");
//				System.exit(0);
//				return "";
//			}
//		} catch(FileNotFoundException e) {
//			System.err.println("file could not be found");
//			System.exit(0);
//			return "";
//		}					
//		
//	}


}
