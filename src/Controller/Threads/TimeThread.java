package Controller.Threads;

import Controller.GestionDeSceanceController;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by MedTaki on 07/05/2018.
 */
public class TimeThread extends Thread {
    private  GestionDeSceanceController gestionDeSceanceController;
    private Calendar cal;
    private boolean running;

    public TimeThread(GestionDeSceanceController g){
        gestionDeSceanceController = g;
         cal = new GregorianCalendar();
        running = true;
    }

    public TimeThread() {
        cal = new GregorianCalendar();
        running = true;
    }

    @Override
    public void run() {
        while (running){
            loadTime();
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
                ex.getStackTrace();
            }
        }
    }



    private void loadTime() {
        cal = new GregorianCalendar();
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);
        String realTime = String.format("%02d", hour) + " : " + String.format("%02d", minute) + " : " + String.format("%02d", seconds);
        Platform.runLater(()->{gestionDeSceanceController.setTime(realTime);});

    }

    public GestionDeSceanceController getGestionDeSceanceController() {
        return gestionDeSceanceController;
    }

    public void setGestionDeSceanceController(GestionDeSceanceController gestionDeSceanceController) {
        this.gestionDeSceanceController = gestionDeSceanceController;
    }

    public void shutdown() {
        running = false;
    }
}
