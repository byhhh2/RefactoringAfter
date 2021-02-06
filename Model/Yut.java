package Model;

import Default.GameManager;
import View.InGameView;

import javax.swing.*;

public class Yut extends JLabel implements Runnable{

    Thread  myThread;
    int nSleepTime, nResult; //thread사용시 필요한 변수와 윷던지고 나온결과값
    public String[] iconImageName = new String[6];  //윷결과에 따라 보여줄 이미지의 이름을 저장할 문자열배열
    public ImageIcon[]    iconYut;                  //윷결과에 따라 보여줄 이미지 배열

    private InGameView inGameView;
    private InGameData inGameData;

    public Yut(){
        super();

        inGameView = GameManager.getGameManager().getInGameView();      //ingame view 받아오기
        inGameData = GameManager.getGameManager().getInGameData();    //ingame data 받아오기

        //문자열 배열에 이미지 이름 저장
        iconImageName[0] = "images/Do.png";
        iconImageName[1] = "images/Gae.png";
        iconImageName[2] = "images/Girl.png";
        iconImageName[3] = "images/Yut.png";
        iconImageName[4] = "images/Mo.png";
        iconImageName[5] = "images/Bdo.png";

        iconYut= new ImageIcon[6];
        //이미지 배열생성하고 이미지 이름에 따라 이미지 연결하기
        for( int i=0; i<6; i++)
            iconYut[i] = new ImageIcon(iconImageName[i]);

        nSleepTime = 100;
        setIcon(iconYut[0]);  //시작시 첫 이미지 설정
}
    public Yut(String arg){
        super(arg);
    }

    //thread 에 필요한 데이터 초기화
    private void initInstanceDatas(){
        myThread = null;
        nSleepTime = 1000;
    }


    public void setResult(int arg)  { nResult= arg;}
    public int getResult()          {return nResult;}

    public void setResultYutImg(int result){
        setIcon(iconYut[result-1]);
    }

    public void start(){
        myThread = new Thread(this);
        myThread.start();
    }

    public void stop(){
        if(myThread != null) myThread.stop();
    }


    @Override
    public void run(){
        //thread 실행. 도부터 빽도까지 이미지 보여주기
        for(int i=0;i<6;i++) {
            setIcon(new ImageIcon(iconImageName[i]));
            try { myThread.sleep(nSleepTime); }
            catch (Exception e) { }
        }

        //윷 던진 결과에 따라 이미지, 글자로보여주는이미지 변경
        setIcon(new ImageIcon(iconImageName[nResult-1]));
        inGameView.lblYutResult.setIcon(inGameData.iconYutText[nResult-1]);


    }//run()


}//LabelThread Class