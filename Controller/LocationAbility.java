package Controller;

import Model.*;
import Default.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class LocationAbility {

    public static class GoHome extends Ability{ //상대 말 하나 집으로 보내기

        Player opponent;
        SelectOpponent pawnAdapter;

        @Override
        public void use() {
            opponent = inGameData.activatedPlayer == inGameData.leftPlayer ? inGameData.rightPlayer : inGameData.leftPlayer;//지금 실행 중인 player가 왼쪽이면 opponent는 오른쪽
            pawnAdapter = new SelectOpponent();
            for(Pawn p:opponent.pawns) if(p.getCurrentIndex()!=0) p.addMouseListener(pawnAdapter); //리스너를 추가해준다.
        }
        private class SelectOpponent extends MouseAdapter{
            public InGameController inGameController = GameManager.getGameManager().getInGameControl();
            @Override
            public void mouseReleased(MouseEvent e) { //눌러진 마우스 버튼이 떼어지는 순간이다.
                Pawn obj = (Pawn)e.getSource();
                for(Pawn p: opponent.pawns) if (obj.getCurrentIndex() == p.getCurrentIndex()) inGameController.goWaitingRoom(p, opponent);  //말을 찾아, 상대편 말을 대기칸으로 보낸다.
                for(Pawn p:opponent.pawns) if(p.getCurrentIndex()!=0) p.removeMouseListener(pawnAdapter); //리스너를 없앤다.
            }
        }

    }//GoHome class

    public static class Shuffle extends Ability {//밥상뒤집기
        public int[] destination = new int [8]; //랜덤인 목적지 index를 저장

        int currentCount; //자리를 바꿀 모든 말의 수
        int randomIndex;

        ArrayList<Integer> leftCurrentAllPawnIndex; //왼쪽 player의 현재 모든 말들의 위치 index
        ArrayList<Integer> rightCurrentAllPawnIndex; //오른쪽 player의 현재 모든 말들의 위치 index

        @Override
        public void use() {
            leftCurrentAllPawnIndex=new ArrayList<Integer>();
            rightCurrentAllPawnIndex=new ArrayList<Integer>();
            currentCount = 0;
            shuffle();
        }

        public void shuffle(){
            InGameController inGameController= GameManager.getGameManager().getInGameControl();
            InGameData inGameData =GameManager.getGameManager().getInGameData();
            int i = 0; // RandomIdx 랜덤 인덱스 뽑는 변수

            getCurrentPawnIndex(this.inGameData.leftPlayer,leftCurrentAllPawnIndex);
            getCurrentPawnIndex(this.inGameData.rightPlayer,rightCurrentAllPawnIndex);
            createRandomPawnIndex();

            Iterator<Integer> it=leftCurrentAllPawnIndex.iterator();   //leftPlayer moveAllPawns해주기
            i=0;      //Destination의 인덱스 참조하는 변수
            while(it.hasNext()){
                inGameData.pawnNow= it.next();
                inGameData.pawnArrive=destination[i++];
                inGameController.movePawn(this.inGameData.leftPlayer,null);
            }
            it=rightCurrentAllPawnIndex.iterator();   //rightPlayer moveAllPawns해주기
            while(it.hasNext()) {
                inGameData.pawnNow= it.next();
                inGameData.pawnArrive=destination[i++];
                inGameController.movePawn(this.inGameData.rightPlayer,null);
            }

        }

        private void createRandomPawnIndex() { //중복없는 랜덤 값을 destination에 넣어주는 함수
            int i;
            HashSet<Integer> hashset=new HashSet<Integer>();   //RandomIdx중복값을 없애기 위한 HashSet
            for(i = 0 ; i < currentCount; i++) {// 왼쪽 플레이어 말의 위치를 뽑기 위한 반복문
                while (true) { // 중복된 수를 뽑지 않기 위한 무한 반복문
                    randomIndex = (int) (Math.random() * 29) + 1;
                    if(hashset.add(randomIndex)) {   //HashSet에 중복없이 add가 된다면
                        destination[i]=randomIndex;//Destination에 넣어주기
                        break;
                    }

                }
            }
        }

        private void getCurrentPawnIndex(Player player, ArrayList<Integer> currentAllPawnIndex) {//바꾸기전 원래의 말이 있던 index를 currentIdx배열에 저장하는 함수
            int flag = 0;

            for(Pawn p: player.pawns) {
                if(p.getIsFinished() == false && p.getCurrentIndex() != 0 ) { //만약 판에 있으면
                    Iterator<Integer> it = currentAllPawnIndex.iterator();
                    while(it.hasNext()) {
                        if(it.next()==p.getCurrentIndex()) //업힌 말이라면 자리를 바꿀 총 말의 수를 증가시켜 주지 않는다.
                            flag=1;
                    }
                    if(flag==0) {
                        currentCount++;
                        currentAllPawnIndex.add(p.getCurrentIndex()*-1); //만약에 왼쪽 사용자가 갈 위치가 오른쪽 사용자가 현재 있는 위치라면 왼쪽 사용자의 말이 오른쪽 사용자의 말을 잡는 오류가 생기므로 인덱스를 옮겨준다.
                        p.setCurrentIndex(p.getCurrentIndex()*-1);
                    }
                }
            }
        }//getCurrentPawnIdx()
    }//UpSideDown class

    public static class Swap extends Ability { //상대 말과 자리바꾸기
        Player activatedPlayer;
        Player opponentPlayer; //상대 플레이어 변수
        SelectOpponent opponentPawnAdapter;
        SelectActive activatedPawnAdapter;
        private int activeIndex, opponentIndex; //activeIdx = 지금 차례인 플레이어의 선택한 말을 저장한 변수, opponenIdx = 반대편

        @Override
        public void use() {
            activatedPlayer  = inGameData.activatedPlayer;
            opponentPlayer = inGameData.activatedPlayer == inGameData.leftPlayer ? inGameData.rightPlayer : inGameData.leftPlayer;//지금 실행 중인 player가 왼쪽이면 opponent는 오른쪽

            opponentPawnAdapter = new SelectOpponent();
            activatedPawnAdapter = new SelectActive();

            for(Pawn p:activatedPlayer.pawns) if(p.getCurrentIndex()!=0) p.addMouseListener(activatedPawnAdapter);
        }
        private class SelectOpponent extends MouseAdapter{
            @Override
            public void mouseReleased(MouseEvent e) {
                Pawn clikedPawn = (Pawn)e.getSource();
                findClikedPawnAndGetIndex(clikedPawn);
                allPawnsRemoveMouseListener();
                swap();
            }
            private void findClikedPawnAndGetIndex(Pawn clikedPawn) {
                System.out.println(clikedPawn.getPawnNumber() + "번 상대 말 선택");
                for(Pawn p: opponentPlayer.pawns) if (clikedPawn.getCurrentIndex() == p.getCurrentIndex()) opponentIndex = p.getCurrentIndex();//인덱스를 가져오기
            }
            private void allPawnsRemoveMouseListener() {
                for(Pawn p:opponentPlayer.pawns) if(p.getCurrentIndex()!=0) p.removeMouseListener(opponentPawnAdapter);
            }
            private void swap() {
                for(int i = 0; i < 4; i++) {
                    if(activatedPlayer.pawns[i].getCurrentIndex() == activeIndex) {
                        activatedPlayer.pawns[i].setCurrentIndex(opponentIndex);
                        activatedPlayer.pawns[i].setLocation(inGameData.boardIndexer[opponentIndex].p);
                    }
                    if(opponentPlayer.pawns[i].getCurrentIndex() == opponentIndex) {
                        opponentPlayer.pawns[i].setCurrentIndex(activeIndex);
                        opponentPlayer.pawns[i].setLocation(inGameData.boardIndexer[activeIndex].p);
                    }
                }
            }

        }
        private class SelectActive extends MouseAdapter{
            @Override
            public void mouseReleased(MouseEvent e) {// 능력을 누른 사람의 폰 선택
                Pawn clikedPawn = (Pawn)e.getSource();
                findClikedPawnAndGetIndex(clikedPawn);
                allPawnsRemoveMouseListener();
                addOpponentMouseListener();
            }
            private void addOpponentMouseListener() {
                for(Pawn p: opponentPlayer.pawns) if(p.getCurrentIndex() != 0) p.addMouseListener(opponentPawnAdapter);//상대방 폰의 리스너 추가
            }
            private void findClikedPawnAndGetIndex(Pawn clikedPawn) {
                System.out.println(clikedPawn.getPawnNumber() + "번 폰 선택됨");
                for(Pawn p: activatedPlayer.pawns) if (clikedPawn.getCurrentIndex() == p.getCurrentIndex()) activeIndex = p.getCurrentIndex(); // 선택한 폰의 인덱스를 가져오기
            }
            private void allPawnsRemoveMouseListener() {
                for(Pawn p:activatedPlayer.pawns) if(p.getCurrentIndex()!=0) p.removeMouseListener(activatedPawnAdapter);//더이상 삭제 되지 못하도록 리스너 삭제
            }
        }
    }




}