package com.company;

public class runnableCounter implements Runnable {
    private String word;
    private Object lock;
    public static int vowelCount;

    public runnableCounter(String word, Object lock){
        this.word = word;
        this.lock = lock;
    }

    public  void aCounter(String str){
        for(int i = 0; i < str.length(); i++)
            if(str.charAt(i) == 'a')
                synchronized (lock) {
                    vowelCount++;
                }
    }

    public  void eCounter(String str){
        for(int i = 0; i < str.length(); i++)
            if(str.charAt(i) == 'e')
                synchronized (lock) {
                    vowelCount++;
                }
    }

    public  void iCounter(String str){
        for(int i = 0; i < str.length(); i++)
            if(str.charAt(i) == 'i')
                synchronized (lock) {
                    vowelCount++;
                }
    }

    public  void oCounter(String str){
        for(int i = 0; i < str.length(); i++)
            if(str.charAt(i) == 'o')
                synchronized (lock) {
                    vowelCount++;
                }
    }

    public void uCounter(String str){
        for(int i = 0; i < str.length(); i++)
            if(str.charAt(i) == 'u')
                synchronized (lock) {
                    vowelCount++;
                }
    }
    public void run(){
        aCounter(word);
        eCounter(word);
        iCounter(word);
        oCounter(word);
        uCounter(word);
    }
}
