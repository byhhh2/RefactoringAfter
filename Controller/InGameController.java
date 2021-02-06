package Controller;

import Model.*;
import View.*;
import Default.*;

import javax.swing.*;
import java.awt.event.*;


public class InGameController {

    private InGameView inGameView;
    private static InGameData inGameData;
    private InputInfo inputInfo;
    public static PawnClickMouseAdapter leftPawnAdapter;
    public static PawnClickMouseAdapter rightPawnAdapter;
    public LoopOfPawn loopPawn;
    public LoopOfThrowData loopData;
    private InputInfoController inputInfoController;

    public InGameController() {

        loopData = new LoopOfThrowData();
        loopPawn = new LoopOfPawn();

        inGameView = GameManager.getGameManager().getInGameView();         // View.InGameView 받아오기
        inGameData = GameManager.getGameManager().getInGameData();         //   데이터 받아오기
        GameManager.getGameManager().setInGameController(this);

        //각 플레이어의 폰 선택 리스터 생성
        leftPawnAdapter = new PawnClickMouseAdapter();
        rightPawnAdapter = new PawnClickMouseAdapter();


        //던지기 버튼에 리스너 넣기
        inGameView.btnThrowLeft.addActionListener(new ThrowingYut());
        inGameView.btnThrowRight.addActionListener(new ThrowingYut());


        //능력 버튼에 리스너 넣기
        inGameView.leftUserPanel.btnAbility1.addActionListener(new UseAbility(inGameData.leftPlayer,0));
        inGameView.leftUserPanel.btnAbility2.addActionListener(new UseAbility(inGameData.leftPlayer,1));

        inGameView.rightUserPanel.btnAbility1.addActionListener(new UseAbility(inGameData.rightPlayer,0));
        inGameView.rightUserPanel.btnAbility2.addActionListener(new UseAbility(inGameData.rightPlayer,1));

        //게임 초기화하며 시작
        initGamePlayer(inGameData.leftPlayer, leftPawnAdapter, inGameView.btnThrowLeft);
        initGamePlayer(inGameData.rightPlayer, rightPawnAdapter, inGameView.btnThrowRight);
        changePlayerImgnLabel();
        activeThrowBtn(inGameData.activatedPlayer);


    }

    //이동할 말을 선택하는 과정에서 플레이어의 말 클릭 시 이벤트 발생
    public class PawnClickMouseAdapter extends MouseAdapter {
        public void mouseReleased(MouseEvent e) {

            //set으로 focusedpawn 설정하기
            inGameData.focusedPawn=(Pawn)e.getSource();     //이벤트가 발생한 말을 선택된 말로 설정

            loopData.loopThrowDatas(new PreviewVisibleFalse());
            loopData.loopThrowDatas(new ShowAllPreviews());
            inGameView.repaint();
        }
    }


    public class MoveSelectedPawnMouseAdapter extends MouseAdapter {   //플레이어의 말을 클릭했을 때 보여진 미리보기 말을 클락하면 발생하는 이벤트 리스너
        public void mouseReleased(MouseEvent e) {
            inGameData.focusedPreview = (Pawn)e.getSource();

            //focusedpawn 임시변수 만들고 get으로 가져오기
            Pawn focused = inGameData.focusedPawn;

            boolean catched;

            //말 이동
            inGameData.pawnNow=focused.getCurrentIndex();
            inGameData.pawnArrive= inGameData.focusedPreview.getCurrentIndex();
            catched = movePawn(inGameData.activatedPlayer,focused.getCurrentIndex()==0? focused:null);

            loopData.loopThrowDatas(new PreviewVisibleFalse());

            //////2 메서드 추출
            removeClickedPreview(inGameData.focusedPreview);
            inGameData.previewPawns.trimToSize();    //리스트 사이즈 갱신
            System.out.println("catched =" + catched);

            if( checkAllPawnFinished()==1 ) return;
            //게임이 계속 진행되는 경우
            if(catched) {   //말 이동 후 상대 말을 잡으면 윷을 던질 기회 획득
                activeThrowBtn(inGameData.activatedPlayer);   //윷 던질 준비 시키기
                //////5
//                deactivationPawnClick();           //윷을 던지는 동안에는 말 이동시키지 못하도록 리스너 제거
                loopPawn.loopPawns(new DeactivateAdapter(), inGameData.activatedPlayer);
            }
            else if(inGameData.previewPawns.size()==0) { //추가 턴을 획득하지 못하고 던진 윷 결과데이터들을 모두 사용한 경우
                //////5
//                deactivationPawnClick();                //말 선택 리스너 모두 제거
                loopPawn.loopPawns(new DeactivateAdapter(), inGameData.activatedPlayer);
                passPlayerTurn();   //상대에게 턴 넘겨주기
            }
            else {  //아직 이동할 결과데이터가 남아있는 경우
                isBackDoNPawnInWaitingRoom();
            }//else
        }//mouseReleased


