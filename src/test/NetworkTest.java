import chat.Chat;
import chat.ChatImpl;
import chat.CmdlineUI;
import org.junit.Assert;
import org.junit.Test;
import java.lang.Thread;
import java.io.*;
import static org.hamcrest.CoreMatchers.containsString;

public class NetworkTest {

    private CmdlineUI UI;
    private Chat chat;
    private String uiTesHelper;


    public NetworkTest() {
        this.chat = new ChatImpl();
        this.UI = new CmdlineUI();
    }

    @Test
    public void integralCommunication() throws Exception {
        String serverOutputMessage;
        String clientOutputMessage = "";
        String message1 = "Hello";
        String message2 = "Hi!";
        String IP = "127.0.0.1";
        CmdlineUI serverUI = new CmdlineUI();
        InputStream serverIS = null;
        ByteArrayOutputStream serverOS = new ByteArrayOutputStream();

        CmdlineUI clientUI = new CmdlineUI();
        InputStream clientIS = null;
        ByteArrayOutputStream clientOS = new ByteArrayOutputStream();

        /////////////////
        // Server ///////
        /////////////////
        //OPENS CONNECTION
        Thread serverThread = new Thread(new UserThread(CmdlineUI.OPEN_COMMAND, serverIS, serverOS, serverUI));
        serverThread.start();

        //READS BACK UI OUTPUT -- connection opened/opening
        Thread.sleep(2000);     //waiting to retrieve output message from server thread
        serverOutputMessage = serverOS.toString().trim();
        Assert.assertEquals(CmdlineUI.OPENING_MESSAGE, serverOutputMessage);


        /////////////////
        // Client ///////
        /////////////////
        //CONNECTS TO SERVER AND WRITES MESSAGE
        String temp = String.format("%s %s\n %s %s", CmdlineUI.CONNECT_COMMAND, IP, CmdlineUI.WRITE_COMMAND, message1);
        Thread clientThread = new Thread(new UserThread(temp, clientIS, clientOS, clientUI));
        clientThread.start();

        //READS BACK UI OUTPUT -- connection established and message send
        Thread.sleep(2000);     //waiting to retrieve output message from client thread and for connection
        clientOutputMessage = clientOS.toString().trim();
        Assert.assertThat(clientOutputMessage, containsString(CmdlineUI.CONNECTING_MESSAGE + "\n" + CmdlineUI.CONNECTED_MESSAGE));
        Assert.assertThat(clientOutputMessage, containsString(CmdlineUI.WRITING_MESSAGE + "\n" + CmdlineUI.WROTE_MESSAGE));


        /////////////////
        // Server ///////
        /////////////////
        //SEES "Connected." AND MESSAGE -- after the client's connection
        Thread.sleep(2000);
        serverOutputMessage = serverOS.toString().trim();
        Assert.assertThat(serverOutputMessage, containsString(CmdlineUI.CONNECTED_MESSAGE));
        Assert.assertThat(serverOutputMessage, containsString("Message received: " + message1));
        
    }

    //Helper methods///////////////////////////////////////////////////////////////////////////////////////////////////

    private static class UserThread implements Runnable {
        private String message;
        private InputStream is;
        private OutputStream os;
        private CmdlineUI ui;

        public UserThread(String message, InputStream is, OutputStream os, CmdlineUI ui) {
            this.message = message;
            this.is = is;
            this.os = os;
            this.ui = ui;
        }

        private void uiTestHelper(String message) {
            //Write something in an array of bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream outputStream = new DataOutputStream(baos);

            //"write ..." exists now in an array of bytes coded as UTF inside baos
            try {
                outputStream.writeUTF(message);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            byte[] inputBytes = baos.toByteArray();
            this.is = new ByteArrayInputStream(inputBytes);

        }

        private void runUI() throws Exception{
            this.ui.runUI(this.is, this.os);
        }

        @Override
        public void run() {
            String shownMessage = null;
            try {
                this.uiTestHelper(this.message);
                this.runUI();
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }

    }




}
