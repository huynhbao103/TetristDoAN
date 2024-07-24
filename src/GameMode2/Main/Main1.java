/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameMode2.Main;

import javax.swing.JFrame;
public class Main1 {

   
    public static void main(String[] args) {
        
        JFrame window =new JFrame ("Simple Testis");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        //Thêm GamePanel tới cửa sổ  
        GamePanel1 gp =new GamePanel1 ();
        window.add (gp);
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gp.lauchGame();
    }
    
}
