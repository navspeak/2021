package javastuff.io;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;

public class ObjectStreamEx {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User u1 = new User("Paul", 23);
        User u2 = new User("Jenn", 25);

        try(FileOutputStream fos = new FileOutputStream("files/users.bin");
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(u1);
            oos.writeObject(u2); // If user class was not loaded, JVM won't know
            // how to serialize it and throw ClassNotFoundException
            // also if property names are changed, invalidClassException
            // serialVersionUID of new and old class will be diff
            // goto intellij setting > checck Serializable class without serialVersionId
            // if u have serialized with say a versionid x and fields a and b
            // while deserializing, if the fields are changed, you may get silent errors
            // The desrialization will try to match the names if possible
        }

        try(FileInputStream fis = new FileInputStream("files/users.bin");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object o1 = ois.readObject();
            System.out.println("o1 = " + o1);
            Object o2 = ois.readObject();
            System.out.println("o2 = " + o2);
        }
    }
}

@AllArgsConstructor
@Data
class User implements Serializable {
    private static final long serialVersionUID = 8446874970588129312L;
    String name;
    int age;


}