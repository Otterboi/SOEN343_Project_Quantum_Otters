package UI.Simulator;

import Backend.HouseLayout.*;
import Backend.Model.DateTime;


import java.net.URL;
import java.text.DateFormat;
import java.util.*;

import Backend.Model.Log;
import UI.SmartHomeModules.SHModulesController;
import Util.TemperatureUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import Backend.Observer.*;
import Backend.SimulatorMenu.SimulatorHome;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.text.SimpleDateFormat;


public class SimulatorHomeController implements Initializable {

    @FXML
    Pane r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12;
    private static Pane[] paneArray;

    @FXML
    private AnchorPane simulatorHome, roomPanes;

    @FXML
    Button editSimulationBTN;
    Stage stage;
    @FXML
    private Label dateTimeLabel;
    @FXML
    private Slider speedSlider;
    @FXML
    private Slider slider;
    @FXML
    private Label currentMultiplier;
    @FXML
    private ToggleButton startStopToggle, stopSHHBTN, awayModeButton;
    @FXML
    private Label chosenDate;
    @FXML
    private Label chosenTime;

    @FXML
    ListView<String> consoleLog;

    @FXML
    private Label userLabel, tempLabel, roomLabel;
    private DateTime dateTime; // Instance of DateTime model for managing time
    private SimulatorHome menu;
    private Timer timer = new Timer(); // Timer for scheduling time updates
    private boolean isInitialized = false;
    private int timeCount = 0, policeTimerCount = 0;
    private boolean flag = false, policeFlag = false;
    private float previousTemp = 0f;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        consoleLog.setItems(Log.getInstance().getLogEntriesConsole());
        consoleLog.setFocusTraversable(false);
        menu = SimulatorHome.getInstance();
        dateTime = DateTime.getInstance();
        Pane[] initPanes = {r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12};
        paneArray = initPanes;

        SimulatorHomeObserver menuObserver = new SimulatorHomeObserver(menu, chosenTime, chosenDate, userLabel, tempLabel, roomLabel, awayModeButton);
        menu.attachObserver(menuObserver);
        menu.notifyObservers(menu);

        // Assuming dateTime object is correctly initialized
        dateTime.dateTimeProperty().addListener((observable, oldValue, newValue) -> {
            // This method will be called every second
            Platform.runLater(() -> {
                updateDateTimeDisplay(); // Ensure this method updates UI based on dateTime.getDate()
            });
        });
        dateTime.startTime(); // Start the clock
        setupMultiplierSliderListener();

        for (int i = 0; i < House.getRooms().size(); i++) {
            Room r = House.getRooms().get(i);
            RoomObserver ro = new RoomObserver(paneArray[i], r);
            r.getObservers().clear();
            r.attachObserver(ro);
            updateTemperatureDisplay();
            paneArray[i].setVisible(true);

            if (r instanceof OutdoorRoom) {
                paneArray[i].getChildren().get(4).setVisible(false);
                if (!((OutdoorRoom) r).isGarage()) {
                    paneArray[i].getChildren().get(6).setVisible(false);
                }
            }

            r.notifyObservers(r);

        }

