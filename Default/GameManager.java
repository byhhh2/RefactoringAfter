package Default;

import Controller.InGameController;
import Model.InGameData;
import View.*;

public class GameManager {

    private static GameManager gameManager;
    private MainPanel mainPanel;
    private InGameView inGameView;
    private ExplainPanel explainPanel;
    private MenuPanel menuPanel;
    private InGameData inGameData;
    private InputInfo inputInfo;
    private RankPanel rankPanel;
    private InGameController inGameController;

    private GameManager(){}//constructor

    //get
    public MainPanel getMainPanel(){return mainPanel;}
    public InGameView getInGameView(){return inGameView;}
    public ExplainPanel getExplainPanel(){return explainPanel;}
    public MenuPanel getMenuPanel(){return menuPanel;}
    public InGameData getInGameData(){return inGameData;}
    public RankPanel getRankPanel() {return rankPanel; }
    public InputInfo getInputInfo() { return inputInfo;};
    public InGameController getInGameControl() { return inGameController; }

    //set
    public void setMainPanel(MainPanel view){ this.mainPanel = view;}
    public void setInGameView(InGameView inGame){this.inGameView = inGame;}
    public void setExplainPanel(ExplainPanel explain){this.explainPanel = explain;}
    public void setMenuPanel(MenuPanel menu){this.menuPanel = menu;}
    public void setInGameData(InGameData data){this.inGameData = data;}
    public void setRankPanel(RankPanel rank) {this.rankPanel = rank; }
    public void setInputInfo(InputInfo inputinfo) {this.inputInfo = inputinfo; }
    public void setInGameController(InGameController gameControl) {this.inGameController = gameControl; }

    public static GameManager getGameManager(){
        if(gameManager == null) gameManager = new GameManager();
        return gameManager;
    }
}