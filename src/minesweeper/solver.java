package minesweeper;

public class solver {

	int[][] botBoard = new int[30][16];
	
	//this board gives the probability that the adjacent tiles are bombs
	float[][] adjP = new float[30][16];
	
	//the number of unclicked boxes adjacent
	int[][] adjC = new int[30][16];
	
	//this board gives the probability that the tile we're on should be clicked
	float[][] clickP = new float[30][16];
	
	//threshold value
	double a = 0.7f;
	boolean hasClicked = false;
	
	public void scanner() {
		//scan through the board and assign the adjacent probabilities
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 30; x++) {
				if (botBoard[x][y] != 10 && botBoard[x][y] != 0) {
					
					adjC[x][y] = 0;
					
					for (int sy = (y - 1); sy < (y + 2); sy++) {
						for (int sx = (x - 1); sx < (x + 2); sx++) {
							if (sx >= 0 && sx < 30 && sy >= 0 && sy < 16) {
								//if (sx != x && sy != y) {
									
									//System.out.println("sx: " + sx + ", sy: " + sy);
									if (botBoard[sx][sy] == 10) {
										adjC[x][y]++;
									}	
								//}							
							}
						}
					}
					adjP[x][y] = ((float) botBoard[x][y]/((float) adjC[x][y]));
				}
			}
		}
		
		//now go back through and assess the probability that each box should be clicked
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 30; x++) {
				
				clickP[x][y] = 0;
				
				for (int sy = (y - 1); sy < (y + 2); sy++) {
					for (int sx = (x - 1); sx < (x + 2); sx++) {
						if (sx >= 0 && sx < 30 && sy >= 0 && sy < 16) {
							if ((sx != x) || (sy != y)) {
								clickP[x][y] = clickP[x][y] + adjP[sx][sy];
							}					
						}
					}					
				}
			}
		}
	}
	
	public boolean clicker(int[][] gameBoard) {
		
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 30; x++) {
			//	System.out.println("a: " + a);
			//	System.out.println("click: " + clickP[x][y]);
				if ((clickP[x][y] < a) && (clickP[x][y] != 0)) {
					//System.out.println("x: " + x + ", y: " + y);
					hasClicked = true;
					if (gameBoard[x][y] == 9) {
						a = a - 0.01;
						System.out.println("a: " + a);
						return false;
					} else {
					botBoard[x][y] = gameBoard[x][y];
					}
				}
			}
		}	
		return true;
	}
	
}
