package Backend.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Log {

    private ObservableList<String> logEntries;
    private static Log INSTANCE;

    private Log() {
        logEntries = FXCollections.observableArrayList();
    }

    public static Log getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Log();
        }
            return INSTANCE;

    }


    public ObservableList<String> getLogEntries() {
        return logEntries;
    }
}
