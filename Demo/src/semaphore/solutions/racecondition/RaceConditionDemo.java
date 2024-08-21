package semaphore.solutions.racecondition;

import java.util.concurrent.Semaphore;

public class RaceConditionDemo {

        public static void main(String[] args) throws InterruptedException {
            Contador contador = new Contador();

            Semaphore semaphore = new Semaphore(1);

            Runnable tarea1 = new HiloContador(contador, semaphore);
            Runnable tarea2 = new HiloContador(contador, semaphore);
            Runnable tarea3 = new HiloContador(contador, semaphore);
            Runnable tarea4 = new HiloContador(contador, semaphore);

            Thread t1 = new Thread(tarea1);
            Thread t2 = new Thread(tarea2);
            Thread t3 = new Thread(tarea3);
            Thread t4 = new Thread(tarea4);

            t1.start();
            t2.start();
            t3.start();
            t4.start();

            t1.join();
            t2.join();
            t3.join();
            t4.join();

            System.out.println("Valor final contador: " + contador.getContador());
        }

}
