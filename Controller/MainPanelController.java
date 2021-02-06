package Controller;

import View.MainPanel;
import Default.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// MainPanel, ExplainPanelController,RankPanelController의
// 마우스리스너, 액션리스너 부분을 통일하고 재정의
public class MainPanelController {

    private MainPanel mainPanel;

    public MainPanelController() {
        mainPanel = GameManager.getGameManager().getMainPanel();

        //MenuPanel의 버튼 4개에 액션리스너(MenuSelect)와 btnMouseEvent클래스 추가
        mainPanel.menuPanel.btnStart.addActionListener(new MenuSelect());
        mainPanel.menuPanel.btnExplain.addActionListener(new MenuSelect());
        mainPanel.menuPanel.btnExit.addActionListener(new MenuSelect());
        mainPanel.menuPanel.btnRank.addActionListener(new MenuSelect());

        mainPanel.menuPanel.btnStart.addMouseListener(new BtnColorMouseAdapter());
        mainPanel.menuPanel.btnExplain.addMouseListener(new BtnColorMouseAdapter());
        mainPanel.menuPanel.btnExit.addMouseListener(new BtnColorMouseAdapter());
        mainPanel.menuPanel.btnRank.addMouseListener(new BtnColorMouseAdapter());


        //ExplainPanel
        mainPanel.explainPanel.btnPrev.addMouseListener(new BtnColorMouseAdapter());
        mainPanel.explainPanel.btnNext.addMouseListener(new BtnColorMouseAdapter());
        //btnNext, btnPrev버튼에 액션리스너 추가
        mainPanel.explainPanel.btnNext.addActionListener(new MoveExplainPageListener());
        mainPanel.explainPanel.btnPrev.addActionListener(new MoveExplainPageListener());



        //RankPanel
        mainPanel.rankPanel.btnMenu.addMouseListener(new BtnColorMouseAdapter());
        //menu버튼에 액션리스너 추가
        mainPanel.rankPanel.btnMenu.addActionListener(new BackToMenu());

    }//constructor


    //내부클래스 액션리스너
    //MenuPanel의 버튼 클릭시 해당 뷰를 보여주는 메소드 호출
    private class MenuSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object Button = e.getSource();

            if (mainPanel.menuPanel.btnStart == Button) mainPanel.showInGame();
            else if (mainPanel.menuPanel.btnExplain == Button) mainPanel.showExplain();
            else if (mainPanel.menuPanel.btnRank== Button) mainPanel.showRank();
            else if (mainPanel.menuPanel.btnExit == Button) { System.exit(0);}

        }
    }//MenuSelect


    //버튼에 마우스 올렸을 때 색 변경
    public class BtnColorMouseAdapter extends MouseAdapter {
        public void mouseEntered(MouseEvent e){
            JButton btn = (JButton)e.getSource();
            btn.setForeground(new Color(150,100,50)); //버튼에 들어가면 색 변경
        }
        public void mouseExited(MouseEvent e) {
            JButton btn = (JButton)e.getSource();
            btn.setForeground(Color.black);  //버튼을 나오면 다시 원래 색으로 변경
        }
    }//btnColorMouseAdapter



    //RankPanel의 menu버튼을 클릭하면 MainPanel의 Menu페이지를 보여줌
    //MainPanel의 showMenu 메소드 호출
    private class BackToMenu implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            mainPanel.showMenu();
            mainPanel.rankPanel.repaint();
        }
    }//backtoMenu


    //ExplainPanel의 btnNext,btnPrev 클릭시 페이지 이동(이미지 교체, 게임시작)
    private class MoveExplainPageListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton)e.getSource();
            //버튼이 next일때 다음 페이지의 이미지 변경
            if(btn == mainPanel.explainPanel.btnNext){
                //지금 페이지가 마지막 페이지면 게임 바로시작
                if(mainPanel.explainPanel.imageIndex == 3) {
                    mainPanel.showInGame();
                    return;
                }
                mainPanel.explainPanel.imageIndex += 1;  //이미지의 인덱스 +1
                if(mainPanel.explainPanel.imageIndex == 3){ btn.setText("Start"); }  //마지막 설명페이지에서 start로 버튼 이름 변경
                mainPanel.explainPanel.btnPrev.setText("Prev Page"); //게임설명 첫페이지를 제외하고 prev버튼 이름을 prev page로 변경
            }
            //버튼이 prev 일때 이전 페이지 변경
            else if(btn == mainPanel.explainPanel.btnPrev){
                //지금 페이지가 첫번째 페이지면 게임의 메뉴로 돌아가기
                if(mainPanel.explainPanel.imageIndex == 0)
                    mainPanel.showMenu();
                else mainPanel.explainPanel.imageIndex -= 1;  //이미지의 인덱스 -1
                if(mainPanel.explainPanel.imageIndex ==0)
                    btn.setText("Menu");  //첫번째 설명페이지에서 prev 버튼을 menu로 이름 변경
                mainPanel.explainPanel.btnNext.setText("Next Page"); //첫번째 페이지를 제외하고 next버튼을 next page로 변경
            }
//            setPannelIconImage();
            mainPanel.explainPanel.lblTitle.setIcon(mainPanel.explainPanel.explains[mainPanel.explainPanel.imageIndex]); //인덱스에 따라 이미지 설정
            mainPanel.explainPanel.repaint();
        }

//        private void setPannelIconImage() {
//            _explain.lblTitle.setIcon(_explain.explains[_explain.imageIndex]); //인덱스에 따라 이미지 설정
//        }

    }//moveExplainPageListener

}//Controller.MainPanelController
