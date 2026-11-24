import java.util.ArrayList;
import java.util.List;

public class Lucky {
    private static final int max_ticket = 999_999;
    private static final int thread_count = 3;

    static boolean isLucky(int number){
        return digitSum(number / 1000) == digitSum(number % 1000);
    }

    private static int digitSum(int digit){
        return digit / 100 + digit / 10 % 10 + digit % 10;
    }

    static class LuckyThread extends Thread{
        private final int from;
        private final int to;
        private final List<Integer> found = new ArrayList<>();

        LuckyThread(int from, int to){
            this.from = from;
            this.to = to;
        }

        @Override
        public void run(){
            for(int n = from; n <= to; n++){
                if (isLucky(n)) {
                    found.add(n);
                }
            }
        }

        List<Integer> getFound(){
            return found;
        }
    }

    public static void main(String[] args) throws InterruptedException{
        int rangSize = max_ticket / thread_count;
        LuckyThread[] threads = new LuckyThread[thread_count];

        for(int i = 0; i < thread_count; i++){
            int from = i * rangSize + 1;
            int to = (i == thread_count - 1) ? max_ticket : (i + 1) * rangSize;
            threads[i] = new LuckyThread(from, to);
            threads[i].start();
        }

        int total = 0;
        StringBuilder out = new StringBuilder();
        for(LuckyThread t : threads){
            t.join();
            for(int n : t.getFound()){
                out.append(n).append('\n');
            }
            total += t.getFound().size();
        }

        System.out.println(out);
        System.out.println("Total: " + total);
    }
}