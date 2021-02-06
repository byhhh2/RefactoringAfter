package Model;

import Default.GameManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InGameData {
    public static Player leftPlayer;
    public Player rightPlayer;     //게임을 진행할 플레이어 두 명
    public Pawn focusedPawn; //현재 선택된 말
    public Pawn focusedPreview;
    public int throwResult; //마지막으로 던진 윷의 결과
    public BoardIndexData[] boardIndexer;   //게임판 위 29칸에 대한 데이터
    public Point[] leftPawnWaiting, rightPawnWaiting;   //대기칸의 좌표

    public ImageIcon[] iconYutText;  //윷을 던지면 윷 오른쪽에 보여주는 글자 이미지들

    public static Player activatedPlayer;  //현재 턴을 진행중인 플레이어

    public ArrayList<ThrowData> previewPawns;   //말 이동에 쓰일 "윷 던지기 결과데이터"를 저장하는 리스트
    public int throwableNCnt;   //남은 던질 수 있는 횟수
    public boolean flag;
    public boolean catchOppenent;

    public ThrowData clicked;

    public int pawnNow,pawnArrive;

    //게임 데이터 생성하는부분
    public InGameData() {

        GameManager.getGameManager().setInGameData(this);   //게임매니저에 인스턴스를 등록해둔다

        //////3  각 플레이어 생성 + 플레이어 움직이는 사진지정
        makePlayer();//make player and set player Image
        flag=true;
        throwResult = 0;
        activatedPlayer = leftPlayer;   //왼쪽 플레이어부터 게임 진행 시작
        clicked = null;

        previewPawns = new ArrayList<ThrowData>();  //던지기 결과 리스트 생성

        //각 플레이어 말들의 대기 칸 좌표 미리 저장
        //////4
        setPawnCoordinates();

        //기본적인 길 인덱스
        //////5
        setBoardRoadIndex();

        //윷던지고 결과 텍스트 이미지 변경
        //////6
        setYutTextImage();
    }//constructor

    //////3  각 플레이어 생성 + 플레이어 움직이는 사진지정
    public void makePlayer()
    {
        leftPlayer=makePlayer("Left",80,80);
        rightPlayer=makePlayer("Right",100,75);
    }

    public Player makePlayer(String playerImg, int width, int height){
        Player player = new Player("images/"+playerImg+"_0.png",80,80);
        player.iconPlayer[0] = new ImageIcon("images/"+playerImg+"_1.gif");
        player.iconPlayer[1] = new ImageIcon("images/"+playerImg+"_2.png");
        return player;
    }

    //////4
    public void setPawnCoordinates()
    {
        leftPawnWaiting = new Point[4];
        leftPawnWaiting[0] = new Point(10,410);
        leftPawnWaiting[1] = new Point(110,410);
        leftPawnWaiting[2] = new Point(10,510);
        leftPawnWaiting[3] = new Point(110,510);

        rightPawnWaiting = new Point[4];
        rightPawnWaiting[0] = new Point(811,410);
        rightPawnWaiting[1] = new Point(911,410);
        rightPawnWaiting[2] = new Point(811,510);
        rightPawnWaiting[3] = new Point(911,510);

        for(int i=0;i<4;i++) leftPlayer.pawns[i].setLocation(leftPawnWaiting[i]);
        for(int i=0;i<4;i++) rightPlayer.pawns[i].setLocation(rightPawnWaiting[i]);
    }

    //////5
    public void setBoardRoadIndex()
    {
        int x, y;
        boardIndexer = new BoardIndexData[30];
        boardIndexer[0] = new BoardIndexData(0,0,0,false,0);
        boardIndexer[5] = new BoardIndexData(697,32,5,true,26);
        boardIndexer[10] = new BoardIndexData(237,35,10,true,21);
        boardIndexer[15] = new BoardIndexData(237,490,15,false,0);
        boardIndexer[20] = new BoardIndexData(699,496,20,false,0);
        x = 697; y = 400;
        for(int i=1;i<=4;i++, y-=90) boardIndexer[i] = new BoardIndexData(x, y, i,false, 0);
        x = 601; y = 32;
        for(int i=6;i<=9;i++, x-=90) boardIndexer[i] = new BoardIndexData(x, y, i,false, 0);
        x = 237; y = 129;
        for(int i=11;i<=14;i++, y+=90) boardIndexer[i] = new BoardIndexData(x, y, i,false, 0);
        x = 332; y = 493;
        for(int i=16;i<=19;i++, x+=90) boardIndexer[i] = new BoardIndexData(x, y, i,false, 0);

        //지름길 인덱스 (왼쪽위 -> 오른쪽 아래, 중간 지점 인덱스 포함)
        boardIndexer[21] = new BoardIndexData(323, 123,21,false,0);
        boardIndexer[22] = new BoardIndexData(388, 188,22,false,0);
        boardIndexer[23] = new BoardIndexData(468, 263,23,true,24);
        boardIndexer[24] = new BoardIndexData(542, 343,24,false,0);
        boardIndexer[25] = new BoardIndexData(607, 408,25,false,0);
        //지름길 인덱스 (오른쪽위 -> 왼쪽아래, 중간 지점 인덱스 제외)
        boardIndexer[26] = new BoardIndexData(607, 123,26,false,0);
        boardIndexer[27] = new BoardIndexData(542, 188,27,false,0);
        boardIndexer[28] = new BoardIndexData(388, 343,28,false,0);
        boardIndexer[29] = new BoardIndexData(323, 408,29,false,0);

        //set exceptional index
        boardIndexer[0].nextIndex = 0;
        boardIndexer[1].prevIndex = 20;
        boardIndexer[21].prevIndex = 10;
        boardIndexer[23].nextIndex = 28;
        boardIndexer[26].prevIndex = 5;
        boardIndexer[25].nextIndex = 20;
        boardIndexer[27].nextIndex = 23;
        boardIndexer[28].prevIndex = 23;
        boardIndexer[29].nextIndex = 15;
        boardIndexer[20].nextIndex = 0;
    }

    //////6
    public void setYutTextImage()
    {
        iconYutText = new ImageIcon[7];
        for(int i=0; i<7; i++)
            iconYutText[i]= new ImageIcon("images/"+i+".png");
    }


}