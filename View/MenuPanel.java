package View;

import javax.swing.*;
import java.awt.*;

import Default.*;

public class MenuPanel extends JPanel {

    private JLabel lblTitle;
    public JButton btnStart, btnExplain, btnExit, btnRank;

    public MenuPanel() {

        GameManager.getGameManager().setMenuPanel(this);

        setBounds(0,0,1000, 800);
        setBackground(Color.white);
        setLayout(null);

        lblTitle = new JLabel(new ImageIcon("images/Title.PNG"));
        lblTitle.setBounds(220, 150, 600, 120);
        lblTitle.setVisible(true);
        add(lblTitle);

        btnStart = new JButton("Start");
        setBtn(btnStart, 300, 400, 400, 100);
        add(btnStart);

        btnExplain = new JButton("How to Play");
        setBtn(btnExplain, 300, 500, 400, 100);
        add(btnExplain);

        btnRank = new JButton("Rank");
        setBtn(btnRank, 300, 600, 400, 100);
        add(btnRank);

        btnExit = new JButton("Exit");
        setBtn(btnExit, 300, 700, 400, 100);
        add(btnExit);


    }//constructor

    public void setBtn(JButton btn, int x, int y, int width, int height) {
        btn.setBounds(x, y, width, height);
        btn.setFont(new Font("OCR A Extended", Font.BOLD, 40));
    }


}