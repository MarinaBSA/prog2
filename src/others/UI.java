package others;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.io.*;

public class UI {

    public static final String ERRORMESSAGE = "unknown command";
    public static final String EXIT = "exit";

    public BufferedReader userInput;

    public void userInputLoop(InputStream is, OutputStream os) throws IOException {
        this.userInput = new BufferedReader(new InputStreamReader(is));

        // create PrintStream from OutputStream or use OutputStream
        PrintStream ps = new PrintStream(os);

        boolean again = true;
        while(again) {
            String command = this.userInput.readLine().trim();

            switch (command) {
                case EXIT: again = false; break;
                default:
                    ps.print(ERRORMESSAGE);
                    again = false;
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        UI ui = new UI();
        ui.userInputLoop(System.in, System.out);
    }
}
