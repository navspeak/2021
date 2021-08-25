import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class One {
/*

34.5, 10   , ,36,37
1   , 2    , 3
98.7, -10.3,57,28,29

2,3

Line = 34.5,10,,36,37
Line = 1,2,3
Line = 98.7,-10.3,57,28,29
Line =
Line = 2,3

----------

 */
    public static void main(String[] args) {
      // Read the file
      //List<List<Double>> numbers = new ArrayList<>();
      Path path = Paths.get("files/col.csv");
      List<Double> columnSums = new ArrayList<>();
      /*--*/
      BigDecimal[] array = {
              new BigDecimal(10.0),
              new BigDecimal(2.0),
              new BigDecimal(-10.3),
              new BigDecimal(0.0),
              new BigDecimal(3.0)
      };
      BigDecimal ans = new BigDecimal(0.0);
      for (BigDecimal d: array){
          ans = ans.add(d);
      }
      System.out.println(ans); // 4.699999999999999
      /*--*/
      try(BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);){
            String line;
            while((line = bufferedReader.readLine()) != null) {
                //System.out.println("Line = " + line );
                String[] numbersInThisRow = line.split(",");
                System.out.println(Arrays.toString(numbersInThisRow));
                for(int i = 0; i < numbersInThisRow.length; i++){
                    String num =  numbersInThisRow[i];
                    Double sum = columnSums.size() > i ? columnSums.get(i): 0.0;
                    if (num.isBlank())  num = "0.0";

                    sum += Double.parseDouble(num);
                    /*
                       1, 3, 7 ??
                       i = 3
                       size = 3
                     */
                    if (columnSums.size() <= i){
                        columnSums.add(sum);
                    } else
                        columnSums.set(i, sum);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("============");
        System.out.println(Arrays.toString(columnSums.toArray()));
    }
}
