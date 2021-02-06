package View;

import javax.swing.*;
import java.awt.*;
import Default.*;

//게임 방법을 누르면 나오는 패널
public class ExplainPanel extends JPanel {

    public JLabel lblTitle;
    public JButton btnPrev, btnNext, btn;    //게임방법을 이동할 버튼
    public ImageIcon[] explains;        //게임방법의 이미지를 보여줄 배열 선언
    public int     imageIndex;          //이미지배열에 사용할 인덱스
    public String[] buttonName= {"Menu", "Next Page"};


    public ExplainPanel(){
        GameManager.getGameManager().setExplainPanel(this);    //explainpanel 설정하기

        setBounds(0,0,1000,800);
        setBackground(Color.white);
        setLayout(null);

        //이미지 배열에 이미지 생성
        setImage();
        //게임 방법 타이틀
        setLabelTitle();

        //이전 페이지로 넘어가게 하는 버튼
        btnPrev = setBtn(buttonName[0],50,720,220,50);
        add(btnPrev);
        //다음 페이지로 넘어가게 하는 버튼
        btnNext = setBtn(buttonName[1],730,720,220,50);
        add(btnNext);

    }//constructor

    private void setLabelTitle() {
        lblTitle = new JLabel();
        lblTitle.setBounds(0,100,1000,562);
        lblTitle.setIcon(explains[imageIndex]);  //라벨이 이미지 붙이기
        lblTitle.setVisible(true);
        add(lblTitle);
    }

    private JButton setBtn(String name,int x, int y, int width,int height) {
        btn = new JButton(name);
        btn.setBounds(x,y,width,height);
        btn.setFont(new Font("OCR A Extended",Font.BOLD,30));
        return btn;
    }

    private void setImage() {
        imageIndex=0;               //인덱스 0으로 설정 : 처음 게임설명이미지
        explains = new ImageIcon[4];
        for(int i=0;i<4;i++)
            explains[i] = new ImageIcon("images/rule"+(i+1)+".png");

    }

}//YutGamePanel()