package javastuff.designPatterns;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipInputStream;

public class Decorator {
    public static void main(String[] args) throws IOException {
        File file = new File("some.file");
        InputStream fileInputStream = new FileInputStream(file);
        FilterInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        final ZipInputStream zipInputStream = new ZipInputStream(bufferedInputStream);

//        final File file = new File("myfile.dat");
//        final FileOutputStream fileOutputStream = new
//                FileOutputStream(file);
//        final BufferedOutputStream bufferedOutputStream = new
//                BufferedOutputStream(fileOutputStream);
//        final ObjectOutputStream objectOutputStream = new
//                ObjectOutputStream(fileOutputStream);
//        objectOutputStream.writeBoolean(true);
//        objectOutputStream.writeInt(12);
//        objectOutputStream.writeObject(new ArrayList<Integer>());
//        objectOutputStream.flush();
//        objectOutputStream.close();
//        bufferedOutputStream.close();
//        fileOutputStream.close();
        //assertTrue(file.exists());
    }
}
