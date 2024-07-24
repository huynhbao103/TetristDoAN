/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameMode2.Mino;

import java.awt.Color;
import java.awt.Graphics2D;
import GameMode2.Main.GamePanel1;
import GameMode2.Main.KeyHandler1;
import GameMode2.Main.PlayManager1;

public class Mino1 {
    
    public Block1 b[] =new Block1[4];
    public Block1 tempB[] = new Block1[4];
    int autoDropCounter = 0 ;
    public int direction =1 ; /// there are 4 direction (1/2/3/4)
    boolean leftCollision ,rightCollision,bottomCollision;
    public boolean active =true;
    public boolean deactivating;
    int deactivateCounter =0 ;
    public void create (Color c){
        b[0] =new Block1(c);
        b[1] =new Block1(c);
        b[2] =new Block1(c);
        b[3] =new Block1(c);
        tempB[0]= new Block1(c);
        tempB[1]= new Block1(c);
        tempB[2]= new Block1(c);
        tempB[3]= new Block1(c);
    }
    
    public void setXY(int x ,int y){}
    public void updateXY(int direction){
        checkRotationCollision();
        
        if(leftCollision == false && rightCollision == false && bottomCollision == false ){
        this.direction =direction ;
        b[0].x =tempB[0].x;
        b[0].y =tempB[0].y;
        b[1].x =tempB[1].x;
        b[1].y =tempB[1].y;
        b[2].x =tempB[2].x;
        b[2].y =tempB[2].y;
        b[3].x =tempB[3].x;
        b[3].y =tempB[3].y;
        }
       
    }
    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}
    public void checkMovementCollision(){
    leftCollision =false;
    rightCollision =false;
    bottomCollision = false;
    // check static block collision
    checkStaticBlockCollision();
    
    // check frame collision
    // left wall
    for(int i =0;i < b.length;i++){
        if(b[i].x == PlayManager1.left_x){
            leftCollision = true ;
        }
      }
    // right wall
    for(int i =0 ;i<b.length;i++){
        if(b[i].x + Block1.SIZE == PlayManager1.right_x ){
            rightCollision = true;
        }
    }
    //Bottom wall
      for(int i =0 ;i<b.length;i++){
        if(b[i].y + Block1.SIZE == PlayManager1.bottom_y ){
            bottomCollision = true;
        }
      }
    }
    public void checkRotationCollision(){
        leftCollision =false;
    rightCollision =false;
    bottomCollision = false;
    
    // check static block collision
    checkStaticBlockCollision();
    // check frame collision
    // left wall
    for(int i =0;i < b.length;i++){
        if(tempB[i].x < PlayManager1.left_x){
            leftCollision = true ;
        }
      }
    // right wall
    for(int i =0 ;i<b.length;i++){
        if(tempB[i].x + Block1.SIZE > PlayManager1.right_x ){
            rightCollision = true;
        }
    }
    //Bottom wall
      for(int i =0 ;i<b.length;i++){
        if(tempB[i].y + Block1.SIZE > PlayManager1.bottom_y ){
            bottomCollision = true;
        }
      }    
    }
    public void checkStaticBlockCollision(){
        for (int i =0; i < PlayManager1.statBlocks.size();i++){
            int targetX =PlayManager1.statBlocks.get(i).x;
            int targetY =PlayManager1.statBlocks.get(i).y;
        // check down 
        for(int ii =0 ;ii <b.length;ii++){
            if(b[ii].y + Block1.SIZE == targetY && b[ii].x ==targetX){
                bottomCollision =true;
            }
        }
        // check left 
        for(int ii = 0;ii < b.length;ii++){
            if(b[ii].x + Block1.SIZE==targetX && b[ii].y ==targetY){
                rightCollision =true;
            }
        }
      }
    }
    public void update (){
        if( deactivating){
            deactivating();
        }
        
        //Move the mino
        if (KeyHandler1.upPressed){
            switch(direction){
                case 1: getDirection2();break;
                case 2: getDirection3();break;
                case 3: getDirection4();break;
                case 4: getDirection1();break;
            }
            KeyHandler1.upPressed = false;
            GamePanel1.se.play(3, false);
        }
        checkMovementCollision();
        
        if(KeyHandler1.downPressed){
            // if the mino's bottom is not hitting , it cango down
            if(bottomCollision == false){
            b[0].y += Block1.SIZE;
            b[1].y += Block1.SIZE;
            b[2].y += Block1.SIZE;
            b[3].y += Block1.SIZE;
            
            // when moved down , reset the autodropcounter
            autoDropCounter = 0 ;
        }
            KeyHandler1.downPressed = false;
            
        }
        if(KeyHandler1.leftPressed){
            if(leftCollision == false){
             b[0].x -= Block1.SIZE;
             b[1].x -= Block1.SIZE;
             b[2].x -= Block1.SIZE;
             b[3].x -= Block1.SIZE;
            }
             KeyHandler1.leftPressed = false;
        }
        if(KeyHandler1.rightPressed){
            if(rightCollision == false){
             b[0].x += Block1.SIZE;
             b[1].x += Block1.SIZE;
             b[2].x += Block1.SIZE;
             b[3].x += Block1.SIZE;
            }
             KeyHandler1.rightPressed = false;
        }
        
        if(bottomCollision){
            if(deactivating == false){
                GamePanel1.se.play(4, false);
            }
         deactivating= true;
        }
        else{
            autoDropCounter++; // bộ đếm tăng lên trong mỗi khung hình
        if (autoDropCounter == PlayManager1.dropInterval){
            // mino ddi xuống 
            b[0].y += Block1.SIZE;
            b[1].y += Block1.SIZE;
            b[2].y += Block1.SIZE;
            b[3].y += Block1.SIZE;
            autoDropCounter = 0 ; 
        }
       
       }
    }
    private void deactivating(){
        deactivateCounter++;
        // wait 45 frames until diactivate
        if (deactivateCounter == 45){
            deactivateCounter = 0;
            checkMovementCollision();// check if the bottom is still hitting
            
            //if the bottom is still hitting after 45 frames , deactivate the mino
            if(bottomCollision){
                active =false;
            }
        }
    }
    public void draw (Graphics2D g2){
        int margin = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x+margin, b[0].y+margin,Block1.SIZE-(margin*2), Block1.SIZE-(margin*2));
        g2.fillRect(b[1].x+margin, b[1].y+margin,Block1.SIZE-(margin*2), Block1.SIZE-(margin*2));
        g2.fillRect(b[2].x+margin, b[2].y+margin,Block1.SIZE-(margin*2), Block1.SIZE-(margin*2));
        g2.fillRect(b[3].x+margin, b[3].y+margin,Block1.SIZE-(margin*2), Block1.SIZE-(margin*2));
        
        
    }
}