        //////2 메서드 추출
        void removeClickedPreview(Pawn p){
            loopData.loopThrowDatas(new RemoveClickPreview());
            inGameData.previewPawns.remove(inGameData.clicked); //결과 데이터의 리스트에서 방금 사용된 결과 데이터를 삭제
        }

        int checkAllPawnFinished( ){
            if(inGameData.activatedPlayer.score ==4){    //말 4개가 모두 완주
                String str;                //승자에 따라 메시지 설정
                if(inGameData.activatedPlayer == inGameData.leftPlayer)
                    str="Left Player Win!! \n Do you want new Game?" ;
                else str= "Right Player Win!! \n Do you want new Game?";

                int result = JOptionPane.showConfirmDialog(inGameView, str,"Game End",JOptionPane.YES_NO_OPTION);   //게임을 계속 진행할 지  묻기

                //frame에 상관없이 두 케이스 모두 실행
                makeInputinfoFrame();

                switch(result) {
                    case JOptionPane.NO_OPTION:
                        inputInfo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        break;

                    case JOptionPane.YES_OPTION:

//                        deactivationPawnClick();
                        loopPawn.loopPawns(new DeactivateAdapter(), inGameData.activatedPlayer);

                        //다음판 가능하도록 활성화
                        if( inGameData.activatedPlayer == inGameData.rightPlayer) passPlayerTurn();
                        activeThrowBtn(inGameData.leftPlayer);
                        inGameDataInit();
                        inGameData.previewPawns.clear();
                        inGameData.previewPawns.trimToSize();

                        inGameView.lblThrowing.setIcon(inGameView.lblThrowing.iconYut[0]);
                        inGameView.lblYutResult.setIcon(inGameData.iconYutText[6]);

                        GameManager.getGameManager().getMainPanel().showMenu();
                        return 1;
                } // switch
            }
            return 0;
        }//checkAllPawnFinished

    }

    public void makeInputinfoFrame(){
        inputInfo =GameManager.getGameManager().getInputInfo();
        inputInfoController = new InputInfoController();
        inputInfo.setTitle("Input ID");
        inputInfo.setLocation(0,0);
        inputInfo.setSize(1000,800);
        inputInfo.setVisible(true);
        inputInfo.setResizable(true);
    }

