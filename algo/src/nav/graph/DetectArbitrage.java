package nav.popularAlgo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Bellman ford algorithm - detect negative edge cycle
public class DetectArbitrage {
    public boolean detectArbitrage(List<List<Double>> exchangeRates) {
        Double[][] rates = new Double[exchangeRates.size()][exchangeRates.get(0).size()];
        Double[] minCost = new Double[exchangeRates.size()];
        Arrays.fill(minCost, Double.MAX_VALUE);
        minCost[0] =0.0;
        for (int i = 0; i < rates.length; i++ ){
            for (int j = 0; j < rates[0].length; j++){
                if (i == j)
                    rates[i][j] = 0.0;
                else
                    rates[i][j] = -Math.log10(exchangeRates.get(i).get(j));
            }
        }

        int count = 0;
        boolean updateHappenedInThisIteration = true;
        // O(N^3) time
        while(count < rates.length - 1 ){ // relax edges for V-1 times
            if (updateHappenedInThisIteration == false) return false;
            updateHappenedInThisIteration = false;
            for (int src = 0; src < rates.length; src++ ){
                for (int dest = 0; dest < rates[0].length; dest++ ) {
                    if (minCost[dest] > minCost[src] + rates[src][dest]) {
                        updateHappenedInThisIteration = true;
                        minCost[dest] = minCost[dest] + rates[src][dest];
                    }
                }
            }
            count++;
        }

        for (int src = 0; src < rates.length; src++ ){ // detect negative edge weight
            for (int dest = 0; dest < rates[0].length; dest++ ) {
                if (minCost[dest] > minCost[src] + rates[src][dest]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        List<List<Double>> input = new ArrayList<>();
        input.add(new ArrayList<Double>(Arrays.asList(1.0, 0.8631, 0.5903)));
        input.add(new ArrayList<Double>(Arrays.asList(1.1586, 1.0, 0.6849)));
        input.add(new ArrayList<Double>(Arrays.asList(1.6939, 1.46, 1.0)));

        boolean expected = true;
        boolean actual = new DetectArbitrage().detectArbitrage(input);

        System.out.println(actual);

        List<List<Double>> input2 = new ArrayList<>();
        input2.add(new ArrayList<Double>(Arrays.asList(1.0,2.)));
        input2.add(new ArrayList<Double>(Arrays.asList(0.5, 1.0)));
        actual = new DetectArbitrage().detectArbitrage(input2);

    }


}
