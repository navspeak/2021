package javastuff.io;

import java.io.*;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Two { // FileWrite, FileReader, BufferedReader and BufferedWriter
    public static void main(String[] args) throws IOException {
        //one_TryResource();
        //markSupported();
        //writerExample();
        //bufferedWriterExample();
        //bufferedReaderExample();
        // BufferedReader is an extension and composition of Reader
        // BufferedReader is a decorator of Reader
        // provides additional features like read & write lines
        // Writers get options for opening
    }

    private static void bufferedReaderExample() throws IOException {
        Path path = Path.of("files/sonnet.txt");
        try(BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);){
            String line;
            while((line = bufferedReader.readLine()) != null) {
                System.out.println("Line = " + line );
            }
        }

        try(BufferedReader bufferedReader = Files.newBufferedReader(path);){
            bufferedReader.lines().forEach(System.out::println);
        }
    }

    private static void bufferedWriterExample() throws IOException {
        try(FileWriter fileWriter = new FileWriter("files/words.txt")){
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Writing to a buffered writer");
           // bufferedWriter.flush();
            bufferedWriter.close();
        }

        try(FileWriter fileWriter = new FileWriter("files/words.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            ){
             bufferedWriter.write("Writing to a buffered writer");
        }
        //Java 8 way
        Path path = Path.of("files/words.txt");
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(path,
                StandardCharsets.UTF_8,
                StandardOpenOption.APPEND);){
            bufferedWriter.write("Writing to a buffered writer");
        }
    }

    private static void writerExample() throws IOException {
        try(Writer writer = new FileWriter("files/words.txt");){ // closing writer calls flush
            writer.write("Hello world!");
            writer.append("a").append("b"); // Hello world!ab

        }

        try(StringWriter writer = new StringWriter();){ // has an underlying stringbuffer
            writer.write("Hello world!");
            writer.append("a").append("b"); // Hello world!ab
            writer.flush();
            StringBuffer buffer = writer.getBuffer();
            System.out.println("buffer = " + buffer); // both
            System.out.println(writer.toString());// has same underlying buf

        }
    }

    private static void markSupported() throws FileNotFoundException {
        System.out.println((new FileReader("files/sonnet.txt")).markSupported() + " == false i.e not supported");
        System.out.println((new CharArrayReader(new char[16])).markSupported() + " == true i.e supported");
        System.out.println((new CharArrayReader(new char[16])).markSupported() + " == true i.e supported");
    }

    public static void one_TryResource(){
        try (
            Reader reader = new FileReader("files/sonnet.txt");
        ){
            char[] buf = new char[16];
            int read ;
            StringBuilder sb = new StringBuilder();
            while ((read = reader.read(buf)) > 0){
                sb.append(buf, 0, read);

            }
            System.out.println(sb);
            //reader.close(); // Not needed now
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void one(){
        try {
            Reader reader = new FileReader("files/sonnet.txt");

            char[] buf = new char[16];
            int read ;
            StringBuilder sb = new StringBuilder();
            while ((read = reader.read(buf)) > 0){
                sb.append(buf, 0, read);

            }
            System.out.println(sb);
            reader.close(); // what if exception happen above
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
