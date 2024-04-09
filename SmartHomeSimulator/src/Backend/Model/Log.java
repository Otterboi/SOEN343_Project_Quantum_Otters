package Backend.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Log {

    private ObservableList<String> logEntriesConsole;
    private ArrayList<String> logEntries;
    private static Log INSTANCE;

    private Log() {
        logEntriesConsole = FXCollections.observableArrayList();
        logEntries = new ArrayList<>();
    }

    public static Log getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Log();
        }
            return INSTANCE;

    }


    public ObservableList<String> getLogEntriesConsole() {
        return logEntriesConsole;
    }
    public ArrayList<String> getLogEntries(){return logEntries;}
}
