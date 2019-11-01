package others;

import others.TestUser;

import java.io.*;

public class WriteObjectToFile {

    public static void main(String[] args) {
        TestUser user = new TestUser();
        File file = new File("/Users/marinasantana/Desktop/user.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
             try {
                 ObjectOutputStream os = new ObjectOutputStream(fos);
                 os.writeObject(user);
             } catch (IOException e) {
                 System.err.println(e.getMessage());
             }

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /*public static void main(String[] args) {
        File file = new File("/Users/marinasantana/Desktop/user.txt");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos);
            try {
                dos.writeInt(577);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch(FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);
            try {
                System.out.println(dis.readInt());
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }*/
}