    private class ThrowingYut implements ActionListener {   //윷 던지기 버튼을 누르면 발생하는 이벤트 리스너
        @Override
        public void actionPerformed(ActionEvent e) {
            inGameData.flag = true;

            JButton btn = (JButton) e.getSource();
            //본인 턴이 아닐 때 버튼이 눌리면 무시
            if ((inGameData.leftPlayer.isMyTurn && btn == inGameView.btnThrowRight) || (inGameData.rightPlayer.isMyTurn && btn == inGameView.btnThrowLeft))
                return;

            //상태
            if(inGameData.activatedPlayer.isNowAbility1Use==true)    //윷 결과 조작 능력 사용
                inGameData.activatedPlayer.isNowAbility1Use=false;
                //상태
            else{   //윷 결과 랜덤으로 뽑기
                inGameData.throwResult = randomYutResult();
            }
            inGameView.lblThrowing.start();  //윷 던지는 모습 보여주기
            btn.setEnabled(false);

            inGameView.lblThrowing.setResult(inGameData.throwResult); //Yut으로 결과값보내서 결과이미지 띄우기

            if (inGameData.throwResult != 4 && inGameData.throwResult != 5)   //윷이나 모가 나온 경우
                inGameData.throwableNCnt--;  //던질 수 있는 횟수 -1


            //////7
            saveThrowResult();//던진 결과 저장 - 미리보기용

            //윷 던질 기회 모두 사용한 경우
            if (inGameData.throwableNCnt == 0) {
                if(!isBackDoNPawnInWaitingRoom()){   //이동할 말이 있다면 말 이동을 위한 준비
                    loopPawn.loopPawns(new ActivateAdapter(), inGameData.activatedPlayer);//내 말 중 완주하지 않은 말에 말 선택 리스너를 add
                }
            }//if(다 던짐)
            else {  //던질 기회가 남았다면 다시 던질 준비
                activeThrowBtn(inGameData.activatedPlayer);
                loopPawn.loopPawns(new DeactivateAdapter(), inGameData.activatedPlayer);//윷을 던지는 동안에는 말 이동시키지 못하도록 리스너 제거
            }  //던질 기회가 남았다면 다시 던질 준비

        }//actionPerformed()


        //////7
        public void saveThrowResult()//던진 결과 저장 - 미리보기용
        {
            ThrowData data = new ThrowData(inGameData.throwResult);
//            data.preview.addMouseListener(new MoveSelectedPawn());
            data.preview.addMouseListener(new MoveSelectedPawnMouseAdapter());
            inGameData.previewPawns.add(data);
            inGameView.add(data.preview);
            inGameView.setComponentZOrder(data.preview, 0);  //윷판보다 위에 보이도록 설정

        }
    }

    public boolean isBackDoNPawnInWaitingRoom(){
        inGameData.flag = true;
        loopData.loopThrowDatas(new IsBackDo());
        //결과들이 모두 빽도인 경우 말을 이동할 준비
        loopPawn.loopPawns(new CheckMovePossible(), inGameData.activatedPlayer);
        if(inGameData.flag){   //윷판에 올라온 말이 없는 경우(빽도 이동이 가능한 말이 없는 경우)
            //상대 턴으로 넘어가기

            //////5
            loopPawn.loopPawns(new DeactivateAdapter(), inGameData.activatedPlayer);
            //deactivationPawnClick();

            passPlayerTurn();
            inGameData.previewPawns.clear();
            inGameData.previewPawns.trimToSize();
        }
        return inGameData.flag;
    }

    //////9
    //능력 버튼 클릭 시 발생하는 이벤트 리스너
    private class UseAbility implements ActionListener{

        Player player;
        int abilitynum;

        public UseAbility(Player p, int num) {
            player=p;
            abilitynum=num;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            player.abilities[abilitynum].run(player);
            inGameView.repaint();
        }
    }

    public void initGamePlayer(Player player, PawnClickMouseAdapter pawnListener, JButton btnThrow){
        loopPawn.loopPawns(new DeactivateAdapter(),player);
        btnThrow.setEnabled(false);
    }

    //////a
    public void changePlayerImgnLabel(){   //진행중인 차례에 맞게 플레이어 이미지 바꾸는 메소드

        Player activedPlayer = inGameData.activatedPlayer == inGameData.leftPlayer ? inGameData.leftPlayer: inGameData.rightPlayer ;
        Player deactivedPlayer = inGameData.activatedPlayer == inGameData.leftPlayer ? inGameData.rightPlayer: inGameData.leftPlayer;

        activedPlayer.imgPlayer.setIcon(activedPlayer.iconPlayer[0]);
        activedPlayer.imgPlayer.setBounds(-25,90,250,230);
        deactivedPlayer.imgPlayer.setIcon(deactivedPlayer.iconPlayer[1]);
        deactivedPlayer.imgPlayer.setBounds(-1,90,250,230);

        activedPlayer.lblTurn.setVisible(true);
        deactivedPlayer.lblTurn.setVisible(false);

    }



