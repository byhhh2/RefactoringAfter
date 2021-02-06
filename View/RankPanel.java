package View;

import Default.*;
import Database.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

//JPanel 상속
public class RankPanel extends JPanel {

    public JButton btnMenu;                                 // MenuPanel로 돌아가기 위한 버튼
    private JLabel lblTitle, lblId, lblCntWin;
    private JTextArea txtRank;                              //DB에서 가져온 결과 출력

    //생성자
    public RankPanel() throws ClassNotFoundException, SQLException {

        GameManager.getGameManager().setRankPanel(this);           //GM에 RankPanel 설정

        setBounds(0,0,1000, 800);
        setBackground(Color.white);
        setLayout(null);

        //MenuPanel로 돌아갈 버튼 생성 및 설정
        btnMenu = new JButton("Menu");
        btnMenu.setBounds(730,720,220,50);
        btnMenu.setFont(new Font("OCR A Extended",Font.BOLD,30));
        add(btnMenu);

        //패널 이름 생성
        makeLblTitle();

        makeLblId();

        makeLblCntWin();

        txtRank=makeTxtRank();
        ShowRank();

    }//construct


    public JLabel makeLabel(String title, int fontsize){
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("OCR A Extended",Font.BOLD,fontsize));
        lbl.setVisible(true);
        add(lbl);
        return lbl;
    }

    public void makeLblTitle(){
        lblTitle=makeLabel("Rank", 50);
        lblTitle.setBounds(50, 30, 200, 120);
    }

    public void makeLblCntWin(){
        lblCntWin= makeLabel("Win", 40);
        lblCntWin.setBounds(700, 130, 600, 120);
    }
    public void makeLblId(){
        lblId = makeLabel("ID", 40);
        lblId.setBounds(220, 130, 600, 120);
    }


    public JTextArea makeTxtRank(){
        JTextArea txtArea;
        txtArea = new JTextArea();
        txtArea.setBounds(100, 250, 800, 450);
        Border lineBorder = BorderFactory.createLineBorder(Color.black, 3);                  //외곽선 설정
        Border emptyBorder = BorderFactory.createEmptyBorder(7, 7, 7, 7);      //패딩 설정
        txtArea.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        txtArea.setFont(new Font("궁서체",Font.BOLD,40));
        txtArea.setVisible(true);
        txtArea.setEditable(false);         //편집 불가능 설정
        add(txtArea);
        return  txtArea;
    }


    //메소드
    public void ShowRank() throws SQLException, ClassNotFoundException {
        System.out.println("show rank");
        Database.connection();
        String selectStmt = "select * from ranking order by cnt_win DESC, name ASC limit 8;" ;
        try {
            //rsMain에 select문을 실행해서 받은 결과값 저장
            ResultSet rsMain = Database.dbExecuteQuery(selectStmt);
            txtRank.setText(getRankList(rsMain));
            Database.disConnection();
        }
        catch (SQLException e) {
            System.out.println("Show Rank ERROR " + e);
            throw e;
        }
    }


    //DB에서 받아온 결과값을 TextArea에 들어갈 String으로 변경
    private static String getRankList(ResultSet rs) throws SQLException {
        System.out.println("getRankList");

        String txtResult="";
        //String txtResult에 '회원Id 승리횟수\n' 순으로 저장
        while (rs.next()) {
            txtResult += rs.getString(1) + "\t\t\t" + rs.getInt(2) + "\n";
        }
        return txtResult;
    }
}
