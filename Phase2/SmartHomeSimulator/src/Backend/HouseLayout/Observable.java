package Backend.HouseLayout;

import Backend.Observer;

public interface Observable {
    public void attachObserver(Observer o);
    public void detachObserver(Observer o);
    public void notifyObservers(Observable o);
}
