
public class BoardModel {
	
	private int lightPieces = 2;
	private int darkPieces = 2;
	
	private int board[][] ={{0,0,0,0,0,0,0,0}, //Initialising the board in it's empty state
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,1,2,0,0,0},
							{0,0,0,2,1,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0}} ;
	
	public int[][] getBoard(boolean flipped){
		int returnedBoard[][] = new int[8][8];
		for (int x=0; x<board.length; x++) {
			for (int y=0; y<board[x].length; y++) {
				if(!flipped)
					returnedBoard[x][y] = board[x][y];
				else
					returnedBoard[y][x] = board[7-x][7-y];
			}
		}
		return returnedBoard;
	}
	
	public void setBoard(int x, int y, int piece, boolean flipped) {
		if(!flipped) {
			board[x][y] = piece;
			if(piece ==1)
				lightPieces++;
			else
				darkPieces++;
		}else {
		
			board[7-x][7-y] = piece;
			if(piece ==1)
				lightPieces++;
			else
				darkPieces++;
			
		}
	}
	
	public int getLight() {
		return lightPieces;
	}
	
	public int getDark() {
		return darkPieces;
	}
		
}
