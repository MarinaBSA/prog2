package ex1;

import java.io.*;
import java.util.Arrays;
import static java.lang.System.*;

public class CmdlineUI {
	//TODO write a constructor initialize to streams
	//TODO removing some prints -> replacing them with string messages
	//TODO organize throws/exceptions
	//TODO use interface when initializing methods

	public Chat chat;
	public static final String EXITCOMMAND = "exit";
	public static final String WRITECOMMAND = "write";
	public static final String READCOMMAND = "read";
	public static final String CONNECTCOMMAND = "connect";
	public static final String EXITMESSAGE = "Exit.";
	public static final String WRITINGMESSAGE = "Writing...";
	public static final String READINGMESSAGE = "Reading...";
	public static final String FILECONTENTMESSAGE = "File's content: ";
	public static final String UNKNOWNCOMMANDMESSAGE = "Unknown command. Exit.";
	public static final String CONNECTMESSAGE = "Connected";
	public static final String UITOKEN = ">\t";

	private String message;
	private String[] rawMessageArray;
	private String command;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String[] getRawMessageArray() {
		return rawMessageArray;
	}

	public void setRawMessageArray(String[] rawMessageArray) {
		this.rawMessageArray = rawMessageArray;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String rawMessage) {
		this.message = rawMessage;
	}

	public CmdlineUI(){
		chat = new ChatImpl();
	}

	public void runUI(InputStream is, OutputStream os) throws Exception {
		String commandLineString = "";
		BufferedReader bf = new BufferedReader(new InputStreamReader(is));
		boolean shellIsRunning = true;

		try {
			StringBuilder userInstructions = new StringBuilder("Insert commands with this format ");
			userInstructions.append("<command> [text (optional - depends on the command)]");
			userInstructions.append("\n\nAvailable commands");
			userInstructions.append("\n\tExit\tExits from the chat");
			userInstructions.append("\n\tWrite\tWrite to a file. Example: write hello world");
			userInstructions.append("\n\tRead\tRead from a file. Example: read\n\n");

			System.out.print(userInstructions);
			while(shellIsRunning) {
				System.out.print(CmdlineUI.UITOKEN);
				commandLineString = bf.readLine();
				if (commandLineString == null) { break; }
				this.setMessage(parseInput(commandLineString));
				shellIsRunning = verifyUserInput(os);
			}
		} catch(IOException e) {
			System.err.println("Exception when reading from command line: " + e.getLocalizedMessage());
		}
	}

	//Separating command from the actual message out of the user's input.
	private String parseInput(String input) {
		//TODO: Do I really need this split("") and trim() ?
		String[] rawMessageArray = input.trim().split(" ");
		this.setRawMessageArray(rawMessageArray);

		String command = rawMessageArray[0];
		this.setCommand(command);

		String message = String.join(" ", Arrays.copyOfRange(rawMessageArray, 1, rawMessageArray.length));
		this.setMessage(message);
		return message;
	}

	private boolean verifyUserInput(OutputStream os) throws Exception {
		PrintStream ps = new PrintStream(os);

		String command = this.getCommand().toLowerCase();
		String message = this.getMessage();

		switch(command) {
			case EXITCOMMAND:
				ps.println(EXITMESSAGE);
				return false;
			case WRITECOMMAND:
				ps.println(WRITINGMESSAGE);
				chat.writeMessage(message);
				return true;
			case READCOMMAND:
				ps.println(READINGMESSAGE);
				this.showReceivedMessage(chat.readMessage());
				return true;
			case CONNECTCOMMAND:
				ps.println(CONNECTMESSAGE);

				return true;
			default:
				ps.println(UNKNOWNCOMMANDMESSAGE);
				return false;
		}
	}

	private void showReceivedMessage(String[] messages) {
		out.println(FILECONTENTMESSAGE);
		for (String word : messages) {
			out.println(word);
		}
	}

	public static void main(String[] args) {
		CmdlineUI ui = new CmdlineUI();
		try {
			ui.runUI(System.in, System.out);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}

