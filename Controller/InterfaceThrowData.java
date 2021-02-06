package Controller;

import Default.GameManager;
import Model.InGameData;
import Model.ThrowData;

interface LoopThrowData {
    InGameData inGameData = GameManager.getGameManager().getInGameData();
    public void inLoop(ThrowData data );
}

class IsBackDo implements LoopThrowData {
    @Override
    public void inLoop(ThrowData data ){
        if(data.result != 6) inGameData.flag = false;
    }
}

class PreviewVisibleFalse implements LoopThrowData {
    @Override
    public void inLoop(ThrowData data  ){
        data.preview.setVisible(false);
    }
}


class ShowAllPreviews implements LoopThrowData {
    @Override
    public void inLoop(ThrowData data) {
        data.preview.setVisible(true);
        if(data.result == 6 && inGameData.focusedPawn.getCurrentIndex() == 0) data.preview.setVisible(false);    //말이 이동할 곳이 없는 경우는 보이지 않도록 설정
        else InGameController.findNextPoint(inGameData,data);
    }
}

class RemoveClickPreview implements LoopThrowData {
    @Override
    public void inLoop(ThrowData data) {
        if(data.preview == inGameData.focusedPreview)
            inGameData.clicked = data;
    }
}

class LoopOfThrowData {
    InGameData inGameData = GameManager.getGameManager().getInGameData();
    public void loopThrowDatas(LoopThrowData interfaceData){
        for(ThrowData data : inGameData.previewPawns){
            interfaceData.inLoop(data);
        }
    }
}

/*
      for(ThrowData d : _inGameData.previewPawns) {    //남은 결과값들 중 빽도가 아닌 것이 있는지 확인
          if(d.result != 6) flag = false;

     for(ThrowData data: _inGameData.previewPawns){ //선택된 말에 대해 이동 가능한 위치를 보여주기 위해 저장된 윷 결과 데이터에 있는 미리보기 말 초기화
            data.preview.setVisible(false); //미리보기 말들 모두 안보이게 하기
        }


      for(ThrowData d: _inGameData.previewPawns) //결과 데이터에서 이벤트가 발생한 미리보기 말 찾기
          if(d.preview == p) clicked = data;

  for(ThrowData data: _inGameData.previewPawns) {
            data.preview.setVisible(true);
            if(data.result == 6 && _inGameData.getFocusedPawn().getCurrentIndex() == 0) data.preview.setVisible(false);    //말이 이동할 곳이 없는 경우는 보이지 않도록 설정
            else findNextPoint(_inGameData,data); //말이 결과 데이터에 따라 이동하게 될 위치 찾기
        }


*/