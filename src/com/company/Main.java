package com.company;

import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

import static com.company.runnableCounter.vowelCount;

public class Main {

    private static BlockingQueue queue;
    private static BlockingQueue singleThreadQueue;

    public static void main(String[] args) throws InterruptedException {
	    Scanner in = new Scanner(System.in);
	    boolean onlyIntegers = false;
	    int numOfStrings = 0;

	    while(onlyIntegers == false){
            System.out.println("How many Strings would you like in the word bank?");
            String response = in.next();

            try{
                numOfStrings = Integer.parseInt(response);
                onlyIntegers = true;

            } catch (NumberFormatException e) {
                System.out.println("Only type numbers please.");
                in.nextLine();
            }
        }

	    in.nextLine();
	    queue = new ArrayBlockingQueue(numOfStrings);

	    insertTerms();

	    singleThreadQueue = new ArrayBlockingQueue(numOfStrings);
	    for(Object e : queue)
	        singleThreadQueue.put(e);




        int wordCount = 1;
        for(Object e : queue){
            System.out.println("Word " + wordCount + ": " + e);
            wordCount++;
        }

        long startTime = System.nanoTime();
        Object lock = new Object();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for(int i = 0; i < numOfStrings; i++){
            executorService.submit((Runnable) new runnableCounter((String) queue.take(), lock));
        }

        executorService.shutdown();

        try{
            executorService.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        System.out.println("\nMulti-threaded: There are " + vowelCount + " vowels.");
        long elapsedTime = endTime - startTime;
        double elapsedSeconds = (double) elapsedTime / 1_000_000_000;
        System.out.println("It took " + elapsedSeconds + " seconds to complete the method via multithreading.");

        startTime = System.nanoTime();
        singleThreadVowelCounter();
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
         elapsedSeconds = (double) elapsedTime / 1_000_000_000;
        System.out.println("It took " + elapsedSeconds + " seconds to complete the method via a single thread.");
    }

    private static void insertTerms() throws InterruptedException {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();

        while(queue.remainingCapacity() > 0) {
            String producedString = "";

            for(int j = 0; j < 7; j++){
                String randLetter = alphabet.split("")[rand.nextInt(alphabet.length())];
                producedString += randLetter;
            }

            System.out.println("Remaining capacity is: " + queue.remainingCapacity());
            queue.put(producedString);
        }
    }

    public static void singleThreadVowelCounter(){
        int numVowels = 0;
        int size = singleThreadQueue.size();

        for(int i = 0; i < size; i++){
            String str = (String) singleThreadQueue.peek();

            for(int j = 0; j < str.length(); j++)
                if(str.charAt(j) == 'a')
                    numVowels++;

            for(int k = 0; k < str.length(); k++)
                if(str.charAt(k) == 'e')
                    numVowels++;

            for(int l = 0; l < str.length(); l++)
                if(str.charAt(l) == 'i')
                    numVowels++;

            for(int m = 0; m < str.length(); m++)
                if(str.charAt(m) == 'o')
                    numVowels++;

            for(int n = 0; n < str.length(); n++)
                if(str.charAt(n) == 'u')
                    numVowels++;

            singleThreadQueue.remove();
        }
        System.out.println("\nSingle-threaded: there are " + numVowels + " vowels.");
    }
}

// Your app should also utilize the following elements:
//
//    join() method
//
//
// Elements to think about may include:
//
//    How do we pass through variables to a Multithreaded class
//    How do we span different tasks within the overridden run() method
//
