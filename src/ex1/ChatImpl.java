package ex1;

import java.io.*;
import static java.lang.System.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ChatImpl implements Chat {

    private String fileLocation = "/Users/marinasantana/Desktop/testing.txt";

    @Override
    public void writeMessage(String message) throws Exception {
        FileOutputStream os = createFile(this.fileLocation);
        assert os != null;
        this.writeToFile(os, message);
    }

    @Override
    public String[] readMessage() throws Exception {
        ArrayList<String> fileContentList = new ArrayList<String>();
        String[] fileContent = new String[0];
        String[] errorArray = {"error"};
        try {
            FileInputStream fis = new FileInputStream(this.fileLocation);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader bf = new BufferedReader(isr);
            //while(bf.readLine() != null) {
            if(fileContentList.add(bf.readLine())) {
                fileContent = fileContentList.toArray(fileContent);
            //}
                return fileContent;
            }
            out.println("Could not write to array list in order to read line");
            return errorArray;

        } catch(FileNotFoundException e) {
            err.println("file could not be found");
            exit(0);
            return errorArray;
        }
    }

    private FileOutputStream createFile(String filename) {
        final String fileCreationMessage = "File successfully created.";
        final String fileOpenMessage = "couldn't open file - fatal.";
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            out.println(fileCreationMessage);
            return fos;
        } catch (FileNotFoundException ex) {
            err.println(fileOpenMessage);
            exit(0);
            return null;
        }
    }

    private void writeToFile(FileOutputStream file, String usersInput) {
        byte[] usersInputBytes = null;
        usersInputBytes = usersInput.getBytes();        //String to byte[]
        try {
            file.write(usersInputBytes);
        } catch(IOException ex) {
            err.println("could not write data.");
            exit(0);
        }
        out.println("Text successfully written to file.");
    }
}
