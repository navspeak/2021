### History
- Java I/O introduced in Java 1 (1995)
- Java NIO - Java 4 (2002)
- Java NIO 2 - Java 7 (2011)
- data : chars or bytes

## Basic Structure
- Abstract Reader - read chars From any resource (File/Memory). Source is abstracted and not known to the Reader.
    - close() : abstract
    - read(char[], int, int): abstract
- Concrete Impl of Reader: FileReader, StringReader, CharArrayReader
- To deal with Files use: File class & Path (as a part of Java NIO)

http://tutorials.jenkov.com/java-io/linenumberreader.html

- 4 Base class: Reader and Writer (for txt) + InputStream and OutputStream (for bin)
- 2 utility class: File (independent of file system) and Path (interface. Java 7. OS specific impl. Linked to FileSystem)
- Note Path.of() is a static method in the interface  
- Reader support skip elements, may support reset, may support mark     

## Reader
- Disk: FileReader | In-memory: CharArrayReader, StringReader
- classes that add behavior: BufferedReader, LineNumberReader, PushBackReader
    - File f = new File("files/data.txt"); Reader r = new FileReader(f)
    - String text = "Hello";  Reader r = new StringReader(text)
- Decorator Pattern: BufferedReader extends Reader (and also composes Reader ): lines(), readLine()
    - LineNumberReader extends BufferedReader: getLineNumber()