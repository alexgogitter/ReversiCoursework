package reversi_Coursework;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;


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
	
	int[] nonEdgeCaseTransforms = {-9, -8, -7, -1, 1, 7, 8, 9};
	
	int[] leftEdgeCaseTransforms = {-8, -7, 1,  8, 9};
	int[] rightEdgeCaseTransforms = {-9, -8, -1, 7, 8};
	int[] topEdgeCaseTransforms = {-1, 1, 7, 8, 9};
	int[] bottomEdgeCaseTransforms = {-9, -8, -7, -1, 1};
	
	public boolean isIn(int[] haystack, int needle)
	{
		for(int i=0; i<haystack.length; i++)
		{
			if(haystack[i]==needle)
				return true;
		}
		
		return false;
	}
	
	private class PotentialMove{//A struct-like class to help me with associating potential moves and the pieces taken when that move is made
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
		
		this.currentTurn=Piece.WHITE;
		
		for(int i=0; i<pieces.length; i++)
			pieces[i]=Piece.EMPTY;
		
		pieces[27] = Piece.WHITE;
		pieces[36] = Piece.WHITE;
		pieces[28] = Piece.BLACK;
		pieces[35] = Piece.BLACK;
	}
	
	public void resetBoard()
	{
		this.currentTurn=Piece.WHITE;
		
		this.clearPotentialMoves();
		
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
	
	public int autoMove()
	{
		int currentMove = -1, currentMoveTaken = -1;
		for(int i=0; i<potentialMoves.size(); i++)
		{
			
			
			if(potentialMoves.get(i).getLengthTakenPieces()>currentMoveTaken)
			{
				currentMove=potentialMoves.get(i).getMoveIndex();
				currentMoveTaken=potentialMoves.get(i).getLengthTakenPieces();
			}
			
		}
		
		if(currentMove!=-1)
		{
			return currentMove;
		}
		
		return -1;
		
	}
	
	public void checkMakeMoves()
	{
		if(!(potentialMoves.size()>0))
		{
			this.currentTurn=getOppositePiece(currentTurn);
			this.updatePotentialMoves();
			if(!(potentialMoves.size()>0))
			{
				this.currentTurn=Piece.EMPTY;//Stops anyone from makiing moves.
				int black = 0, white = 0;
				
				for(int i = 0; i<64; i++)
				{
					if(this.get(i)==Piece.WHITE)
						white++;
					else if(this.get(i)==Piece.BLACK)
						black++;
				}
				
				String winner = "";
				if(black>white)
				{
					winner="Black";
				}
				else if(black<white)
				{
					winner = "White";
				}
				else if(black==white)
				{
					winner = "no one";
				}
				int confirm = JOptionPane.showOptionDialog(null, "The Winner is " + winner +"\n" + "White: "+ white+"\nBlack: " + black+"\n\nPlay Again?", "Play again?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
	         
				if(confirm==0)
				{
					this.resetBoard();
					this.updatePotentialMoves();
				}
				
				else
					System.exit(0);
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
//		if(canMakeMove())
//		{
		for(int i=0; i<pieces.length; i++)
		{
			
			if(pieces[i]==Piece.EMPTY&&validatePoint(i))
			{
				pieces[i]=Piece.POTENTIAL;
			}
		}
//		}
//		else
//		{
//			this.currentTurn=getOppositePiece(this.currentTurn);
//			
//			if(canMakeMove())
//			{
//				for(int i=0; i<pieces.length; i++)
//				{
//					
//					if(pieces[i]==Piece.EMPTY&&validatePoint(i))
//					{
//						pieces[i]=Piece.POTENTIAL;
//					}
//				}
//			}
//			else
//				this.currentTurn=Piece.EMPTY;
//		}
		
	}
	
	private boolean generalCase(int i1)
	{
		return !isIn(edgeCasesLeft, i1)&&!isIn(edgeCasesRight, i1)
		&&!isIn(edgeCasesTop, i1)&&!isIn(edgeCasesBottom, i1)
		&&!isIn(cornerCases, i1);
	}
	
	private void processPoints(PotentialMove m, int[] transforms, int index)
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
			boolean validDirection=true;
			int commonPiece=-1;
			
			while(i>=lowerLimit&&i<=upperLimit)
			{
				if(pieces[i]==currentTurn)
				{
					commonPiece=i;
					break;
				}
				
				i+=offset;
				
			}
			if(commonPiece>=0&&pieces[index+offset]==getOppositePiece(currentTurn))
			{
				int j = index+offset;
				while(j!=commonPiece)
				{
					if(pieces[j]!=getOppositePiece(currentTurn))
					{
						validDirection=false;
						break;
					}
					
					j+=offset;
				}
				
			}
			else
			{
				validDirection=false;
			}
		
			if(validDirection)
			{
				int j = index;
				while(j!=commonPiece)
				{
					m.addTakenPiece(j);
					j+=offset;
				}
			}
		}
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
				int[] i= { 1, 8, 9};
				processPoints(m, i, index);
			}
			else if(index==7)
			{
				int[] i= {-1, 7, 8};
				processPoints(m, i, index);
			}
			else if(index==56)
			{
				int[] i= {1,  -8, -7};
				processPoints(m, i, index);
			}
			else if(index==63)
			{
				int[] i= {-1, -8,-9};
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
