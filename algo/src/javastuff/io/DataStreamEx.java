package javastuff.io;

import java.io.*;

public class DataStreamEx {
    public static void main(String[] args) throws IOException {
        int[] ints = {1,2,3,4,5}; // one int = 4 byte => 20 bytes
        byte[] bytes = {};
        try(ByteArrayOutputStream os = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(os)){
            for(int i: ints) {
                dos.writeInt(i);
            }
            dos.flush();
            bytes = os.toByteArray();
        }
        System.out.println("bytes.length = " + bytes.length);//20

        try(ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            DataInputStream dis = new DataInputStream(bis);
        ){
            for (int i = 0; i < 5; i++) {
               int read = dis.readInt();
                System.out.println("read = " + read);
            }
        }
    }
}
