package minesweeper;

import java.util.Random;
import minesweeper.solver;

public class mainMine {

	public static int searchAdjacent(int x, int y, int[][] botBoard, int[][] gameBoard) {
		
		int r = 1;
		
		for (int sy = (y - 1); sy < (y + 2); sy++) {
			for (int sx = (x - 1); sx < (x + 2); sx++) {
				if (sx >= 0 && sx < 30 && sy >= 0 && sy < 16) {
					if (sx == x && sy == y) {
						
					} else {
						if (botBoard[sx][sy] != gameBoard[sx][sy]) {
							botBoard[sx][sy] = gameBoard[sx][sy];
							r = 0;
						}
						if (botBoard[sx][sy] == 0) {
							
							
						}
					}
				}
			}
		}
		
		return r;
	}
	
	public static void main(String[] args) {
		
		boolean loop = true;
		
		for (int k = 0; k < 100; k++) {
			while (loop) {
				int[][] gameBoard = new int[30][16];
				int bombs = 0;
				Random rand = new Random();
		
				int  xr, yr;
				
				while (bombs < 99) {
					xr = rand.nextInt(30);
					yr = rand.nextInt(16);
		
					if (gameBoard[xr][yr] != 9) {
						gameBoard[xr][yr] = 9;
						bombs++;
					}
				}
				
				for (int y = 0; y < 16; y++) {
					for (int x = 0; x < 30; x++) {
						for (int searchy = y-1; searchy < (y+2); searchy++) {
							for (int searchx = x-1; searchx < (x+2); searchx++) {
								if ((searchx >= 0) && (searchx < 30) && (searchy >= 0) && (searchy < 16)) {
									if ((gameBoard[searchx][searchy] == 9) && (gameBoard[x][y] != 9)) {
										gameBoard[x][y]++;
									}
								}
							}
						}
						if (gameBoard[x][y] == 0) {
						//	System.out.print("- ");
						} else {
						//	System.out.print(gameBoard[x][y] + " ");
						}
					}
					//System.out.println();
				}
				/*
				System.out.println("------------------------");
				System.out.println("Time to solve!");
				System.out.println("------------------------");
				*/
				//create a new bot
				solver pivot = new solver();
				
				pivot.botBoard = new int[30][16];
				
				//make all boxes 0
				//0 on gameboard means a blank square
				//0 on botboard is unseen
				for (int i = 0; i < 30; i++) {
					for (int j = 0; j < 16; j++) {
						pivot.botBoard[i][j] = 10;
					}
				}
				
				//select random point
				xr = rand.nextInt(30);
				yr = rand.nextInt(16);
				
				
				//make sure the first click isn't a bomb
				while (gameBoard[xr][yr] == 9) {
					xr = rand.nextInt(30);
					yr = rand.nextInt(16);
				}
				
				if (gameBoard[xr][yr] != 9) {
					pivot.botBoard[xr][yr] = gameBoard[xr][yr];
					//System.out.println(botBoard[xr][yr]);
					//System.out.println("xr: " + xr);
					//System.out.println("yr: " + yr);
				}
		
				//search surrounding area
				if (pivot.botBoard[xr][yr] == 0) {
					searchAdjacent(xr, yr, pivot.botBoard, gameBoard);
				}
				
				for (int i = 0; i < 30; i++) {
					for (int j = 0; j < 16; j++) {
						if (pivot.botBoard[i][j] == 0) {
							if (searchAdjacent(i, j, pivot.botBoard, gameBoard) == 0) {
								i = 0;
								j = 0;
							}
						}
					}
				}
				
				//for (int y = 0; y < 16; y++) {
				//	for (int x = 0; x < 30; x++) {
							//System.out.print(pivot.botBoard[x][y] + " ");
				//	}
						//System.out.println();
				//}
				while (!pivot.hasClicked) {
					pivot.scanner();
					loop = pivot.clicker(gameBoard);
				}
			}
			System.out.println("k: " + k);
			loop = true;
		}
	}
}