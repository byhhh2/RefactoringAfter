package View;

import Model.Player;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel{

    public JButton btnAbility1, btnAbility2,btnAbility;

    public UserPanel(Player user){

        setBackground(Color.white);
        setLayout(null);

        add(user.imgPlayer);

        btnAbility1 = setBtnAbility(user,0);
        add(btnAbility1);
        btnAbility2 = setBtnAbility(user,1);
        add(btnAbility2);

        add(user.lblTurn);
    }//constructor

    private JButton setBtnAbility(Player user, int abilityNumber) {
        btnAbility = new JButton(
                user.abilities[abilityNumber].abilityName);
        btnAbility.setBounds(100*abilityNumber,350,100,50);
        btnAbility.setLayout(null);
        btnAbility.setFont(
                new Font("OCR A Extended", Font.BOLD, 13));
        btnAbility.setBorderPainted(false);  //외곽선
        btnAbility.setFocusPainted(false);  //선택시 테두리 사용x
        btnAbility.setBackground(new Color(225,213,191));
        return btnAbility;
    }
}