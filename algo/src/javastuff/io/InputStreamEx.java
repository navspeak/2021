package javastuff.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Three {
    public static void main(String[] args) throws IOException {
        //one();
        String hello = "Hello world!";
        ByteArrayOutputStream buffer;
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(bos);
        ){
            writer.write(hello);
            buffer = bos;
        }
        byte[] bytes = buffer.toByteArray();

        try (ByteArrayInputStream bis =  new ByteArrayInputStream(bytes);
             InputStreamReader isr = new InputStreamReader(bis);
             BufferedReader reader = new BufferedReader(isr);){
            String s = reader.readLine();
            System.out.println("s = " + s);
        }
    }

    private static void one() throws IOException {
        byte[] headers = {0xC, 0xA, 0xF, 0xE};
        Path path = Path.of("files/data.bin");
        // try(OutputStream os = new FileOutputStream("files/data.bin")){
        try(OutputStream os = Files.newOutputStream(path)){
            os.write(headers);
        }

        try(InputStream is = Files.newInputStream(path)){
            byte[] bytes = is.readAllBytes();
            for(byte b: bytes){
                System.out.printf("0x%x\n", b);
            }
        }
    }
}
