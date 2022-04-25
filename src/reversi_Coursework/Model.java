package reversi_Coursework;

import java.util.ArrayList;
import java.util.Arrays;


public class Model {

	ArrayList<View> views = new ArrayList<View>();
	Piece[] pieces = new Piece[64];
	Piece currentTurn = Piece.WHITE;
	
	ArrayList<PotentialMove> potentialMoves = new ArrayList<PotentialMove>();
	
	int[] edgeCasesLeft = {8, 16, 24, 32, 40, 48};
	int[] edgeCasesRight = {15, 23, 31, 39, 47, 55};
	int[] edgeCasesTop = {1, 2, 3, 4, 5, 6};
	int[] edgeCasesBottom = {57, 58, 59, 60, 61, 62};
	
	int[] cornerCases = {0, 7, 56, 63};
	
	int[] nonEdgeCaseTransforms = {-9, -8, -7, -1, 0, 1, 7, 8, 9};
	
	int[] leftEdgeCaseTransforms = {-8, -7,  1,  8, 9};
	int[] rightEdgeCaseTransforms = {-9, -8, -1, 7, 8};
	int[] topEdgeCaseTransforms = {-1, 1, 7, 8, 9};
	int[] bottomEdgeCaseTransforms = {-9, -8, -7, -1, 1};
	
	
	private class PotentialMove{//A struct-like class to help me with associating potential moves and the pieces taken when that move is maid
		private int moveIndex;
		ArrayList<Integer> takenPieces = new ArrayList<>();
		
		public void setMoveIndex(int i)
		{
			this.moveIndex=i;
		}
		public int getMoveIndex()
		{
			return this.moveIndex;
		}
		public void addTakenPiece(int takenPiece)
		{
			takenPieces.add(takenPiece);
		}
		
		public int getLengthTakenPieces()
		{
			return takenPieces.size();
		}
		
		public ArrayList<Integer> getPieces()
		{
			return takenPieces;
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
	
	public void makeMove(int index, Piece p)
	{
		
		System.out.println("potentialMoves.size(): " + potentialMoves.size() );
		for(int i=0; i<potentialMoves.size(); i++)
		{
			
			System.out.println("index: " + index +", potentialMoves.get(i).getMoveIndex():" + potentialMoves.get(i).getMoveIndex());
			
			if(potentialMoves.get(i).getMoveIndex()==index)
			{
				ArrayList<Integer> piecesToMove = potentialMoves.get(i).getPieces();
				System.out.println("PiecesToMove.Size() "+ piecesToMove.size());
				for(int j = 0; j<piecesToMove.size(); j++)
				{
					pieces[piecesToMove.get(j)]=p;
				}
			}
		}
		
	}
	
	public Piece getOppositePiece(Piece p)
	{
		if (p==Piece.WHITE)
			return Piece.BLACK;
		
		else if(p==Piece.BLACK) 
			return Piece.WHITE;
		
		else if(p==Piece.POTENTIAL)
			return Piece.POTENTIAL;
		
		return Piece.EMPTY;
				
	}
	
	public int[] generateMaxPositions(int index)
	{
		
	}
	
	public void clearPotentialMoves()
	{
		for (int i = 0 ; i < pieces.length ; i++)
		{
			if(pieces[i]==Piece.POTENTIAL)
				pieces[i]=Piece.EMPTY;
			
			potentialMoves.clear();
			
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
					pieces[i]=Piece.POTENTIAL;
				}
			}
		}
	}
	
	private PotentialMove processPoints(PotentialMove m, int[] transforms, int index)
	{
		for(int k=0; k<transforms.length; k++)
		{	
			
			int offset=transforms[k];
			int i = index;
			
			
			if(pieces[i]==Piece.EMPTY&&pieces[i+offset]==getOppositePiece(currentTurn))
			{
				while((i>=0&&i<=63))
				{
				
					i+=offset;
					if(pieces[i]==currentTurn)
					{
						int j = i;
						boolean valid=true;
						
						
						while(j!=index&&valid)
						{
							j-=offset;
							m.addTakenPiece(j);
						}
						break;
					}
					else if(pieces[i]==Piece.EMPTY)
						break;
					
				}			
			}
		}
		
		return m;
		
	}
	
	private boolean validatePoint(int index)
	{
		
		PotentialMove m = new PotentialMove();
		m.setMoveIndex(index);
		
		if(Arrays.binarySearch(edgeCasesLeft, index) < 0 && Arrays.binarySearch(edgeCasesRight, index) < 0  &&
				Arrays.binarySearch(edgeCasesTop, index) < 0  && Arrays.binarySearch(edgeCasesBottom, index) < 0 &&
				Arrays.binarySearch(cornerCases, index) < 0)//General Case
		{
			processPoints(m, nonEdgeCaseTransforms, index);
		}
		else if (Arrays.binarySearch(edgeCasesLeft, index) > 0 && Arrays.binarySearch(cornerCases, index) < 0)
		{
			processPoints(m, leftEdgeCaseTransforms, index);
		}
		else if (Arrays.binarySearch(edgeCasesRight, index) > 0 && Arrays.binarySearch(cornerCases, index) < 0)
		{
			processPoints(m, rightEdgeCaseTransforms, index);
		}
		else if (Arrays.binarySearch(edgeCasesTop, index) > 0 && Arrays.binarySearch(cornerCases, index) < 0)
		{
			processPoints(m, topEdgeCaseTransforms, index);
		}
		else if (Arrays.binarySearch(edgeCasesBottom, index) > 0 && Arrays.binarySearch(cornerCases, index) < 0)
		{
			processPoints(m, bottomEdgeCaseTransforms, index);
		}
		else if (Arrays.binarySearch(cornerCases, index) >0)
		{
			if(index==0)
			{
				int[] i= {1,8,9};
				processPoints(m, i, index);
			}
			else if(index==7)
			{
				int[] i= {-1,7,8};
				processPoints(m, i, index);
			}
			else if(index==56)
			{
				int[] i= {1,-8,-7};
				processPoints(m, i, index);
			}
			else if(index==63)
			{
				int[] i= {-1,-8,-9};
				processPoints(m, i, index);
			}
		}
		
		if(m.getLengthTakenPieces()>0)
		{
			potentialMoves.add(m);
			return true;
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
		this.makeMove(position, piece);
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