    //////b
    public void activeThrowBtn(Player player){   //던지기 버튼을 누를 수 있도록 활성화 하는 메소드
        JButton p = player== inGameData.leftPlayer? inGameView.btnThrowLeft : inGameView.btnThrowRight;
        p.setEnabled(true);
        inGameData.throwableNCnt = 1;
    }


    public void passPlayerTurn(){   //현재 진행중인 턴을 상대 차례로 넘기는 메소드

        inGameData.activatedPlayer.isMyTurn = false;
        inGameData.activatedPlayer = inGameData.activatedPlayer == inGameData.leftPlayer ? inGameData.rightPlayer : inGameData.leftPlayer;
        inGameData.activatedPlayer.isMyTurn = true;

        inGameView.lblYutResult.setIcon(inGameData.iconYutText[6]);   //윷 결과 지우기
        changePlayerImgnLabel();   //플레이어 이미지와 라벨 변경
        activeThrowBtn(inGameData.activatedPlayer);   //던지기 버튼 활성화
    }


    public int randomYutResult()
    {
        int YutResult = (int) Math.floor( Math.random()*(6)+1 );
        return YutResult;
    }


    ///말 이동 관련 메소드///
    public void inGameDataInit(){

        initPlayer(inGameData.leftPlayer,"images/Left_0.png");
        initPlayer(inGameData.rightPlayer,"images/Right_0.png");

        inGameData.activatedPlayer= inGameData.leftPlayer;

    }
    public void initPlayer(Player player, String pawnImg){
        player.playerInit();
        loopPawn.loopPawns(new InitPawns(),player);
    }

    public static void goWaitingRoom(Pawn pawn, Player owner) {    //말을 대기칸 좌표로 이동시키는 메소드
        pawn.setLocation(owner == inGameData.leftPlayer ? inGameData.leftPawnWaiting[pawn.getPawnNumber()] : inGameData.rightPawnWaiting[pawn.getPawnNumber()]);
    }

    public static void findNextPoint(InGameData _data, ThrowData throwData){  //파라미터로 받은 결과데이터를 이용해 선택된 말이 가게 될 위치를 찾는 메소드
        new FindNextPoint(_data,throwData).setNextPoint();
    }

    public boolean movePawn(Player owner,Pawn p){
        Player opponent = owner == inGameData.leftPlayer ? inGameData.rightPlayer : inGameData.leftPlayer;
        if(p==null){//moveallpawns의 상황
            loopPawn.loopPawns(new MoveAllPawns(),owner);
        }
        else//대기칸 옮길때
            move(owner,p, inGameData.pawnArrive);

        inGameData.catchOppenent = false;//아직 잡은지 안잡은지 확인 전이므로 false로 초기화
        if(inGameData.boardIndexer[inGameData.pawnArrive].currentIndex != 0)
            loopPawn.loopPawns(new CatchOpponentPawns(),opponent); //이동한 자리에 있는 상대말 잡기

        return inGameData.catchOppenent;
    }

    public static void move(Player owner, Pawn p, int end){
        int index = inGameData.boardIndexer[end].currentIndex;
        p.setCurrentIndex(index);//칸 인덱스 갱신
        if (index == 0) {  //골인지점을 지나는 경우
            goWaitingRoom(p, owner);    //대기칸으로 말을 보낸다
            p.setIsFinished(true);    //말의 완주 여부 설정
            owner.score++;
            p.setIcon(new ImageIcon("images/FinishedPawn.png"));
        }//완주시 대기실로 이동
        else p.setLocation(inGameData.boardIndexer[index].p); //인덱스에 맞춰서 좌표 이동
    }

//    public void catchOpponentPawns(Player opponent, BoardIndexData index) {  //특정 위치에 있는 상대 말을 집으로 보내고 성공 여부를 반환하는 메소드
//        loopPawn.loopPawns(new catchOpponentPawns(),opponent);
//    }//catchOpponentPawns()



}