        if (House.isSHHOn()) {
            stopSHHBTN.setSelected(true);
            stopSHHBTN.setText("Stop SHH");
        } else {
            stopSHHBTN.setSelected(false);
            stopSHHBTN.setText("Stop SHH");
        }

    }

    @FXML
    public void handleBedroomClick(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/SmartHomeModules/SHModules.fxml"));

            SHModulesController controller;

            Pane eventPane = (Pane) event.getSource();
            for (int i = 0; i < paneArray.length; i++) {
                if (eventPane.getId().equals(paneArray[i].getId())) {
                    controller = new SHModulesController(House.getRooms().get(i));
                    loader.setController(controller);
                    break;
                }
            }
            Parent root = loader.load();
            stage = new Stage();
            Scene scene = new Scene(root);
            stage.setResizable(false);


            stage.setScene(scene);
            stage.setTitle("Room");
            stage.show();

        } catch (Exception e) {
            System.out.println("oops");
            System.out.println(e);
            e.printStackTrace();

        }
    }

    @FXML
    public void handleEditClick(ActionEvent event) {
        try {
            Parent usr = FXMLLoader.load(getClass().getResource("/UI/Simulator/User.fxml"));
            Scene scene = new Scene(usr);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("User Profiles");
            stage.show();

        } catch (Exception e) {
            System.out.println("error edit user button");
            System.out.println(e);
        }
    }

    public void enableSHHSwitch() {
        if (stopSHHBTN.isSelected()) {
            stopSHHBTN.setText("Stop SHH");
            House.setSHHOn(true);
        } else {
            stopSHHBTN.setText("Start SHH");
            House.setSHHOn(false);
        }
    }

    private void updateDateTimeDisplay() {
        // This method updates the displayed date and time based on the current dateTime
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        chosenTime.setText(timeFormat.format(dateTime.getDate().getTime()));
        chosenDate.setText(dateFormat.format(dateTime.getDate().getTime()));

        menu.setDate(dateFormat.format(dateTime.getDate().getTime()));
        menu.setTime(dateTime.getTimeAsString());
        updateTemperatureDisplay();
        temperatureControl();
        checkPoliceTimer();
    }

    private void checkPoliceTimer() {
        if (SimulatorHome.getInstance().isAwayMode()) {
            if (!policeFlag) {
                for (Room room : House.getRooms()) {
                    if (room.isMotionDetectorTriggered()) {
                        policeTimerCount++;
                        break;
                    }
                }
                if (policeTimerCount == SimulatorHome.getInstance().getPoliceTimer() && policeTimerCount != 0) {
                    showAlert("Emergency", "Calling police...");
                    policeTimerCount = 0;
                    policeFlag = true;
                }
            }
        } else {
            policeFlag = false;
            policeTimerCount = 0;
        }
    }

    private void temperatureControl() {
        float roomTempChange = 0f;
        float zoneTempChange = 0f, zoneTemp = 0f;

        if (House.getAvgTemp() <= 0) {
            if (!House.isTooCold()) {
                System.out.println("WARNING: Home is too cold! Pipes in danger of bursting!");
                House.setTooCold(true);
                String output = "Home is too cold, pipes are in danger of bursting.";
                Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
                Log.getInstance().getLogEntries().add(
                        "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString() +
                                "\nEvent: Temperature Warning" +
                                "\nLocation: " + "Entire Household" +
                                "\nTriggered By: SHH" +
                                "\nDestined to: " + House.getLoggedInUser().getName() +
                                "\nEvent Details: " + output
                );
            }
        }

        for (Room room : House.getRooms()) {
            if (room.getZone() != null) {

                if (!flag) {
                    previousTemp = room.getTemp();
                    flag = true;
                }

                if (!room.isOverwritingTemp()) {
                    zoneTemp = room.getZone().getDesiredTemp();
                } else {
                    zoneTemp = room.getOverwritigTemp();
                }
                roomTempChange = room.getTemp();
                zoneTempChange = room.getZone().getCurrentTemp();

                if (House.isHouseEmpty() && !room.getZone().isSummer()) {
                    room.getZone().setDesiredTemp(17);
                } else if (!House.isHouseEmpty() && !room.getZone().isSummer()) {
                    room.getZone().setUser(false);
                    room.getZone().updateDesiredTemp();
                    room.getZone().setUser(true);
                }

                if (!House.isHouseEmpty() && room.getZone().isSummer() && room instanceof IndoorRoom && House.isSHHOn() && !House.isDoorBlocked()) {
                    if (SimulatorHome.getInstance().getTemp() >= 20 && room.getTemp() > SimulatorHome.getInstance().getTemp()) {
                        if (!((IndoorRoom) room).isWindowBlocked()) {
                            ((IndoorRoom) room).setWindowOpen(true);
                            String output = "Window in " + room.getRoomName() + " is now OPEN";
                            System.out.println(output);
                            Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
                            Log.getInstance().getLogEntries().add(
                                    "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString() +
                                            "\nEvent: Window State Change" +
                                            "\nLocation: " + room.getRoomName() +
                                            "\nTriggered By: SHH" +
                                            "\nEvent Details: Window is now OPEN"
                            );
                        }
                    }
                }

                if (!room.isTempDecaying()) {
                    if (room.getTemp() < zoneTemp && House.isSHHOn()) {
                        // Heat the house
                        roomTempChange = roomTempChange + 0.1f;
                        zoneTempChange = zoneTempChange + 0.1f;
                        room.setHeating(true);
                        room.setTemp((float) (Math.round(roomTempChange * 100.0) / 100.0));
                        room.getZone().setCurrentTemp((float) (Math.round(zoneTempChange * 100.0) / 100.0));
                    } else if (room.getTemp() > zoneTemp || !House.isSHHOn()) {
                        // Cool the house
                        room.setCooling(true);
                        roomTempChange = roomTempChange - 0.1f;
                        zoneTempChange = zoneTempChange - 0.1f;
                        room.setTemp((float) (Math.round(roomTempChange * 100.0) / 100.0));
                        room.getZone().setCurrentTemp((float) (Math.round(zoneTempChange * 100.0) / 100.0));
                    } else if (zoneTemp == room.getTemp() && House.isSHHOn()) {
                        // Make the house start to = the outside temp
                        room.setTempDecaying(true);
                        room.setOff(true);
                        House.setTooCold(false);
                    }
                } else {
                    decay(room);
                }

                if (room.getTemp() >= 135) {
                    room.setAway(false);
                    SimulatorHome.getInstance().setAwayMode(false);
                    showAlert("WARNING", "Temperature in " + room.getRoomName() + " has exceeded 135 degrees celsius!");
                    String output = "Temperature exceeded 135 degrees celsius. Away mode is turned OFF!";
                    Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
                    Log.getInstance().getLogEntries().add(
                            "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString() +
                                    "\nEvent: Temperature Warning" +
                                    "\nLocation: " + "Entire Household" +
                                    "\nTriggered By: SHH" +
                                    "\nDestined to: " + House.getLoggedInUser().getName() +
                                    "\nEvent Details: " + output
                    );
                }

                if (timeCount == 60) {
                    timeCount = 0;
                    if (room.getTemp() - previousTemp >= 15) {
                        room.setAway(false);
                        SimulatorHome.getInstance().setAwayMode(false);
                        showAlert("WARNING", "Temperature in " + room.getRoomName() + " increased too quickly!");
                        String output = "Temperature increased too quickly. Away mode is turned OFF!";
                        Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
                        Log.getInstance().getLogEntries().add(
                                "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString() +
                                        "\nEvent: Temperature Warning" +
                                        "\nLocation: " + "Entire Household" +
                                        "\nTriggered By: SHH" +
                                        "\nDestined to: " + House.getLoggedInUser().getName() +
                                        "\nEvent Details: " + output
                        );
                    } else {
                        previousTemp = room.getTemp();
                    }
                }
            }
        }
        timeCount++;
    }

    // Increase or Decrease by 0.05 per second until it reaches temp outside
    private void decay(Room room) {
        float roomTempChange = room.getTemp();
        float zoneTempChange = room.getZone().getCurrentTemp();

        if (room.getZone().isDesiredTempChanged() || (room.getTemp() == (float) Math.floor((room.getZone().getDesiredTemp() + 0.25f) * 100) / 100) || (room.getZone().getCurrentTemp() == (float) Math.floor((room.getZone().getDesiredTemp() + 0.25f) * 100) / 100) ||
                (room.getTemp() == (float) Math.floor((room.getZone().getDesiredTemp() - 0.25f) * 100) / 100) || (room.getZone().getCurrentTemp() == (float) Math.floor((room.getZone().getDesiredTemp() - 0.25f) * 100) / 100)) {

            double temp = room.getTemp() * 100;
            char remain = Double.toString(temp).charAt(3);
            if (remain == '5') {
                StringBuilder strBuild = new StringBuilder(Double.toString(temp));
                strBuild.setCharAt(3, '0');
                room.setTemp((float) Double.parseDouble(strBuild.toString()) / 100);
                room.getZone().setCurrentTemp((float) Double.parseDouble(strBuild.toString()) / 100);
            } else {
                room.setTemp((float) temp / 100);
                room.getZone().setCurrentTemp((float) temp / 100);
            }

            room.setTempDecaying(false);
            room.getZone().setDesiredTempChanged(false);
        } else {
            if (SimulatorHome.getInstance().getTemp() > room.getZone().getDesiredTemp()) {
                roomTempChange = roomTempChange + 0.05f;
                zoneTempChange = zoneTempChange + 0.05f;
                room.setTemp((float) (Math.round(roomTempChange * 100.0) / 100.0));
                room.getZone().setCurrentTemp((float) (Math.round(zoneTempChange * 100.0) / 100.0));
            } else if (SimulatorHome.getInstance().getTemp() < room.getZone().getDesiredTemp()) {
                roomTempChange = roomTempChange - 0.05f;
                zoneTempChange = zoneTempChange - 0.05f;
                room.setTemp((float) (Math.round(roomTempChange * 100.0) / 100.0));
                room.getZone().setCurrentTemp((float) (Math.round(zoneTempChange * 100.0) / 100.0));
            }
        }

    }

    private void setupMultiplierSliderListener() {
        // Listener for the slider to adjust the time speed
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double multiplier = newValue.doubleValue();
            dateTime.setClockSpeedMultiplier(multiplier);
            currentMultiplier.setText(String.format("x %.2f", multiplier));
        });
    }

    public void toggleSimulation() {
        if (startStopToggle.isSelected()) {
            startStopToggle.setText("Stop Simulation");
            dateTime.startTime();
            // Run simulation
        } else {
            startStopToggle.setText("Start Simulation");
            dateTime.stopTime();
            // Pause simulation
        }
    }

    public void editSimulation() {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        menu.setTime(timeFormatter.format(dateTime.getDate().getTime()));
        menu.setDate(dateFormatter.format(dateTime.getDate().getTime()));

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/UI/EditSimulation/EditSimulation.fxml"));

            stage = new Stage();
            Scene scene = new Scene(root);
            stage.setResizable(false);

            stage.setScene(scene);
            stage.setTitle("Edit");
            stage.show();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateTemperatureDisplay() {
        if (!menu.isTempOverwritten()) {
            float temperature = Float.parseFloat(TemperatureUtil.getTemperatureForCurrentTime());
            menu.setTemp(temperature);
        }
    }

    public void handleAwayClick() {
        if (awayModeButton.isSelected()) {
            for (Room room : House.getRooms()) {
                room.setAway(true);
            }

            String output = "Away mode activated. All the doors and windows are closed!";
            Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
            Log.getInstance().getLogEntries().add(
                    "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString() +
                            "\nEvent: Away Mode" +
                            "\nLocation: " + "Entire Household" +
                            "\nTriggered By: SHP" +
                            "\nDestined to: " + House.getLoggedInUser().getName() +
                            "\nEvent Details: " + output
            );
            awayModeButton.setText("Away Mode OFF");
            SimulatorHome.getInstance().setAwayMode(true);
            House.setIsAway(true);
        } else {
            for (Room room : House.getRooms()) {
                room.setAway(false);
                room.setMotionDetectorTriggered(false);
            }

            String output = "House is no longer in away mode. Welcome Home!";
            Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
            Log.getInstance().getLogEntries().add(
                    "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString() +
                            "\nEvent: Away Mode" +
                            "\nLocation: " + "Entire Household" +
                            "\nTriggered By: SHP" +
                            "\nDestined to: " + House.getLoggedInUser().getName() +
                            "\nEvent Details: " + output
            );
            awayModeButton.setText("Away Mode ON");
            SimulatorHome.getInstance().setAwayMode(false);
            House.setIsAway(false);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
