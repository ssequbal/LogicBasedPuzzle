import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.border.Border;

/*
 *  The main window of the gui.
 *  It extends JFrame - so we can add our own components and it implements ActionListener and MouseListener- so we can handle user input.
 *  @author ssequbal
 */
public class mindTheGap extends JFrame implements ActionListener, MouseListener
{
	// gui components that are contained in this frame:
	private JPanel topPanel, bottomPanel;	// top and bottom panels in the main window
	private JLabel instructionLabel;		// a text label to tell the user what to do
    private JButton topButton;				// a 'New Game' button to appear in the top panel
	private GridSquare [][] gridSquares;	// squares to appear in grid formation in the bottom panel
	private int colordetector,nextdetector;
	private Color colourofneighbour1=Color.white,colourofneighbour2=Color.white,colourofneighbour3=Color.white,colourofneighbour4=Color.white,colourofneighbour5=Color.white,colourofneighbour6=Color.white,colourofneighbour7=Color.white,colourofneighbour8=Color.white;
	private int i=1;
	/*
	 *  constructor method creates the panels, their subcomponents and puts them all together in the main frame
	 *  it makes sure that action listeners are added to selectable items
	 *  it makes sure that the gui will be visible
	 */

	public mindTheGap()
	{
		
		this.setSize(600,600); //size of each tile
		
		// first create the panels
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(4, 4));     //To form a 4X4 grid in the bottom panel where the game will be played
		bottomPanel.setSize(500,500);
		
		// then create the components for each panel and add them to it
		
		// for the top panel:

		instructionLabel = new JLabel("Don't select the neighbour squares! Click to begin>>");
		topButton = new JButton("New Game");
		topButton.addActionListener(this);			// Adding an actionlistener to the newgame button 
		
		topPanel.add(instructionLabel);
		topPanel.add(topButton);
        
		Border blackline = BorderFactory.createLineBorder(Color.lightGray);  //Creating a border between each tile
	    
		// for the bottom panel:	
		// create the squares and add them to the grid

		gridSquares = new GridSquare[4][4];

		for ( int x = 0; x < 4; x ++)             //nested for loops to create the 4X4 grid
		{
			for ( int y = 0; y < 4; y ++)
			{
				gridSquares[x][y] = new GridSquare(x, y);
				gridSquares[x][y].setSize(20, 20);
				gridSquares[x][y].setBackground(Color.white);  // Colour of each unselected tile 
				gridSquares[x][y].setBorder(blackline);        // Colour of the border
				
				bottomPanel.add(gridSquares[x][y]);            // Adding each grid square to the bottom panel
			}
		}
		
		// now add the top and bottom panels to the main frame
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(bottomPanel, BorderLayout.CENTER);		// needs to be center or will draw too small
		
		// housekeeping : behaviour
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	
	/*
	 *  handles actions performed in the gui
	 *  this method must be present to correctly implement the ActionListener interface
	 */
	public void actionPerformed(ActionEvent aevt)
	{
		// get the object that was selected in the gui
		Object selected = aevt.getSource();
			
		// if New Game button is clicked 

		if ( selected.equals(topButton) )

		{   if (i==1)
		     {   i=i-1;
				 topButton.doClick();
			    }

			Random rand=new Random();         //To randomly chose which player goes first we use the random function
			int z = rand.nextInt(2);
			if (z == 0)
			    { instructionLabel.setText("Player 2's turn");
				 colordetector=0;             //To keep track of the specifics of the current tile 
				 nextdetector=0;
				}
            else
               {  instructionLabel.setText("Player 1's turn");
				 colordetector=1;            //To keep track of the specifics of the current tile 
				 nextdetector=1;
				 }

		   //If New Game is clicked we have to reset the entire game so we change the colour of tile back to white 

		    for ( int x = 0; x < 4; x ++)
		  {
			    for ( int y = 0; y < 4; y ++)
			   {    
				 gridSquares[x][y].setBackground(Color.white);      
				 gridSquares[x][y].addMouseListener(this);	//By placing the mouse listener here we ensure that the tiles are unclickable before the user has clicked the New Game button  
			   }
		    }
				 
		}
	}


	// Mouse Listener events
	public void mouseClicked(MouseEvent mevt)
	{
		// get the object that was selected in the gui
		Object selected = mevt.getSource();
		
		
		// if a gridsquare is selected then change its color to either red or blue based on which player is playing

		if (selected instanceof GridSquare)
		{   
            GridSquare square = (GridSquare) selected;
			Color colourofsquare=square.getBackground();

			if (colourofsquare==Color.white)   //this if statement prevents user form overriding an already coloured tile
            {
			  if (nextdetector == 0)
			    { instructionLabel.setText("Player 1's turn");
				 colordetector=1;
				 nextdetector=1;}
              else
               {  instructionLabel.setText("Player 2's turn");
				 colordetector=0;
				 nextdetector=0;}

			square.switchColor(colordetector);
		
			}
			
            int x = square.getXcoord();
            int y = square.getYcoord();

		    if(y-1>=0)
			 colourofneighbour1=gridSquares[x][y-1].getBackground();     //To check the colour of the tile left of the current tile
			else
			 colourofneighbour1=Color.white;
			if(y+1<4)
			 colourofneighbour2=gridSquares[x][y+1].getBackground();     //To check the colour of the tile right of the current tile
			else
			 colourofneighbour2=Color.white;
			if(x-1>=0)
			 colourofneighbour3=gridSquares[x-1][y].getBackground();     //To check the colour of the tile above the current tile
			else
			 colourofneighbour3=Color.white;
			if(x+1<4)
			 colourofneighbour4=gridSquares[x+1][y].getBackground();     //To check the colour of the tile below the current tile
			else
			 colourofneighbour4=Color.white;
			if(x-1>=0 && y-1>=0)
			 colourofneighbour5=gridSquares[x-1][y-1].getBackground();   //To check the colour of the tile top-left of the current tile
			else
			 colourofneighbour5=Color.white;
			if(x+1<4 && y+1<4)
			 colourofneighbour6=gridSquares[x+1][y+1].getBackground();   //To check the colour of the tile bottom-right of the current tile
			else
			 colourofneighbour6=Color.white;
			if(y+1<4 && x-1>=0)
			 colourofneighbour7=gridSquares[x-1][y+1].getBackground();   //To check the colour of the tile top-right of the current tile
			else
			 colourofneighbour7=Color.white;
			if(x+1<4 && y-1>=0)
			 colourofneighbour8=gridSquares[x+1][y-1].getBackground();   //To check the colour of the tile bottom-left of the current tile
			else
			 colourofneighbour8=Color.white;
			
			if (colourofsquare==Color.blue)
			{if (colourofneighbour1==Color.blue||colourofneighbour2==Color.blue||colourofneighbour3==Color.blue||colourofneighbour4==Color.blue||colourofneighbour5==Color.blue||colourofneighbour6==Color.blue||colourofneighbour7==Color.blue||colourofneighbour8==Color.blue)
			  instructionLabel.setText("Player 1 wins! Click to play again>>>");
			  
		     
				 
			}

			if (colourofsquare==Color.red)
			{if (colourofneighbour1==Color.red||colourofneighbour2==Color.red||colourofneighbour3==Color.red||colourofneighbour4==Color.red||colourofneighbour5==Color.red||colourofneighbour6==Color.red||colourofneighbour7==Color.red||colourofneighbour8==Color.red)
			  instructionLabel.setText("Player 2 wins! Click to play again>>>");
			  
		     
			}
			
           
		}	
	}
	
	// not used but must be present to fulfil MouseListener contract
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
