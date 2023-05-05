package sbu.cs;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
    In this exercise, you must write a multithreaded program that finds all
    integers in the range [1, n] that are divisible by 3, 5, or 7. Return the
    sum of all unique integers as your answer.
    Note that an integer such as 15 (which is a multiple of 3 and 5) is only
    counted once.

    The Positive integer n > 0 is given to you as input. Create as many threads as
    you need to solve the problem. You can use a Thread Pool for bonus points.

    Example:
    Input: n = 10
    Output: sum = 40
    Explanation: Numbers in the range [1, 10] that are divisible by 3, 5, or 7 are:
    3, 5, 6, 7, 9, 10. The sum of these numbers is 40.

    Use the tests provided in the test folder to ensure your code works correctly.
 */

public class FindMultiples {
    public class Find implements Runnable {
        private int n;
        private int interval;
        private ArrayList<Integer> multiples = new ArrayList<>();

        public Find(int n, int interval) {
            this.n = n;
            this.interval = interval;
        }

        @Override
        public void run() {
            for (int i = 1; i <= interval; i++) {
                if (i % n == 0) {
                    multiples.add(i);
                }
            }
        }

        public ArrayList<Integer> getMultiples() {
            return multiples;
        }
    }

    /*
    The getSum function should be called at the start of your program.
    New Threads and tasks should be created here.
    */
    public int getSum(int n) {
        int sum = 0;
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        Find find3 = new Find(3, n);
        Find find5 = new Find(5, n);
        Find find7 = new Find(7, n);
        threadPool.execute(find3);
        threadPool.execute(find5);
        threadPool.execute(find7);
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        find3.multiples.addAll(find5.getMultiples());
        find3.multiples.addAll(find7.getMultiples());
        ArrayList<Integer> unique = new ArrayList<>();
        for (Integer multiple : find3.multiples) {
            if (!unique.contains(multiple)) {
                unique.add(multiple);
                sum += multiple;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
    }
}