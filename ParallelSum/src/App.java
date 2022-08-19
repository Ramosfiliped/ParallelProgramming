import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Pool.ThreadPool;
import Sum.Reducer;

public class App {
    public static void main(String[] args) throws Exception {
        List<Integer> input = new LinkedList<Integer>();
		Random r = new Random();
		Integer expect = 0;

		for(int i = 0; i < 10000000; i++) {
            Integer number = r.nextInt(10);
            input.add(number);
        }
        
        for(Integer number : input)
            expect += number;

        Reducer reducer = new Reducer();

        reducer.splitData(input);
        reducer.parallelReduce();

        ThreadPool.poolShutdown();
    }
}
