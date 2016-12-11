package objectifcheeze;

import objectifcheeze.tools.TimerThread;

public class Objectifcheeze {

    public static void main(String[] args) {
        TimerThread timer = new TimerThread();
        timer.start();
        Window screen = new Window();
    }
    
}
