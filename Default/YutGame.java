package Default;

import Controller.*;
import View.*;


import javax.swing.JFrame;
import java.sql.SQLException;

public class YutGame {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        JFrame frame = new JFrame("Yut Game");



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        MainPanel Primary = new MainPanel();
        frame.getContentPane().add(Primary);

        MainPanelController mainControl = new MainPanelController();
        InGameController inGameControl = new InGameController();

        InputInfo _inputInfo = new InputInfo();
        GameManager.getGameManager().setInputInfo(_inputInfo);

        frame.pack();
        frame.setVisible(true);
    }

}
