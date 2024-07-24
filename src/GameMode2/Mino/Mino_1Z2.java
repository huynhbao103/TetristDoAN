/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameMode2.Mino;

import java.awt.Color;
public class Mino_1Z2 extends Mino1{
    public Mino_1Z2(){
        create(Color.green);
    }
    public void setXY(int x,int y){
        //   o
        //   o o
        //     o  
        b[0].x =x;
        b[0].y =y;
        b[1].x =b[0].x;
        b[1].y =b[0].y -Block1.SIZE;
        b[2].x =b[0].x +Block1.SIZE;
        b[2].y =b[0].y ;
        b[3].x =b[0].x + Block1.SIZE;
        b[3].y =b[0].y + Block1.SIZE;
    }
    public void getDirection1(){
        //   o
        //   o o
        //     o  
        tempB[0].x =b[0].x ;
        tempB[0].y =b[0].y;
        tempB[1].x =b[0].x ;
        tempB[1].y =b[0].y - Block1.SIZE;
        tempB[2].x =b[0].x + Block1.SIZE;
        tempB[2].y =b[0].y;
        tempB[3].x =b[0].x + Block1.SIZE;
        tempB[3].y =b[0].y + Block1.SIZE;
        updateXY(1);
        
    }
    public void getDirection2(){
        //   o o
        // o o 
        //
        tempB[0].x =b[0].x ;
        tempB[0].y =b[0].y;
        tempB[1].x =b[0].x - Block1.SIZE ;
        tempB[1].y =b[0].y  ;
        tempB[2].x =b[0].x ;
        tempB[2].y =b[0].y - Block1.SIZE;;
        tempB[3].x =b[0].x + Block1.SIZE;
        tempB[3].y =b[0].y - Block1.SIZE;
        updateXY(2);
        
    }
    public void getDirection3(){
    getDirection1();
    }
    public void getDirection4(){
   getDirection2();
    }
}
