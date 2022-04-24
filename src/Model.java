package reversi_Coursework;

import java.util.ArrayList;
import java.util.Arrays;


public class Model {

	ArrayList<View> views = new ArrayList<View>();
	Piece[] pieces = new Piece[64];
	Piece currentTurn = Piece.WHITE;
	
	PotentialMove[] potentialMoves = new PotentialMove[64];
	
	int[] edgeCasesLeft = {8, 16, 24, 32, 40, 48};
	int[] edgeCasesRight = {15, 23, 31, 39, 47, 55};
	int[] edgeCasesTop = {0, 1, 2, 3, 4, 5, 6, 7};
	int[] edgeCasesBottom = {56, 57, 58, 59, 60, 61, 62, 63};
	
	int[] nonEdgeCaseTransforms = {-9, -8, -7, -1, 0, 1, 7, 8, 9};
	
	private class PotentialMove{//A struct-like class to help me with associating potential moves and the pieces taken when that move is maid
		private int moveIndex;
		private int[] takenPieces=new int[64];
		
		public void setMoveIndex(int i)
		{
			this.moveIndex=i;
		}
		public int getMoveIndex()
		{
			return this.moveIndex;
		}
		public void setTakenPiece(int index, int takenPiece)
		{
			takenPieces[index]=takenPiece;
		}
		public int getTakenPiece(int indexAt)
		{
			return takenPieces[indexAt];
		}
		
		
	}
	
	public Model()
	{
		
		for(int i=0; i<pieces.length; i++)
			pieces[i]=Piece.EMPTY;
		
		pieces[27] = Piece.WHITE;
		pieces[36] = Piece.WHITE;
		pieces[28] = Piece.BLACK;
		pieces[35] = Piece.BLACK;
	}
	
	public Piece getOppositePiece(Piece p)
	{
		if (p==Piece.WHITE)
			return Piece.BLACK;
		else 
			return Piece.WHITE;
				
	}
	
	public void clearPotentialMoves()
	{
		for (int i = 0 ; i < pieces.length ; i++)
		{
			if(pieces[i]==Piece.POTENTIAL)
				pieces[i]=Piece.EMPTY;
			
			potentialMoves[i]=null;
			
		}
	}
	
	public void updatePotentialMoves()
	{
		System.out.println("Updating Potential Moves");
		
		for(int i=0; i<pieces.length; i++)
		{
			if(pieces[i]==Piece.EMPTY)
			{
				
				if(validatePoint(i))
				{
					System.out.println(i);
				}
				
			}
		}
	}
	
	private boolean validatePoint(int index)
	{
		if(Arrays.binarySearch(edgeCasesLeft, index) < 0 && Arrays.binarySearch(edgeCasesRight, index) <0  &&
				Arrays.binarySearch(edgeCasesTop, index) < 0  && Arrays.binarySearch(edgeCasesBottom, index) < 0 )//Checking to see if the current node is not an edge case
		{
			for(int k=0; k<nonEdgeCaseTransforms.length; k++)
			{
				for(int j = index; (j>=0&&j<=63)||pieces[j]==currentTurn; j+=nonEdgeCaseTransforms[k])
				{
					
					if(pieces[j]==Piece.EMPTY)
					{
						break;
					}
					else
					{
						
					}
					
				}
			}
		}
		
		return false;
	}
	
	public void storeView( View view )
	{
		views.add(view);
	}
	
	public Piece get(int i)
	{
		return pieces[i];
	}
	
	public void set(int position, Piece piece)
	{
		pieces[position]=piece;
	}
	
	public void updateAllViews()
	{
		for ( int i = 0 ; i < views.size() ; i++ )
			views.get(i).update();
	}
	public Piece getCurrentTurn()
	{
		return currentTurn;
	}
	public void setCurrentTurn(Piece t)
	{
		currentTurn =t;
	}
		
}
