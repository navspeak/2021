package javastuff.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class One {
    public static void main(String[] args) throws IOException {

        //fileio1();
        Path path = Paths.get("images/seaside.jpg");
        Path path2 = Path.of("s/seaside.jpg"); // since java 11
        System.out.println("path2.getClass() = " + path2.getClass()); // can change with OS class sun.nio.fs.WindowsPath
        FileSystem fileSystem = path.getFileSystem();
        System.out.println("fileSystem = " + fileSystem); // un.nio.fs.WindowsFileSystem@1554909b
        //Files.createFile(path,)
        //path.isAbsolute();
        Path resolve= path.resolve(path2);
        System.out.println("resolve = " + resolve);//images\seaside.jpg\s\seaside.jpg

        Path resolveSibling= path.resolveSibling(path2);
        System.out.println("resolveSibling = " + resolveSibling); //images\s\seaside.jpg

        Path relativize = path2.relativize(path); // throws IllegalArgumentException if one path is abs
        System.out.println("relativize = " + relativize);
        //path.normalize();
    }

    // File is class | Path is interface so use factory method defined in Paths
    private static void fileio1() throws IOException {
        File file = new File("files/sonnet.txt");
        System.out.println("File = " + file); // nothing created yet
        System.out.println("File Exists = " + file.exists()); // nothing created yet
        System.out.println("File isDir = " + file.isDirectory());
        System.out.println("File isFile= " + file.isFile());
        System.out.println("File isAbsolute = " + file.isAbsolute());

        File file2 = new File("files/sonnet2.txt");
        boolean fileCreated = file2.createNewFile();
        System.out.println("fileCreated = " + fileCreated);

        File dir = new File("files/data");
        boolean dirCreated = dir.mkdir();
        System.out.println("dirCreated = " + dirCreated);

        File dir2 = new File("files/data2/more-data");
        boolean dirCreated2 = dir2.mkdirs();
        System.out.println("dirsCreated = " + dirCreated2);

        boolean delete = dir.delete();
        System.out.println("delete = " + delete);
        delete = dir2.delete(); // will delete only more-data and not data
        delete = file2.delete();

        dir = new File("images/images/.././././doesnotexist.txt");
        System.out.println(dir.getName());
        System.out.println(dir.getParent());
        System.out.println(dir.getParentFile());
        System.out.println(dir.getPath());
        System.out.println(dir.getAbsolutePath()); //C:\Users\kumar.navneet01\2021\algo\images\images\..\.\.\.\doesnotexist.txt
        System.out.println(dir.getCanonicalPath()); //C:\Users\kumar.navneet01\2021\algo\images\doesnotexist.txt
    }
}
