package semaphore.solutions.starvation;

import java.util.concurrent.Semaphore;

public class StarvationDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        Thread hiloAltaPrioridad = new Thread(new TareaAltaPrioridad(semaphore), "HiloAltaPrioridad");
        Thread hiloBajaPrioridad = new Thread(new TareaBajaPrioridad(semaphore), "HiloBajaPrioridad");

        hiloAltaPrioridad.setPriority(Thread.MAX_PRIORITY);
        hiloBajaPrioridad.setPriority(Thread.MIN_PRIORITY);

        hiloAltaPrioridad.start();
        hiloBajaPrioridad.start();
    }

    static class TareaAltaPrioridad implements Runnable{

        private final Semaphore semaphore;

        public TareaAltaPrioridad(Semaphore semaphore) {
            this.semaphore = semaphore;
        }
        @Override
        public void run() {

            while(true){
                System.out.println("Hilo de alta prioridad ejecutandose");

                try {
                    semaphore.acquire();
                    Thread.sleep(1000);
                    semaphore.release();
                } catch(InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class TareaBajaPrioridad implements Runnable{
        private final Semaphore semaphore;
        public TareaBajaPrioridad(Semaphore semaphore) {
            this.semaphore = semaphore;
        }
        @Override
        public void run() {

            while(true){
                System.out.println("Hilo de baja prioridad ejecutandose");

                try {
                    semaphore.acquire();
                    Thread.sleep(1000);
                    semaphore.release();
                }catch(InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
