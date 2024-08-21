package semaphore.solutions.racecondition;

import java.util.concurrent.Semaphore;

public class HiloContador implements Runnable {

    private final Contador contador;
    Semaphore semaphore;

    public HiloContador(Contador contador, Semaphore semaphore) {
        this.contador = contador;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                semaphore.acquire();
                contador.incrementarContador();
                semaphore.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
