package Controller;

import Model.BoardIndexData;
import Model.InGameData;
import Model.Pawn;
import Model.ThrowData;

import javax.swing.*;

public class FindNextPoint {
    private InGameData inGameData;
    private ThrowData throwData;
    private Pawn previewPawn, focusedPawn;
    private int yutResult;
    private int indexOfFocusedPawn;
    private int[][] lookupTable ={
            /*0번*/        {1,2,3,4,5,0},//여기 빽도 특수 처리
            /*1번*/        {2,3,4,5,6,20},
            /*2번*/        {3,4,5,6,7,1},
            /*3번*/        {4,5,6,7,8,2},
            /*4번*/        {5,6,7,8,9,3},
            /*5번*/        {26,27,23,28,29,4},
            /*6번*/        {7,8,9,10,11,5},
            /*7번*/        {8,9,10,11,12,6},
            /*8번*/        {9,10,11,12,13,7},
            /*9번*/        {10,11,12,13,14,8},
            /*10번*/        {21,22,23,24,25,9},
            /*11번*/        {12,13,14,15,16,10},
            /*12번*/        {13,14,15,16,17,11},
            /*13번*/        {14,15,16,17,18,12},
            /*14번*/        {15,16,17,18,19,13},
            /*15번*/        {16,17,18,19,20,14},
            /*16번*/        {17,18,19,20,0,15},
            /*17번*/        {18,19,20,0,0,16},
            /*18번*/        {19,20,0,0,0,17},
            /*19번*/        {20,0,0,0,0,18},
            /*20번*/        {0,0,0,0,0,19},
            /*21번*/        {22,23,24,25,20,10},
            /*22번*/        {23,24,25,20,0,21},
            /*23번*/        {24,25,20,0,0,22},
            /*24번*/        {25,20,0,0,0,23},
            /*25번*/        {20,0,0,0,0,24},
            /*26번*/        {27,23,28,29,15,5},
            /*27번*/        {23,28,29,15,16,26},
            /*28번*/        {29,15,16,17,18,23},
            /*29번*/        {15,16,17,18,19,28}
    };
    BoardIndexData[] boardIndexer;

    public FindNextPoint(InGameData inGameDataArg, ThrowData throwDataArg){
        inGameData = inGameDataArg;
        throwData =throwDataArg;
        boardIndexer= inGameData.boardIndexer;
        previewPawn = throwData.preview;
        focusedPawn = inGameData.focusedPawn;
        yutResult= throwData.result;
        indexOfFocusedPawn = focusedPawn.getCurrentIndex();
    }

    void setNextPoint(){
        //**//lookup Table 값에 대한 것을 배열에 넣기

        previewPawn.setIcon(new ImageIcon(focusedPawn.getPawnImg()));

        //스위치 문 우선 if문으로 변경해서 최대한 중복코드 삭제함
        if(indexOfFocusedPawn ==0 && yutResult==6)
            previewPawn.setVisible(false);
        else previewPawn.setCurrentIndex(lookupTable[indexOfFocusedPawn][yutResult-1]);


        if(previewPawn.getCurrentIndex() == 0) {   //예상 도착지점이 골인지점을 통과한 경우 골인지점에서 이미지를 바꿔서 띄워주기
            previewPawn.setIcon(new ImageIcon("images/FinishedPawn.png"));
            previewPawn.setLocation(boardIndexer[20].p);
        }
        else
            previewPawn.setLocation(boardIndexer[previewPawn.getCurrentIndex()].p);   //위 과정에서 계산된 인덱스의 좌표로 미리보기용 말 이동

    }

}