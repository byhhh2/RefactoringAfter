package Controller;

import Default.GameManager;
import Model.*;

interface LoopPawn {
    InGameData inGameData = GameManager.getGameManager().getInGameData();
    public void inLoop(Pawn p,Player player);
}
class DeactivateAdapter implements LoopPawn {
    @Override
    public void inLoop(Pawn p,Player player) {
        InGameController.PawnClickMouseAdapter adapter = player == inGameData.leftPlayer ? InGameController.leftPawnAdapter : InGameController.rightPawnAdapter;
        p.removeMouseListener(adapter);
    }
}
class ActivateAdapter implements LoopPawn {
    @Override
    public void inLoop(Pawn p,Player player) {
        InGameController.PawnClickMouseAdapter adapter = player == inGameData.leftPlayer ? InGameController.leftPawnAdapter : InGameController.rightPawnAdapter;
        if(p.getIsFinished()==false)
            p.addMouseListener(adapter);
    }
}
class CheckMovePossible implements LoopPawn {
    @Override
    public void inLoop(Pawn p,Player player) {
        if(p.getCurrentIndex()!=0) inGameData.flag=false;
    }
}
class InitPawns implements LoopPawn {
    @Override
    public void inLoop(Pawn p, Player player) {
        InGameController.goWaitingRoom(p,player);
        p.setPawnImg(player==InGameData.leftPlayer? "images/Left_0.png":"images/Right_0.png");
    }
}
class CatchOpponentPawns implements LoopPawn {
    @Override
    public void inLoop(Pawn p, Player player) {
        if(p.getCurrentIndex() == inGameData.pawnArrive){
            //System.out.println(_inGameData.pawnArrive+"+"+_inGameData.focusedPreview.getCurrentIndex());
            inGameData.catchOppenent = true;
            p.setCurrentIndex(0);
            InGameController.goWaitingRoom(p, player);
        }//if
    }
}
class MoveAllPawns implements LoopPawn {
    @Override
    public void inLoop(Pawn p, Player player) {
        if(p.getCurrentIndex() == inGameData.pawnNow) {//start칸에 있는 말들에 한해서 이동하기 위한 if문
            InGameController.move(player,p, inGameData.pawnArrive);
        }
    }
}
class LoopOfPawn {
    public void loopPawns(LoopPawn l, Player player){
        for(Pawn p:player.pawns) {
            l.inLoop(p,player);
        }
    }
}


