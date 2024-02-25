import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JFrame{
//    private JFrame f;
//    public MainPanel(){
//        f = new JFrame("Log IN");
//        f.setVisible(true);
//    }
    private JPanel SomethingPanel;
    private JButton logInButton;
    private JButton signUpButton;
    private JPanel LogInPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordPasswordField;



    public MainPanel(){

        setContentPane(SomethingPanel);
        setTitle("Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new SimulationPanel();
            }
        });
    }
    public static void main(String[] args) {
        new MainPanel();


    }
}
