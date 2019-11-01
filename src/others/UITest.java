package others;

import org.junit.Assert;
import org.junit.Test;
import java.io.*;

public class UITest {
    @Test
    public void uiTest() throws IOException {
        UI ui = new UI();

       //InputStream myInputStream = null;

        String unknownCommandString = "hallo";
        String exitString = UI.EXIT;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(unknownCommandString);

        byte[] inputBytes = baos.toByteArray();

        InputStream myInputStream = new ByteArrayInputStream(inputBytes);

        ByteArrayOutputStream myOutputStream = new ByteArrayOutputStream();

        ui.userInputLoop(myInputStream, myOutputStream);
        byte[] res = myOutputStream.toByteArray();

        ByteArrayInputStream outputIS = new ByteArrayInputStream(
                res);

        String shownMessage =  myOutputStream.toString();
        Assert.assertEquals(shownMessage, "unknown command");
    }
}

