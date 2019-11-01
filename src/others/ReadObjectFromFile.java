package others;

import others.TestUser;

import java.io.*;

public class ReadObjectFromFile {
    public static void main(String[] args) {
        Object obj = null;
        TestUser user = null;
        File file = new File("/Users/marinasantana/Desktop/user.txt");
        try {
            FileInputStream fis = new FileInputStream(file);
            try {
                ObjectInputStream os = new ObjectInputStream(fis);
                try {
                    obj = os.readObject();
                    user = (TestUser) obj;
                    System.out.println("id: " + user.id);
                    System.out.println("name: " + user.name);
                    System.out.println("level: " + user.level);
                } catch (ClassNotFoundException c) {
                    System.err.println(c.getMessage());
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }
}
