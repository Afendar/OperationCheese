package operationcheese;

import operationcheese.tools.TimerThread;

public class Operationcheese {

    public static void main(String[] args) {
        TimerThread timer = new TimerThread();
        timer.start();
        Window screen = new Window();
    }
    
}
