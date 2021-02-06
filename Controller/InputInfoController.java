package Controller;

import Database.Database;
import View.*;
import Model.*;
import Default.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;


public class InputInfoController {

    private ResultSet rs;
    private InputInfo inputInfo;
    private InfoData infoData; //수정

    public InputInfoController() {
        inputInfo = GameManager.getGameManager().getInputInfo();
        infoData = new InfoData();
        inputInfo.btnInput.setEnabled(true);
        inputInfo.btnInput.addActionListener(new ActionListenerNickNameInput());
    }

    public class ActionListenerNickNameInput implements ActionListener { //액션 리스너
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton)e.getSource();
            try {
                String querySelect = "select cnt_win from ranking where name = ?"; //사용자가 입력한 닉네임의 점수를 묻는 query
                rs = infoData.excuteQueryPstmt(querySelect);
                if(rs.next()) { //점수가 존재한다면 기존 회원
                    System.out.println("You're an existing member!");
                    rs = infoData.excuteQueryPstmt(querySelect);
                    String queryUpdate = "UPDATE ranking SET cnt_win = ? where name = ?"; //기존회원이라면 해당 회원의 점수를 Update
                    infoData.excuteUpdatePstmt(queryUpdate, 2, 1);
                }
                else //점수가 존재하지 않는다면 신규회원
                {
                    System.out.println("You're an new member!");
                    String queryInsert = "INSERT INTO ranking VALUES (?,?)"; //신규회원이라면 입력한 닉네임과 처음 이겼으니 이긴횟수 1을 추가
                    infoData.excuteUpdatePstmt(queryInsert, 1, 2);
                }
                System.out.println("Input success");
                btn.setEnabled(false); //한번 입력하면 버튼을 누르지 못하도록
                infoData.inputInfoDisconnect();
                //rank panel
                GameManager.getGameManager().getRankPanel().ShowRank();

            }catch (Exception e1) {
            }

        }
    }
}







