package Controller;

import Default.GameManager;
import Model.InGameData;
import Model.Player;

public abstract class Ability {

    private boolean isUsed;
    public InGameData inGameData = GameManager.getGameManager().getInGameData();
    public String abilityName;
    public int yutResult1, yutResult2;
    public Ability(){
        isUsed = false;
    }//constructor
    public void run(Player user)
    {
        if(user != inGameData.activatedPlayer) return;
        if(!isUsed) {
            use();
        }
    }

    public static Ability makeAbility(int random) {
        String[] abilityNames= {"Mo/Do","Gae/Girl","OnlyYut","GoHome","Shuffle","Swap"};

        Ability mySelf;
        if(random == 0) {
            System.out.println("modo");
            mySelf= new MovementAbility();
            mySelf.abilityName=abilityNames[0];
            mySelf.yutResult1 =1;
            mySelf.yutResult2 =5;
            return mySelf;
        }
        if(random == 1) {
            System.out.println("gae");
            mySelf= new MovementAbility();
            mySelf.abilityName=abilityNames[1];
            mySelf.yutResult1 =2;
            mySelf.yutResult2 =3;

            return mySelf;
        }
        if(random == 2){
            System.out.println("myut");
            mySelf= new MovementAbility();
            mySelf.abilityName=abilityNames[2];
            mySelf.yutResult1 =4;
            mySelf.yutResult2 =4;
            return mySelf;
        }
        if(random == 3) {
            mySelf = new LocationAbility.GoHome();
            mySelf.abilityName=abilityNames[3];
            return mySelf;
        }
        if(random == 4) {
            mySelf = new LocationAbility.Shuffle();
            mySelf.abilityName=abilityNames[4];
            return mySelf;
        }
        if(random == 5) {
            mySelf = new LocationAbility.Swap();
            mySelf.abilityName=abilityNames[5];
            return mySelf;
        }
        else { return null; }
    }
    public abstract void use();
    public boolean isUsed(){ return isUsed; }


}