public class ReadyChecker {
    private static ReadyChecker instance = new ReadyChecker();
    public static ReadyChecker getInstance() {return instance;}

    private boolean ready = false;

    public synchronized void waitForReady() {
        while (!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void wakeUp() {
        ready = true;
        notifyAll();
    }
}
