package reversi_Coursework;

import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class View implements ActionListener{
	
	CounterPlacementButton[] buttonArray = new CounterPlacementButton[64];
	JFrame guiFrame = new JFrame();
	JLabel turnOutput;
	Model model;
	String colour;
	Piece myPiece,currentTurn;
	boolean reverse;
	
	public View(Model m, boolean reverse, String playerColor)
	{
		model = m;
		colour=playerColor;
		model.storeView(this);
		this.reverse = reverse;
		if(playerColor=="White") {
			this.myPiece=Piece.WHITE;
		}
		else
		{
			this.myPiece=Piece.BLACK;
		}
	}
	
	public void createGUI()//On Page intialization this gets executed
	{
    	guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close window exits
    	guiFrame.setTitle(colour + "'s Window"); // Set a caption/title bar content for the frame
    	//guiFrame.setLocationRelativeTo(null);  // centre on screen
    	guiFrame.setLayout( new BorderLayout(8,8) ); // Layout : how to layout components
    	currentTurn=model.getCurrentTurn();
    	
    	turnOutput = new JLabel();
    	
    	if(myPiece==currentTurn)
    	{
    		turnOutput.setText("My Turn");
    	}
    	else
    	{
    		if(currentTurn==Piece.WHITE)
    		{
    			turnOutput.setText("White's Turn");
    		}
    		else
    		{
    			turnOutput.setText("Black's Turn");
    		}
    	}
    	
    	turnOutput.setFont(new Font("Ariel", Font.BOLD, 11));
    	
    	JPanel panel = new JPanel();
    	panel.setLayout( new GridLayout(8,8) );
    	
    	if(!reverse)
    	{
    		for ( int i = 0 ; i < buttonArray.length ; i++ )
    		{
    			buttonArray[i] = new CounterPlacementButton(40, model.get(i), i);
    		}
    	}
    	else
    	{
    		for ( int i = 0 ; i < buttonArray.length ; i++ )
    		{
    			buttonArray[i] = new CounterPlacementButton(40, model.get(63-i), 63-i);
    		}
    	}
		
    	for ( int i = 0 ; i < buttonArray.length ; i++ )
		{
    		buttonArray[i].addActionListener(this);
    		panel.add(buttonArray[i]);
    		
		}
    	
		update();
		
		JButton button = new JButton("AI Move");
		button.setFont(new Font("Ariel", Font.BOLD, 17));
		button.addActionListener( this );
		guiFrame.add(turnOutput, BorderLayout.NORTH);
		guiFrame.add(panel, BorderLayout.CENTER );
		guiFrame.add(button, BorderLayout.SOUTH );
		
    	guiFrame.pack(); // Resize frame to fit content
    	guiFrame.setVisible(true); // Display it – until you do it will not appear
	}

	@Override
	public void actionPerformed(ActionEvent e)//Listens for any event
	{
		if(model.getCurrentTurn()==myPiece && e.getActionCommand().equals("AI Move")) {//Checks to see if the AI button is pressed
			model.set(model.autoMove(), myPiece);
			if(model.getCurrentTurn()==Piece.WHITE)
				model.setCurrentTurn(Piece.BLACK);
			else
				model.setCurrentTurn(Piece.WHITE);
			
			model.clearPotentialMoves();
		}
		else if(model.getCurrentTurn()==myPiece && 
				((CounterPlacementButton)e.getSource()).getPiece()==Piece.POTENTIAL)//Checks if the current turn is the turn associated to the window and if the placement is valid
		{
			
			
			model.set(((CounterPlacementButton)e.getSource()).getPosition(), myPiece);//Updates the model with the newly placed piece
			
//			model.makeMove(((CounterPlacementButton)e.getSource()).getPosition());
			
			if(model.getCurrentTurn()==Piece.WHITE)
				model.setCurrentTurn(Piece.BLACK);
			else
				model.setCurrentTurn(Piece.WHITE);
			
			model.clearPotentialMoves();
			
			
		}
		
		model.updateAllViews();//Calls update on all the boards so that they reflect the new changes to the model.
	}
	
	public void update() // Gets applied to all Boards
	{	
		model.updatePotentialMoves();
		
		model.checkMakeMoves();		
		
		if(myPiece==model.getCurrentTurn())
    	{
    		turnOutput.setText("My Turn");
    		
    	}
    	else
    	{
    		
    		if(model.getCurrentTurn()==Piece.WHITE)
    			turnOutput.setText("White's Turn");
    		else
    			turnOutput.setText("Black's Turn");
    		
    	}
		
		for ( int i = 0 ; i < buttonArray.length ; i++ )
		{
			if(!reverse)
				buttonArray[i].setPiece(model.get(i));
			else
				buttonArray[i].setPiece(model.get(63-i));
			if(model.getCurrentTurn()==myPiece)
				buttonArray[i].updateBG();
			else
				buttonArray[i].wipeBG();
			
		} 
		
		guiFrame.repaint();
		
	}
	
}
