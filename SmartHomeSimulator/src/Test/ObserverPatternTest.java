package Test;

import Backend.Observer.SimulatorHomeObserver;
import Backend.Observer.Observable;
import Backend.SimulatorMenu.SimulatorHome;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimulatorHomeObserverTest {

    @Mock
    private SimulatorHome mockMenu;

    private Label timeLabel, dateLabel, userLabel, tempLabel, roomLabel;
    private ToggleButton awayMode;

    private SimulatorHomeObserver observer;

    @BeforeEach
    void setUp() {
        new JFXPanel(); //initialize JavaFX runtime

        //initialize JavaFX components
        timeLabel = new Label();
        dateLabel = new Label();
        userLabel = new Label();
        tempLabel = new Label();
        roomLabel = new Label();
        awayMode = new ToggleButton();

        observer = new SimulatorHomeObserver(mockMenu, timeLabel, dateLabel, userLabel, tempLabel, roomLabel, awayMode);
    }

    @Test
    void testUpdateUpdatesUIComponentsCorrectly() {
        Platform.runLater(() -> {
            // Setup the expected values from the menu
            when(mockMenu.getTime()).thenReturn("12:00");
            when(mockMenu.getDate()).thenReturn("2021-12-25");
            when(mockMenu.getUser()).thenReturn("John Doe");
            when(mockMenu.getTemp()).thenReturn(22F);
            when(mockMenu.getRoom()).thenReturn("Living Room");
            when(mockMenu.isAwayMode()).thenReturn(false);

            //call the update method
            observer.update(mockMenu);

            //verifying the UI components are updated correctly
            assertAll("Verify UI component updates",
                    () -> assertEquals("12:00", timeLabel.getText()),
                    () -> assertEquals("2021-12-25", dateLabel.getText()),
                    () -> assertEquals("John Doe", userLabel.getText()),
                    () -> assertEquals("22°C", tempLabel.getText()),
                    () -> assertEquals("Living Room", roomLabel.getText()),
                    () -> assertFalse(awayMode.isSelected()),
                    () -> assertEquals("Away Mode ON", awayMode.getText())
            );
        });
    }
}
