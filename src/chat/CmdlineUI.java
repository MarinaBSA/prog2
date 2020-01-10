package chat;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class CmdlineUI {
	//TODO write a constructor initialize to streams
	//TODO use interface when initializing methods

	public Chat chat;
	public Network network;
	private Socket sock;

	private String message;
	private String command;

	public static final String EXIT_COMMAND = "exit";
	public static final String WRITE_COMMAND = "write";
	public static final String READ_COMMAND = "read";
	public static final String CONNECT_COMMAND = "connect";
	public static final String OPEN_COMMAND = "open";
	public static final String CLOSE_COMMAND = "close";

	public static final String EXIT_MESSAGE = "Exit.";
	public static final String CONNECTED_MESSAGE = "Connected.";
	//public static final String MESSAGE_READ = "Message read.";
	public static final String WROTE_MESSAGE = "Wrote message.";
	public static final String CLOSED_MESSAGE = "Closed channel.";
	public static final String UNKNOWN_COMMAND_MESSAGE = "Unknown command. Exit.";

	public static final String WRITING_MESSAGE = "Writing message...";
	public static final String READING_MESSAGE = "Reading message...";
	public static final String CONNECTING_MESSAGE = "Connecting to server...";
	public static final String OPENING_MESSAGE = "Opened as server.\nWaiting for a connection...";
	public static final String CLOSING_MESSAGE = "Closing...";

	public static final String UI_TOKEN = ">\t";

	public String getCommand() {
		return this.command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String rawMessage) {
		this.message = rawMessage;
	}

	private boolean isConnected = false;
	private boolean isClient = false;

	public CmdlineUI(){
		this.chat = new ChatImpl();
		this.network = new NetworkImpl();
	}

	public void runUI(InputStream is, OutputStream os) throws Exception {
		String commandLineString = "";
		BufferedReader bf = new BufferedReader(new InputStreamReader(is));
		boolean shellIsRunning = true;
		String userInstructions = "Insert commands with this format " +
				"<command> [text (optional - depends on the command)]" +
				"\n\nAvailable commands" +
				"\n\tExit\tExits from the chat" +
				"\n\tWrite\tWrite a message to a TCP peer. Example: write Hello world" +
				//"\n\tRead\tRead a message from a TCP peer. Example: read" +
				"\n\tOpen\tOpen connection as a server to predefined port. Example: open" +
				"\n\tConnect\tConnect to a local server in predefined port. Example: connect 127.0.0.1" +
				"\n\tClose\tClose existing connection to TCP peer. Example: close\n\n";
		try {
			System.out.print(userInstructions);
			while(shellIsRunning) {
				//Start read thread
				if(this.isConnected) {
					Thread readingJob = new Thread(new ReadingThread(this.sock, os));
					readingJob.start();
				}

				System.out.print(CmdlineUI.UI_TOKEN);
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
		String[] rawMessageArray = input.trim().split(" ");

		String command = rawMessageArray[0];
		this.setCommand(command);

		String message = String.join(" ", Arrays.copyOfRange(rawMessageArray, 1, rawMessageArray.length));
		this.setMessage(message.trim());
		return message;
	}

	private boolean verifyUserInput(OutputStream os) throws Exception {
		PrintStream ps = new PrintStream(os);
		String command = this.getCommand().toLowerCase();
		String message = this.getMessage();

		switch(command) {
			case EXIT_COMMAND:
				ps.println(EXIT_MESSAGE);
				return false;
			case WRITE_COMMAND:
				ps.println(WRITING_MESSAGE);
				this.chat.writeMessage(message, this.network.getOutputStream(this.sock));
				ps.println(WROTE_MESSAGE);
				return true;
			/*case READ_COMMAND:
				//TODO Delete - the read command does not make sense here anymore
				ps.println(READING_MESSAGE);
				this.setMessage(this.chat.readMessage(this.network.getInputStream(this.sock)));
				ps.println("Message:\t" + this.getMessage());
				ps.println(MESSAGE_READ);
				return true;*/
			case CONNECT_COMMAND:
				ps.println(CONNECTING_MESSAGE);
				this.sock = this.network.connect(this.getMessage(), NetworkImpl.PORT);
				ps.println(CONNECTED_MESSAGE);
				this.isClient = true;
				this.isConnected = true;
				return true;
			case OPEN_COMMAND:
				ps.println(OPENING_MESSAGE);
				this.sock = this.network.open(NetworkImpl.PORT);
				this.isConnected = true;
				ps.println(CONNECTED_MESSAGE);
				return true;
			case CLOSE_COMMAND:
				ps.println(CLOSING_MESSAGE);
				this.network.close(this.sock);
				ps.println(CLOSED_MESSAGE);
				return false;
			default:
				ps.println(UNKNOWN_COMMAND_MESSAGE);
				//TODO Return true to keep asking for the next commands
				return false;
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

