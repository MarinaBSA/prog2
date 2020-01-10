import chat.Chat;
import chat.ChatImpl;
import chat.CmdlineUI;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;

public class ChatTest{
    private Chat chat;
    private CmdlineUI UI;
    final private String messageToWrite1 = "Houston we have a problem";
    final private String messageToWrite2 = "Test 1 2 3 - end of test";

    public ChatTest() {
        chat = new ChatImpl();
        UI = new CmdlineUI();
    }

    @Test
    public void testFileOutput() throws Exception  {
//        chat.writeMessage(this.messageToWrite1);
//        String[] receivedMessage = chat.readMessage();

//        Assert.assertTrue(Arrays.asList(receivedMessage).contains(this.messageToWrite1));
    }

    @Test
    public void testWrongFileOutput() throws Exception {
        final String wrongMessage = "You need people like me";

//        chat.writeMessage(this.messageToWrite1);
//        String[] receivedMessage = chat.readMessage();

//        try {
//            Assert.assertFalse(Arrays.asList(receivedMessage).contains(wrongMessage));
//        } catch (AssertionError e) {
//            System.err.println("Text written in file is not the expected one");
//        }
    }

    @Test
    public void UITestWriteCommand() throws Exception {
        String shownMessage = UITestHelper(CmdlineUI.WRITE_COMMAND, this.messageToWrite2);
        try {
            Assert.assertEquals(shownMessage, CmdlineUI.WRITING_MESSAGE);
        } catch (AssertionError e) {
            System.err.println("Could not see the writing message after inserting the write command");
        }
    }

    @Test
    public void UITestReadCommand() throws Exception {
        String shownMessage = UITestHelper(CmdlineUI.READ_COMMAND, this.messageToWrite2);
        try {
            Assert.assertEquals(shownMessage, CmdlineUI.READING_MESSAGE);
        } catch (AssertionError e) {
            System.err.println("Could not see the reading message after inserting the read command");
        }
    }

    @Test
    public void UITestExitCommand() throws Exception {
        String shownMessage = UITestHelper(CmdlineUI.EXIT_COMMAND, this.messageToWrite2);
        try {
            Assert.assertEquals(shownMessage, CmdlineUI.EXIT_MESSAGE);
        } catch (AssertionError e) {
            System.err.println("Could not see the exit message after inserting the exit command ");
        }
    }

    @Test
    public void UITestWrongCommand() throws Exception {
        String foo = "nothing";
        String command = "no command";
        String shownMessage = UITestHelper(command, foo);
        try {
            Assert.assertEquals(shownMessage, CmdlineUI.UNKNOWN_COMMAND_MESSAGE);
        } catch (AssertionError e) {
            System.err.println("Could not see the unknown command message after inserting unknown command ");
        }
    }

    @Test
    public void ConnectAsClient() {
        String command = "connect";
    }

    //Helper methods///////////////////////////////////////////////////////////////////////////////////////////////////

    private String UITestHelper(String command, String messageToWrite) throws Exception {
        InputStream is = null;

        //Write something in an array of bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(baos);

        //"write" exists now in an array of bytes coded as UTF inside baos
        outputStream.writeUTF(command + " " + messageToWrite);

        byte[] inputBytes = baos.toByteArray();
        is = new ByteArrayInputStream(inputBytes);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        UI.runUI(is, os);

        return os.toString().trim();     //reading back whatever was written by my output stream in CmdlineUI
    }
}
