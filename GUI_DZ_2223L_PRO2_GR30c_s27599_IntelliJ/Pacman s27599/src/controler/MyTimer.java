package controler;

import java.io.Serializable;


public class MyTimer extends Thread implements Serializable {

    private int hours;
    private int minutes;
    private int seconds;
    private int hundredths;


    public MyTimer() {
        hours = 0;
        minutes = 0;
        seconds = 0;
        hundredths = 0;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                return;
            }
            if (minutes >= 60) {
                hours++;
                minutes = 0;
            } else if (seconds >= 60) {
                minutes++;
                seconds = 0;
            } else if (hundredths >= 100) {
                seconds++;
                hundredths = 0;
            } else {
                hundredths++;
            }
        }
    }
    public void stop(int millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    public long toMillis() {
        return ((hours * 60L + minutes) * 60L + seconds) * 1000L + hundredths * 10L;
    }



    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, hundredths);

//        return hours + ":" + minutes + ":" + seconds+":"+hundredths;
    }


}
