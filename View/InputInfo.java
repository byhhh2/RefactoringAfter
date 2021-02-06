package View;

import Default.GameManager;

import java.awt.*;
import javax.swing.*;

public class InputInfo extends JFrame {
    public JTextField userText;//수정
    public JButton btnInput;

    public InputInfo()
    {
        GameManager.getGameManager().setInputInfo(this);
        JPanel inputInfoPanel = new JPanel();
        add(inputInfoPanel);
        inputInfoPanel.setPreferredSize(new Dimension(1000, 800));
        inputInfoPanel.setBackground(Color.white);

        GridLayout grid = new GridLayout(2, 1, 0, 0);
        inputInfoPanel.setLayout(grid);

        JPanel imagePanel = new JPanel();
        JPanel inputPanel = new JPanel();
        imagePanel.setBackground(Color.white);
        inputPanel.setBackground(Color.white);

        inputInfoPanel.add(imagePanel);
        inputInfoPanel.add(inputPanel); //inputInfoPanel은 imagePanel(안내문 이미지를 넣은 패널)과 inputPanel(사용자 입력 field, 입력 버튼)이 있는 패널
        ImageIcon guideImage = new ImageIcon("images/InputStmt.PNG");

        JLabel guideLabel = new JLabel(guideImage);
        imagePanel.add(guideLabel);

        userText = new JTextField(10);
        btnInput = new JButton(new ImageIcon("images/Input.PNG"));
        btnInput.setBorderPainted(false);
        btnInput.setContentAreaFilled(false);
        btnInput.setEnabled(true);

        inputPanel.add(userText);
        inputPanel.add(btnInput);
    }
}