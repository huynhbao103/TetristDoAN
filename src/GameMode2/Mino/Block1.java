/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameMode2.Mino;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block1 extends Rectangle{
    public int x,y;
    public static final int SIZE =30; // 30x30 block
    public Color c;
    
    public Block1 (Color c){
        this.c =c;
    }
    public void draw(Graphics2D g2){
        int margine =2;
        g2.setColor(c);
        g2.fillRect(x + margine, y+margine, SIZE-(margine*2), SIZE-(margine*2));
    }
            
}

