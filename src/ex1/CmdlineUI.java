package ex1;

import java.io.*;

import org.omg.CORBA.DataInputStream;
//import java.util.Arrays;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;


public class CmdlineUI {
	
	public static void main(String[] args) {
		CmdlineUI ui = new CmdlineUI();
		ui.readCommands();	
	}
	
	private void readCommands() {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		String commandLineString = null;
		String[] commands = null;
		boolean shellIsRunning = true;
		
		//Creating file and inserting user's input
		OutputStream file = createFile("testing.txt");
		try {
			System.out.println("Enter exit to finish");

			while(shellIsRunning) {
				System.out.println("> ");
				commandLineString = br.readLine();
				commands = parsingCommands(commandLineString);
				//System.out.println("read from command line: " + commandLineString);
				shellIsRunning = verifyUserInput(commands);
				if (!shellIsRunning) { continue; }
				writingToFile(file, commands);	
			}
			//read from file
			File fileToRead = new File("/Users/marinasantana/eclipse-workspace/Prog2/testing.txt");
			String fileContent = readFile(fileToRead);
			System.out.println("Content: " + fileContent);
			
		} catch(IOException e) {
			System.err.println("exception when reading from command line: " + e.getLocalizedMessage());
		}
	}
	
	//Extracting each word from the users input. Separation by blank space
	private String[] parsingCommands(String commands) {
		String[] result =  commands.split(" ");
		return result;
	}
	
	private boolean verifyUserInput(String[] input) {
		final String stopCommand = "exit"; 
		String command = input[0].trim(); 
		//Removing the first element of the array(the command)
		//String[] usersInput = Arrays.copyOfRange(input, 1, input.length);
		switch(command) {
			//case "exit":
			case stopCommand:
				System.out.println("Bye.");
				return false;
			default:
				return true;
		}
	}
	
	private OutputStream createFile(String filename) {
		try {
			OutputStream os = new FileOutputStream(filename);
			System.out.println("File successfully created.");
			return os;
			// brutal exception handling
		} catch (FileNotFoundException ex) {
			System.err.println("couldn’t open file - fatal.");
			System.exit(0); 
			return null;
		}
	}
	
	private void writingToFile(OutputStream file, String[] usersInput) {
		byte[] usersInputBytes = null;
		for (String word : usersInput) {
			word = word + " ";
			usersInputBytes = word.getBytes();
			try {
				file.write(usersInputBytes);
			} catch(IOException ex) {
				System.err.println("could not write data.");
				System.exit(0);
			}
		}
		System.out.println("Text successfully written to file.");
	}
	
	private String readFile(File file) {
		String fileContent = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			try {
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				try {
					BufferedReader bf = new BufferedReader(isr);
					//while(bf.readLine() != null) { 
						fileContent = bf.readLine();	
					//}
					return fileContent;
				} catch(IOException e) {
					System.err.println("file could not be read");
					return "";
				}
			} catch(UnsupportedEncodingException e) {
				System.err.print("format not supported");
				System.exit(0);
				return "";
			}
		} catch(FileNotFoundException e) {
			System.err.println("file could not be found");
			System.exit(0);
			return "";
		}					
		
	}
}

