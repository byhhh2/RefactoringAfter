package Model;

import javax.swing.*;

//Jlabel 상속
public class Pawn extends JLabel {

    //////1 필드캡슐화 private으로 형 변경 + strPawnImg 변수명 변경
    private int         currentIndex;      //말의 현재 위치
    private boolean     isFinished;        //완주 여부
    private String strPawnImg;           //말 이미지 이름
    private int          pawnNumber;        //말 번호

    //생성자
    public Pawn(){
        setBounds(0,0,90,80);
    }
    public Pawn(String img, int x, int y, int width, int height, int number){
        currentIndex = 0;
        isFinished = false;
        strPawnImg = img;
        setIcon(new ImageIcon(img));
        setBounds(x,y,width,height);

        pawnNumber = number;
    }// constructor

    //////2 get,set메서드명 변경, 없는거 구현
    public int getCurrentIndex(){ return currentIndex; }
    public void setCurrentIndex(int idx){ currentIndex = idx;}

    public boolean getIsFinished(){ return isFinished; }
    public void setIsFinished(boolean a){ isFinished = a; }

    public String getPawnImg(){ return strPawnImg; }
    public void setPawnImg(String img){//InGameData의 초기화 메소드 InGameData_init에 사용
        strPawnImg = img;
        setIcon(new ImageIcon(img));
    }

    public int getPawnNumber(){return pawnNumber;}

    //////3 get,set 이용
    public void pawnInit(){
        setCurrentIndex(0);
        setIsFinished(false);
    }
}