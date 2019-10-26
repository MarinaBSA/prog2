package ex1;

import com.sun.codemodel.internal.JForEach;

import java.io.*;
import java.util.Arrays;
import static java.lang.System.*;

public class CmdlineUI {
	//TODO create test UI/Chat -> insert text, expect something else
	//TODO append user input to file instead of overwriting it
	//TODO removing some prints -> replacing them with string messages
	//TODO catch case for when user inserts an unknown command in the method verifyUserInput
	//TODO if user input is empty throw except/or handle case
	//TODO organize throws/exceptions

	public ChatImpl chat;
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

	CmdlineUI(){
		chat = new ChatImpl();
	}

	public void readCommands() throws Exception {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		String commandLineString = "";
		boolean shellIsRunning = true;

		try {
			System.out.println("Enter exit to finish");
			while(shellIsRunning) {
				System.out.print("> ");
				commandLineString = br.readLine();
				this.setMessage(parseInput(commandLineString));
				shellIsRunning = verifyUserInput();
			}

		} catch(IOException e) {
			System.err.println("exception when reading from command line: " + e.getLocalizedMessage());
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

	private boolean verifyUserInput() throws Exception {
		String command = this.getCommand();
		String message = this.getMessage();
		final String stopCommand = "exit";
		final String write = "write";
		final String read = "read";
		final String finishedMessage = "Exit.";
		final String writingMessage = "Writing...";
		final String readingMessage = "Reading...";

		switch(command) {
			case stopCommand:
				out.println(finishedMessage);
				return false;
			case write:
				out.println(writingMessage);
				chat.writeMessage(message);
				return true;
			case read:
				out.println(readingMessage);
				this.showReceivedMessage(chat.readMessage());
				return true;
			default:
				return true;
		}
	}

	private void showReceivedMessage(String[] messages) {
		out.println("File's Content: ");
		for (String word : messages) {
			out.println(word);
		}
	}

	public static void main(String[] args) {
		CmdlineUI ui = new CmdlineUI();
		try {
			ui.readCommands();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}

