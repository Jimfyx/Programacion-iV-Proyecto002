package semaphore.solutions.deadlock;

import java.util.concurrent.Semaphore;

public class DeadlockDemo {

    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        Object o2 = new Object();
        Object o3 = new Object();
        Semaphore semaphore1 = new Semaphore(1);

        Thread t1 = new Thread(new SyncThread(o1, o2, semaphore1),"hilo1");
        Thread t2 = new Thread(new SyncThread(o2, o3, semaphore1),"hilo2");
        Thread t3 = new Thread(new SyncThread(o3, o1, semaphore1),"hilo3");

        t1.start();
        //Thread.sleep(5000);
        t2.start();
        //Thread.sleep(5000);
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println("Finalizado");
    }

}

class SyncThread implements Runnable {

    public Object ob1;
    public Object ob2;
    Semaphore semaphore;
    public SyncThread(Object ob1, Object ob2, Semaphore semaphore) {
        this.ob1 = ob1;
        this.ob2 = ob2;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " generando lock en " + ob1);
        try {
            semaphore.acquire();
            synchronized (ob1) {
                System.out.println(name + " lock en " + ob1);
                work();
                System.out.println(name + " generando lock en " + ob2);
                synchronized (ob2) {
                    System.out.println(name + " lock en " + ob2);
                    work();
                }
                System.out.println(name + " lock liberado en " + ob2);
            }
            semaphore.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(name + " lock liberado en " + ob1);
        System.out.println("Final");
    }

    private void  work(){
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}

