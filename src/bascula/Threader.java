package bascula;

/**
 * Clase para implementar hilo con sólo un instanciamiento y ejecución en cliclo (bucle).
 * @author dolivo
 */
public class Threader {

    Thread thread;
    ThreadLoop threadAction;
    boolean loopExecute = true;

    boolean doStart = false;
    boolean running = false;

    public enum eStatus {
        RUNNING,
        STOPPING,
        STOPPED
    }

    /**
     *
     * Hilo.
     *
     * @param isLoopExecute Si es true, ejecutará la instrucción hasta invocar
     * el método stop(), en caso contrario, ejecutará una sola vez la
     * instrucción.
     */
    public Threader(boolean isLoopExecute) {
        threadAction = new ThreadLoop();
        this.loopExecute = isLoopExecute;
    }

    private class ThreadLoop implements Runnable {

        @Override
        public void run() {
            int i = 0;
            doStart = true;
            running = true;
            doOnStart();
            while (doStart) {
                doSomething();
                i++;
            }
            running = false;
            doOnStop();
        }
    }

    /**
     * Permite incluir instrucciones adicionales en el cliclo del hilo y se
     * detiene al invocar el método stop() o si el hilo si el parametro del
     * instanciamiento isLoopExecute = false [new Threader(false)].
     */
    public void doSomething() {

    }

    /**
     * Permite incluir instrucciones al iniciar el hilo.
     */
    public void doOnStart() {
    }

    /**
     * Permite incluir instrucciones al detener el hilo y se ejecutará al
     * terminar las instrucciones indicada en el método doSomething().
     */
    public void doOnStop() {

    }

    /**
     * Estado del hilo.
     *
     * @return
     */
    public eStatus getStatus() {
        if (running) {
            if (doStart) {
                return eStatus.RUNNING;
            } else {
                return eStatus.STOPPING;
            }
        } else {
            return eStatus.STOPPED;
        }
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * Inicial el hilo (Instanciando en cada invocación).
     */
    public void start() {
        if (!(thread instanceof Thread)) {
            thread = new Thread(threadAction);
            doStart = true;
            thread.start();
        }
    }

    /**
     * Detiene el hilo cuando termine las instrucciones indicadas en el método doSomething().
     */
    public void stop() {
        this.doStart = false;
        thread = null;
    }
}
