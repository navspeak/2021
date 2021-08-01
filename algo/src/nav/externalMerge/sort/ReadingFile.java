package nav.externalMerge.sort;


import lombok.AllArgsConstructor;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadingFile {

    public static void main(String[] args) throws IOException {
        System.out.println(Runtime.getRuntime().freeMemory()/(1024*1024) + " MB");
        Path path = Path.of("files/numbers.txt");
        long sizeInBytes= Files.size(path);
        System.out.println("sizeInBytes = " + sizeInBytes);
        System.out.println("sizeInKB = " + sizeInBytes/1024);
        System.out.println("sizeInMB = " + sizeInBytes/(1024*1024));
//        try (RandomAccessFile raf = new RandomAccessFile("files/numbers.txt", "r")) {
//            int read;
//            while((read = raf.read())!= -1){
//                char c = (int) read;
//            }
//        }

        //System.out.println(System.getProperty("java.io.tmpdir"));
        //sortAndCreateChunkedPages();
        //merge();
        //System.in
//        try(DataOutputStream bw = new DataOutputStream(new FileOutputStream("files/page_"+0+".txt"))){
//            bw.writeInt(1);
//            bw.writeInt(2);
//            //bw.flush();
//        }
//
//        try(DataInputStream br = new DataInputStream(new FileInputStream("files/page_"+0+".txt"))){
//            System.out.println(br.readInt());
//            System.out.println(br.readInt());
//            //bw.flush();
//        }

//    try(BufferedReader br = new BufferedReader(new FileReader("files/page_"+0+".txt"));
//        BufferedWriter bw = new BufferedWriter(new FileWriter("files/page_"+0+".txt"));){
//        String line = br.readLine();
//        System.out.println(line);
//        bw.write(0);
//
//    }

        RandomAccessFile randomAccessFile = new RandomAccessFile("files/data.txt", "r");

        int bytes = 0;
        int    val = randomAccessFile.readLine();
        System.out.println(val);
        bytes = bytes + 4;
        randomAccessFile.skipBytes(bytes);
        val = randomAccessFile.readInt();
        System.out.println(val);
        bytes = bytes + 4;
        randomAccessFile.skipBytes(bytes);
        val = randomAccessFile.readInt();
        System.out.println(val);

//        try (Stream<String> lines = Files.lines(Path.of("files/page_"+0+".txt"))){
//            lines.map(Integer::valueOf).forEach(System.out::println);
//        }
        for (int i = 0; i <= 1000; i++) {
            File f = new File("files/page_"+i+".txt");
            if (f.exists()) {
                f.delete();
            }

        }
        //externalMergeSort(4*1028 / 4);
    }

    // main memory say 4KB = 4*1024 bytes => 1024 integers
    private static void  externalMergeSort(int maxNoOfInts) throws IOException {
        Path path = Path.of("files/numbers.txt");
        int read;
        List<Integer> chunk = new ArrayList<>();
        int page = 0;
        Map<Integer, int[]> mapOfPages = new HashMap<>();
        Path tempFilePath = Path.of("files");
        //Files.createTempFile();
        try(
                BufferedReader br = Files.newBufferedReader(path);
        ){
            String intVal = "";
            while((read = br.read()) != -1) {
                char c = (char) read;
                if (chunk.size() > maxNoOfInts && Character.isDigit(c)){
                    Collections.sort(chunk);
                    mapOfPages.put(page, new int[]{chunk.get(0), chunk.get(chunk.size()-1)});
                    File outputFile = new File("files/page_"+page+".txt");
                    page++;
                    outputFile.createNewFile();
                    try(BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))){
                        for(int val: chunk) {
                            bw.write(String.valueOf(val) + "\n");
                        }
                    }
                    chunk.clear();
                    continue;
                }
                if (Character.isDigit(c) ){
                    intVal = intVal + c;
                } else if (!intVal.isBlank()) {
                    chunk.add(Integer.valueOf(intVal));
                    intVal = "";
                }
            }
        }
        mapOfPages.forEach((k,v)-> System.out.println(k + " -> "+ Arrays.toString(v)));
        Reader reader[];
        PriorityQueue<Entry> minHeap = new PriorityQueue<>((e1,e2)-> Integer.compare(e1.val, e2.val));
        int currentPage = 0;
        File outputFile = new File("files/output.txt");
        outputFile.createNewFile();
        try(FileWriter fw = new FileWriter(outputFile)) {
            for (int i = 0; i < mapOfPages.size(); i++) {
                if (minHeap.size() >= mapOfPages.size()) {
                    Entry entry =  minHeap.remove();
                    fw.write(String.valueOf(entry.val)+"\n");
                    currentPage = entry.page;
                } else {
                    currentPage = i;
                }
                File f = new File("files/page_" + currentPage + ".txt");
                try (BufferedReader r = new BufferedReader(new FileReader(f));) {
                    String intVal = "";
                    while ((read = r.read()) != -1) {
                        char c = (char) read;
                        if (c == '[') continue;
                        if (c == ',') break;
                        intVal += c;
                    }
                    minHeap.add(new Entry(Integer.parseInt(intVal), currentPage));
                }
            }
        }
        for (int i = 0; i <= mapOfPages.size()-1; i++) {
            File f = new File("files/page_"+i+".txt");
            if (f.exists()) {
                f.delete();
            }

        }
    }

    @AllArgsConstructor
    private static class Entry {
        int val;
        int page;
    }

//    private static void merge(){
//        int[] pointerToPages = new int[pages]
//        for (int i = 0; i <= pages; i++) {
//
//        }
//    }
}

//        System.out.println(Runtime.getRuntime().maxMemory()/(1024*1024) + " MB");
//        System.out.println(Runtime.getRuntime().totalMemory()/(1024*1024) + " MB");
//        System.out.println(Runtime.getRuntime().freeMemory()/(1024*1024) + " MB");