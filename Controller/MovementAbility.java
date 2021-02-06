package Controller;

public class MovementAbility extends Ability {

    public  int randomResult( ){
        int randomNum = (int)(Math.random()*2);
        return randomNum == 0 ? this.yutResult1 : this.yutResult2;
    }


    @Override
    public void use() {
        inGameData.activatedPlayer.isNowAbility1Use=true;
        inGameData.throwResult = randomResult( );
        System.out.println(inGameData.throwResult );

    }

}//MovementAbility