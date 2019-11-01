package others;

import java.io.*;

public class ReadWriteFromCommandLine {
    public static void main(String[] args) {
        final String finishComm = "exit";
        try {
            System.out.println("insert something: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = null;

            while(!(line = br.readLine()).equals(finishComm)) {
                System.out.println("result: " + line);
            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
