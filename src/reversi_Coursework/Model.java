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
	
	int[] leftEdgeCaseTransforms = {-8, -7,  0, 1,  8, 9};
	int[] rightEdgeCaseTransforms = {-9, -8, -1, 0, 7, 8};
	int[] topEdgeCaseTransforms = {-1, 0, 1, 7, 8, 9};
	int[] bottomEdgeCaseTransforms = {-9, -8, -7, -1, 0, 1};
	
	public boolean isIn(int[] haystack, int needle)
	{
		for(int i=0; i<haystack.length; i++)
		{
			if(haystack[i]==needle)
				return true;
		}
		
		return false;
	}
	
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
		for(int i=0; i<potentialMoves.size(); i++)
		{
			if(potentialMoves.get(i).getMoveIndex()==index)
			{
				ArrayList<Integer> piecesToMove = potentialMoves.get(i).getPieces();
				for(int j = 0; j<piecesToMove.size(); j++)
				{
					pieces[piecesToMove.get(j)]=currentTurn;
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
	
	public void clearPotentialMoves()
	{
		for (int i = 0 ; i < pieces.length ; i++)
		{
			if(pieces[i]==Piece.POTENTIAL)
				pieces[i]=Piece.EMPTY;
		}
		potentialMoves.clear();
	}
	
	public void updatePotentialMoves()
	{
		
		for(int i=0; i<pieces.length; i++)
		{
			
			if(pieces[i]==Piece.EMPTY&&validatePoint(i))
			{
				pieces[i]=Piece.POTENTIAL;
			}
		}
	}
	
	private boolean generalCase(int i1)
	{
		return !isIn(edgeCasesLeft, i1)&&!isIn(edgeCasesRight, i1)
		&&!isIn(edgeCasesTop, i1)&&!isIn(edgeCasesBottom, i1)
		&&!isIn(cornerCases, i1);
	}
	
	private PotentialMove processPoints(PotentialMove m, int[] transforms, int index)
	{
		for(int k=0; k<transforms.length; k++)
		{	
			
			int offset=transforms[k];
			int i = index, lowerLimit=0, upperLimit=63;
			
			if(offset==1||offset==-1)
			{
				lowerLimit=(index/8)*8;
				upperLimit=lowerLimit+7;
			}
			else if(offset==9||offset==-9)
			{
				int i1=index;
				if((index!=0)&&(index!=63))
				{
					if(generalCase(i1))
					{
						while(generalCase(i1))
						{
							i1+=offset;
						}							
					}
					else if(isIn(edgeCasesLeft, i1) && !isIn(cornerCases, i1))
					{
						while(!isIn(edgeCasesRight, i1)&&!isIn(edgeCasesTop, i1)
								&&!isIn(edgeCasesBottom, i1)&&!isIn(cornerCases, i1))
						{
							i1+=offset;
						}
					}
					else if(isIn(edgeCasesRight, i1) && !isIn(cornerCases, i1))
					{
						while(!isIn(edgeCasesLeft, i1)&&!isIn(edgeCasesTop, i1)
								&&!isIn(edgeCasesBottom, i1)&&!isIn(cornerCases, i1))
						{
							i1+=offset;
						}
					}
					else if(isIn(edgeCasesTop, i1) && !isIn(cornerCases, i1))
					{
						while(!isIn(edgeCasesRight, i1)&&!isIn(edgeCasesLeft, i1)
								&&!isIn(edgeCasesBottom, i1)&&!isIn(cornerCases, i1))
						{
							i1+=offset;
						}
					}
					else if(isIn(edgeCasesBottom, i1) && !isIn(cornerCases, i1))
					{
						while(!isIn(edgeCasesRight, i1)&&!isIn(edgeCasesTop, i1)
								&&!isIn(edgeCasesLeft, i1)&&!isIn(cornerCases, i1))
						{
							i1+=offset;
						}
					}
					if(offset>0)
					{
						upperLimit=i1;
					}
					else if(offset<0)
					{
						lowerLimit=i1;
					}
					
				}
				else if(index==0&&offset==9)
				{
					upperLimit=63;
				}
				else if(index==63&&offset==-9)
				{
					lowerLimit=0;
				}
								
			}
			else if(offset==7||offset==-7)
			{
				int i1=index;
				if((index!=7)&&(index!=56))
				{	
					if(generalCase(i1))
					{
						while(generalCase(i1))
						{
							i1+=offset;
						}							
					}
					else if(isIn(edgeCasesLeft, i1) && !isIn(cornerCases, i1))
					{
						while(!isIn(edgeCasesRight, i1)&&!isIn(edgeCasesTop, i1)
								&&!isIn(edgeCasesBottom, i1)&&!isIn(cornerCases, i1))
						{
							i1+=offset;
						}
					}
					else if(isIn(edgeCasesRight, i1) && !isIn(cornerCases, i1))
					{
						while(!isIn(edgeCasesLeft, i1)&&!isIn(edgeCasesTop, i1)
								&&!isIn(edgeCasesBottom, i1)&&!isIn(cornerCases, i1))
						{
							i1+=offset;
						}
					}
					else if(isIn(edgeCasesTop, i1) && !isIn(cornerCases, i1))
					{
						while(!isIn(edgeCasesRight, i1)&&!isIn(edgeCasesLeft, i1)
								&&!isIn(edgeCasesBottom, i1)&&!isIn(cornerCases, i1))
						{
							i1+=offset;
						}
					}
					else if(isIn(edgeCasesBottom, i1) && !isIn(cornerCases, i1))
					{
						while(!isIn(edgeCasesRight, i1)&&!isIn(edgeCasesTop, i1)
								&&!isIn(edgeCasesLeft, i1)&&!isIn(cornerCases, i1))
						{
							i1+=offset;
						}
					}
					if(offset>0)
					{
						upperLimit=i1;
					}
					else if(offset<0)
					{
						lowerLimit=i1;
					}
				}
				else if(index==7&&offset==7)
				{
					upperLimit=56;
				}
				else if(index==56&&offset==-7)
				{
					lowerLimit=7;
				}
			}
			
			//System.out.println("index: "+ index +". offset:" + offset + ". Limits: " + lowerLimit + ", " + upperLimit);
			
			if(pieces[index+offset]==getOppositePiece(currentTurn))
			{
				boolean validDirection=true;
				i+=offset;
				while((i>=lowerLimit&&i<=upperLimit)&&pieces[i]!=currentTurn)
				{
					if(pieces[i]!=getOppositePiece(currentTurn))
					{
						System.out.println("A B");
						validDirection=false;
						break;
					}
					i+=offset;
				}
				
				if(validDirection)
				{
					System.out.println("B");
					int j=index;
					while(j!=i)
					{
						m.addTakenPiece(j);
						j+=offset;
					}
				}
			}
//			if(pieces[index+offset]==getOppositePiece(currentTurn))//If we are next to one of the opponents pieces
//			{
//				
////				while(pieces[i]!=currentTurn)
////				{
////					i+=offset;
////					if(!(i<=upperLimit&&i>=lowerLimit))
////					{
////						i-=offset;
////						break;
////					}
////				}
////				
////				if(pieces[i]==currentTurn)
////				{
////					int j = index+offset;
////					
////					while(j!=i&&pieces[j]!=Piece.EMPTY)
////					{
////						m.addTakenPiece(j);
////						j+=offset;	
////					}
////				}	
//				
//				
//				
//			}
		}
		return m;
		
	}
	
	private boolean validatePoint(int index)
	{
		
		PotentialMove m = new PotentialMove();
		m.setMoveIndex(index);
		if(generalCase(index))//General Case
		{
			processPoints(m, nonEdgeCaseTransforms, index);
			
		}
		else if (isIn(edgeCasesLeft, index) && !isIn(cornerCases, index))
		{
			processPoints(m, leftEdgeCaseTransforms, index);
			
		}
		else if (isIn(edgeCasesRight, index) && !isIn(cornerCases, index))
		{
			processPoints(m, rightEdgeCaseTransforms, index);
			
		}
		else if (isIn(edgeCasesTop, index) && !isIn(cornerCases, index))
		{
			processPoints(m, topEdgeCaseTransforms, index);
			
		}
		else if (isIn(edgeCasesBottom, index) && !isIn(cornerCases, index))
		{
			processPoints(m, bottomEdgeCaseTransforms, index);
			
		}
		else if (Arrays.binarySearch(cornerCases, index) >=0)
		{
			
			if(index==0)
			{
				int[] i= {0, 1, 8, 9};
				processPoints(m, i, index);
			}
			else if(index==7)
			{
				int[] i= {-1, 0, 7, 8};
				processPoints(m, i, index);
			}
			else if(index==56)
			{
				int[] i= {1, 0, -8, -7};
				processPoints(m, i, index);
			}
			else if(index==63)
			{
				int[] i= {-1, 0, -8,-9};
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
