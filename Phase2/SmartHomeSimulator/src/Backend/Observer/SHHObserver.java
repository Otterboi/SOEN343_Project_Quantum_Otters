package Backend.Observer;

import Backend.Model.SHHMonitor;
import Backend.SimulatorMenu.SimulatorHome;

public class SHHObserver implements Observer{

    private SimulatorHome menu = SimulatorHome.getInstance();

    public SHHObserver(){

    }


    @Override
    public void update(Observable o) {
        SHHMonitor monitor = (SHHMonitor) o;

        if(menu.getTemp() < monitor.getCurrentZoneTemp() && menu.getTemp() >= 20){
            // open windows
        }

        // if house temp <= 0 alert about pipes


    }
}
