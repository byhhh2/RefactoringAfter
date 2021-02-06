package View;

import Default.*;

////JPanel 상속
//public class MainPanel extends JPanel{
//    //필드 -패널들과(view) 각 패널의 컨트롤러 + gamedata(model), 마우스리스너이벤트처리 클래스
//    protected MenuPanel Menu;
//    protected ExplainPanel GameExplain;
//    protected RankPanel Rank;
//    protected InGameView GameStart;
//    protected InGameData GameData;
//
//
//    //생성자
//    //예외처리 - throws 예외객체 떠넘기기. YutGame에서 MainPanel을 생성하지만 예외처리 하는 부분이 없음
//    public MainPanel() throws SQLException, ClassNotFoundException {
//        GameManager.getGameManager().setMainPanel(this);                   //GameManager에 MainPanel 설정
//
//        setPreferredSize(new Dimension(1000,800));
//        setLayout(null);
//
//        //패널, 컨트롤러, InGameData 객체 생성
//        Menu = new MenuPanel();
//        GameExplain = new ExplainPanel();
//        GameData = new InGameData();
//        GameStart = new InGameView();
//        Rank = new RankPanel();
//
//
//        //Mainpanel에 각 패널들 추가
//        add(Rank);
//        add(Menu);
//        add(GameExplain);
//        add(GameStart);
//
//        //게임 시작시 메뉴패널보여주는 메소드 호출
//        showMenu();
//        //  showInGame();
//    }
//
//    //메소드 - 각 패널을 MainPanel에 보여주는 메소드. setVisible을 사용함.
//    //메뉴패널을 보여주는 메소드. 생성자에서 사용
//    public void showMenu(){
//        showPanel(true,false,false,false);
//    }
//
//    //게임뷰를 보여주는 메소드.
//    public void showInGame(){
//        showPanel(false,true,false,false);
//    }//게임시작
//
//    //explainpanel을 보여주는 메소드
//    public void showExplain(){
//        showPanel(false,false,true,false);
//    }//게임방법
//
//    //rankpanel을 보여주는 메소드
//    public void showRank(){
//        showPanel(false,false,false,true);
//    }
//
//    /* sun show method extract*/
//    public void showPanel(boolean menu,boolean gamestart,boolean gameexplain,boolean rank)
//    {
//        Menu.setVisible(menu);
//        GameStart.setVisible(gamestart);
//        GameExplain.setVisible(gameexplain);
//        Rank.setVisible(rank);
//    }
//
//
//
//
//
//
//}

import Model.InGameData;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainPanel extends JPanel{
    public MenuPanel menuPanel;
    public ExplainPanel explainPanel;
    public RankPanel rankPanel;
    protected InGameView inGameView;
    protected InGameData inGameData;


    public MainPanel() throws SQLException, ClassNotFoundException {
        GameManager.getGameManager().setMainPanel(this);

        setPreferredSize(new Dimension(1000, 800));
        setLayout(null);

        menuPanel = new MenuPanel();
        explainPanel = new ExplainPanel();
        inGameData = new InGameData();
        inGameView = new InGameView();
        rankPanel = new RankPanel();

        add(rankPanel);
        add(menuPanel);
        add(explainPanel);
        add(inGameView);

        showMenu();
        btnInit();//수정
    }

    public void showMenu(){
        showPanel(true,false,false,false);
    }


    public void showInGame(){
        showPanel(false,true,false,false);
    }

    public void showExplain(){
        showPanel(false,false,true,false);
    }

    public void showRank(){
        showPanel(false,false,false,true);
    }

    public void showPanel(boolean menu,boolean gamestart,boolean gameexplain,boolean rank)
    {
        menuPanel.setVisible(menu);
        inGameView.setVisible(gamestart);
        explainPanel.setVisible(gameexplain);
        rankPanel.setVisible(rank);
    }

    public void btnInit() {
        setBtnInit(explainPanel.btnNext);
        setBtnInit(explainPanel.btnPrev);
        setBtnInit(menuPanel.btnExit);
        setBtnInit(menuPanel.btnExplain);
        setBtnInit(menuPanel.btnRank);
        setBtnInit(menuPanel.btnStart);
        setBtnInit(rankPanel.btnMenu);
    }

    public void setBtnInit(JButton btn) {
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setVisible(true);
    }
}