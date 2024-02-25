import javax.swing.*;

public class SimulationPanel extends JFrame{
    private JPanel Simulation;

    public SimulationPanel(){
        setContentPane(Simulation);
        setTitle("Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
