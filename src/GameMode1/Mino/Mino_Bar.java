package GameMode1.Mino;

import GameMode1.Mino.Mino;
import java.awt.Color;

public class Mino_Bar extends Mino {
	public Mino_Bar() {
		create(Color.cyan);
	}
	
	public void setXY (int x, int y) {
		
		//// o o o o
		
		b[0].x = x;
		b[0].y = y;
		b[1].x = b[0].x -Block.SIZE;
		b[1].y = b[0].y;
		b[2].x = b[0].x + Block.SIZE;
		b[2].y = b[0].y ;
		b[3].x = b[0].x + Block.SIZE *2;
		b[3].y = b[0].y ;
		
	}
public void getDirection1() {
		
	    //	   o
		//     o
		//     o 
		TempB[0].x = b[0].x;
		TempB[0].y = b[0].y;
		TempB[1].x = b[0].x - Block.SIZE ;
		TempB[1].y = b[0].y;
		TempB[2].x = b[0].x + Block.SIZE;
		TempB[2].y = b[0].y ;
		TempB[3].x = b[0].x + Block.SIZE*2;
		TempB[3].y = b[0].y;
		updateXY(1);
	}
	public void getDirection2() {
		// 
		 TempB[0].x = b[0].x;
		    TempB[0].y = b[0].y;
		    TempB[1].x = b[0].x;
		    TempB[1].y = b[0].y - Block.SIZE;
		    TempB[2].x = b[0].x;
		    TempB[2].y = b[0].y + Block.SIZE;
		    TempB[3].x = b[0].x;
		    TempB[3].y = b[0].y + 2 * Block.SIZE;
		    updateXY(2);
	}
	public void getDirection3() {
		getDirection1();
		
	}
	public void getDirection4() {
		getDirection2();
	}

}
