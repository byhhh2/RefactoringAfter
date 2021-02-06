package Model;

import Controller.Ability;

import javax.swing.*;
import java.awt.*;


public class Player {
    public Pawn[]      pawns;
    public int         score;
    public Ability[]   abilities;
    public boolean     isMyTurn, isNowAbility1Use, isNowAbility2Use;
    public int        pawnImgWidth, pawnImgHeight;
    public JLabel       imgPlayer, lblTurn;
    public ImageIcon[]    iconPlayer;


    public Player(String img, int width, int height){

        pawnImgWidth = width;
        pawnImgHeight = height;
        pawns = new Pawn[4];
        for(int i=0;i<4;i++){
            pawns[i] = new Pawn(img, 0,0, width, height, i);
        }
        abilities = new Ability[2];


        setAbility();

        // score, Turn유무, 능력 사용 여부 초기
        playerInit();

        iconPlayer = new ImageIcon[2];
        imgPlayer = new JLabel();

        setLabelTurn();

    }
    private void setLabelTurn() {
        lblTurn= new JLabel();
        lblTurn.setBounds(21 ,10,160,80);
        lblTurn.setFont(new Font("OCR A Extended", Font.BOLD, 30));
        lblTurn.setText("My Turn!");
        lblTurn.setVisible(false);
    }

    //  methods
    public void playerInit(){
        this.score=0;
        this.isMyTurn=false;
        this.isNowAbility1Use=false;
        this.isNowAbility2Use=false;
        for(Pawn p:this.pawns){
            p.pawnInit();
        }
    }

    public void setAbility(){
        int random;
        //1번째 능력 설정
        random = (int)(Math.random() * 3);
        System.out.println("player random1 :"+random);
        abilities[0]= Ability.makeAbility(random);
        //2번째 능력 설정
        random = (int)(Math.random() * 3);
        System.out.println("player random2 :"+(random+3));
        abilities[1]= Ability.makeAbility(random+3);
    }
}