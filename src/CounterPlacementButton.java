package reversi_Coursework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;

public class CounterPlacementButton extends JButton
{
	
	int height, circleRad, position;
	Piece currentPiece;
	Color backgroundColor, circleColor;
	
	
	public CounterPlacementButton(int height, Piece p, int position)
	{
		this.backgroundColor = Color.green;
		this.setBackground(backgroundColor);
		this.currentPiece=p;
		this.position = position;
		this.circleRad=height-2;
		setMinimumSize( new Dimension(height, height) );
		setPreferredSize( new Dimension(height, height) );
		
		
	};
	
	public int getBoxHeight()
	{
		return height;
	}
	
	public int getCircleRad()
	{
		return circleRad;
	}
	
	public Color getBackground()
	{
		return backgroundColor;
	}
	
	public Color getCircleColor()
	{
		return circleColor;
	}
	
	public Piece getPiece()
	{
		return currentPiece;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPiece(Piece p)
	{
		this.currentPiece = p;
	}
	
	protected void updateBG()
	{
		if(this.currentPiece==Piece.POTENTIAL)
		{
			this.backgroundColor=Color.yellow;
		}else {
			this.backgroundColor=Color.green;
		}
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(this.currentPiece==Piece.WHITE)
		{
			g.setColor(Color.white);
			g.fillOval(1, 1, circleRad, circleRad);
			g.setColor(Color.black);
			g.drawOval(1, 1, circleRad, circleRad);
		}
		else if(this.currentPiece==Piece.BLACK)
		{
			g.setColor(Color.black);
			g.fillOval(1, 1, circleRad, circleRad);
			g.setColor(Color.white);
			g.drawOval(1, 1, circleRad, circleRad);
		}
	
	}
	
}
