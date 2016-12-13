//https://codeshare.io/Ull76

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.Arrays;

public class battleship extends JFrame
{
	static public battleship b;
	
	JLabel pic1;
	static JLabel [][] defenseGrid;// = new JLabel[10][10];
	static JLabel [][] offenseGrid;
	static Boolean[][] gridOccupied;
	static String[][] gridOccupiedByShip;
	static Boolean isPressed = false;
	int moveX, moveY;
	static Boolean confirmed = false;
	static JButton confirm;
	static JButton automatic;
	
	static boolean isServer=false;
	static boolean isClient=false;
	static boolean winner=false;
	static server gameServer;
	static client gameClient;
	
	JTextArea infoText;
	
	PrintStream standardOut;
	JTextArea textArea;

	public static String boardPiecePNG = "src/images/boardpiece.png";
	
	public static Ship Ship5;
	public static Ship Ship4;
	public static Ship Ship31;
	public static Ship Ship32;
	public static Ship Ship2;
	
	public static final String ship5Link = "src/images/shipWheelFull.png";
	static ImageIcon ship5Image = new ImageIcon(ship5Link);
    
    public static final String ship4Link = "src/images/lifeBuoyFull.png";
    static ImageIcon ship4Image = new ImageIcon(ship4Link);

    public static final String ship31Link = "src/images/moonFull.png";
    static ImageIcon ship31Image = new ImageIcon(ship31Link);
    
    public static final String ship32Link = "src/images/bullseyeFull.png";
    static ImageIcon ship32Image = new ImageIcon(ship32Link);
    
    public static final String ship2Link = "src/images/sunFull.png";
    static ImageIcon ship2Image = new ImageIcon(ship2Link);
    
    
    public static final String[] ship5IndividualLinks = {"src/images/shipWheel.png", "src/images/shipWheel.png", "src/images/shipWheel.png", "src/images/shipWheel.png", "src/images/shipWheel.png"};
    static ImageIcon [] ship5Pic = {new ImageIcon(ship5IndividualLinks[0]), new ImageIcon(ship5IndividualLinks[1]), new ImageIcon(ship5IndividualLinks[2]), new ImageIcon(ship5IndividualLinks[3]), new ImageIcon(ship5IndividualLinks[4])};
    public static final String[] ship4IndividualLinks = {"src/images/lifeBuoy.png", "src/images/lifeBuoy.png", "src/images/lifeBuoy.png", "src/images/lifeBuoy.png"};
    static ImageIcon [] ship4Pic = {new ImageIcon(ship4IndividualLinks[0]), new ImageIcon(ship4IndividualLinks[1]), new ImageIcon(ship4IndividualLinks[2]), new ImageIcon(ship4IndividualLinks[3])};
    public static final String[] ship31IndividualLinks = {"src/images/moon.png", "src/images/moon.png", "src/images/moon.png"};
    static ImageIcon [] ship31Pic = {new ImageIcon(ship31IndividualLinks[0]), new ImageIcon(ship31IndividualLinks[1]), new ImageIcon(ship31IndividualLinks[2])};
    public static final String[] ship32IndividualLinks = {"src/images/bullseye.png", "src/images/bullseye.png", "src/images/bullseye.png"};
    static ImageIcon [] ship32Pic = {new ImageIcon(ship32IndividualLinks[0]), new ImageIcon(ship32IndividualLinks[1]), new ImageIcon(ship32IndividualLinks[2])};
    public static final String[] ship2IndividualLinks = {"src/images/sun.png", "src/images/sun.png"};
    static ImageIcon [] ship2Pic = {new ImageIcon(ship2IndividualLinks[0]), new ImageIcon(ship2IndividualLinks[1])};
    
    public static final String hitString = "src/images/redsquare.png";
    static ImageIcon hitIcon = new ImageIcon(hitString);
    public static final String missString = "src/images/whitesquare.png";
    static ImageIcon missIcon = new ImageIcon(missString);
    
    static TransferHandler[] fullShipTransferHandler;
    static TransferHandler shipTransferHandler;
    
    static gridChangeListener gridchangelis;
    gridMouseListen gridMouseLis;
    static TransferHandler[][] defGridTransferHandler;
    
    static int gridPieceDimension;
    
    static JLabel blank5, blank4, blank31, blank32, blank2;
    
	public battleship() 
	{
		super("Battleship");
		
		setLayout(null);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight() - 40;
	    setSize(width,height); // Battleship Application Size
	    gridPieceDimension = 60; //(int)(width * 0.03);
	    
	    fullShipTransferHandler = new ShipTransfer[5];
	    for (int i = 0; i < 5; i++)
	    {
	    	fullShipTransferHandler[i] = new ShipTransfer();
	    }
	    shipTransferHandler = new ShipTransfer();
	    gridchangelis = new gridChangeListener();
	    gridMouseLis = new gridMouseListen();
	    
	    //Create Ships
	    Ship5 = new Ship("Ship5",5);
	    Ship5.setIcon(ship5Image);
	    Ship5.setFullIcon(ship5Image);
	    Ship5.setBounds(800, 200, 330, 60);
	    Ship5.setHoles(ship5Pic);
		Ship5.addMouseMotionListener(MouseMotion.mml);
		Ship5.addMouseListener(ShipMouseListen.ml);
		//Ship5.setName("ship5");
		Ship5.setTransferHandler(fullShipTransferHandler[0]);		
		add(Ship5);
	    
	    Ship4 = new Ship("Ship4",4);
	    Ship4.setIcon(ship4Image);
	    Ship4.setFullIcon(ship4Image);
	    Ship4.setBounds(830, 260, 240, 60);
	    Ship4.setHoles(ship4Pic);
		Ship4.addMouseMotionListener(MouseMotion.mml);
		Ship4.addMouseListener(ShipMouseListen.ml);
		//Ship4.setName("Ship4");
		Ship4.setTransferHandler(fullShipTransferHandler[1]);
		add(Ship4);
	    
	    Ship31 = new Ship("Ship31",3);
	    Ship31.setIcon(ship31Image);
	    Ship31.setFullIcon(ship31Image);
	    Ship31.setBounds(850, 320, 205, 60);
	    Ship31.setHoles(ship31Pic);
		Ship31.addMouseMotionListener(MouseMotion.mml);
		Ship31.addMouseListener(ShipMouseListen.ml);
		//Ship31.setName("Ship31");
		Ship31.setTransferHandler(fullShipTransferHandler[2]);
		add(Ship31);
	    
	    Ship32 = new Ship("Ship32",3);
	    Ship32.setIcon(ship32Image);
	    Ship32.setFullIcon(ship32Image);
	    Ship32.setBounds(850, 380, 208, 60);
	    Ship32.setHoles(ship32Pic);
		Ship32.addMouseMotionListener(MouseMotion.mml);
		Ship32.setTransferHandler(fullShipTransferHandler[3]);
		Ship32.addMouseListener(ShipMouseListen.ml);
		//Ship32.setName("Ship32");
		add(Ship32);
	    
	    Ship2 = new Ship("Ship2",2);
	    Ship2.setIcon(ship2Image);
	    Ship2.setFullIcon(ship2Image);
	    Ship2.setBounds(880, 440, 138, 60);
	    Ship2.setHoles(ship2Pic);
		Ship2.addMouseMotionListener(MouseMotion.mml);
		Ship2.setTransferHandler(fullShipTransferHandler[4]);
		Ship2.addMouseListener(ShipMouseListen.ml);
		//Ship2.setName("Ship2");
		add(Ship2);
		
				
		
	    
		
	    defenseGrid = new JLabel[10][10]; //Initialize all defensive grid pieces
	    defGridTransferHandler = new TransferHandler[10][10];
		for (int row = 0; row < 10; row++) 
		{
			for (int col = 0; col < 10; col++)
			{
				defenseGrid[row][col] = new JLabel();
				defenseGrid[row][col].setBounds(100 + gridPieceDimension*col, 200 + gridPieceDimension*row, gridPieceDimension, gridPieceDimension);
				defenseGrid[row][col].setIcon(new ImageIcon(boardPiecePNG));
				defenseGrid[row][col].setName("def" + "r" + Integer.toString(row) + "c" + Integer.toString(col) );
				defGridTransferHandler[row][col] = new TransferHandler("icon");
				defenseGrid[row][col].setTransferHandler(defGridTransferHandler[row][col]);//("icon"));
				defenseGrid[row][col].getDropTarget().setActive(true); //Sets above defGridTransferHandler[row][col] to Active
				defenseGrid[row][col].addPropertyChangeListener(gridchangelis);
				
				defenseGrid[row][col].addMouseListener(gridMouseLis.ml);
				
				add(defenseGrid[row][col]);
			}
		}
		
		offenseGrid = new JLabel[10][10];  //Initialize all offensive grid pieces
		for (int row = 0; row < 10; row++)
		{
			for (int col = 0; col < 10; col++)
			{
				offenseGrid[row][col] = new JLabel();
				offenseGrid[row][col].setBounds(1200 + gridPieceDimension*col, 200 + gridPieceDimension*row, gridPieceDimension, gridPieceDimension);
				offenseGrid[row][col].setIcon(new ImageIcon(boardPiecePNG));
				
				//offenseGrid[row][col].addMouseListener(OffenseMouseListen.ml);
				
				add(offenseGrid[row][col]);
			}
		}
		
		gridOccupied = new Boolean[10][10];
		for(int r = 0; r < 10; r++) //Initialize gridOccupied with False
		{
			for(int c = 0; c < 10; c++)
			{
				gridOccupied[r][c] = false;
			}
		}
		gridOccupiedByShip = new String[10][10];
		
		
		
		
	       	textArea = new JTextArea(50, 10);
	        textArea.setEditable(false);
	        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
	         
	        // keeps reference of standard output stream
	        standardOut = System.out;
	         
	        // re-assigns standard output stream and error output stream
	        System.setOut(printStream);
	        
	        textArea.setBounds(700, 800, 500, 150);
	        textArea.setVisible(true);
	        textArea.setEnabled(true);
	        textArea.setFont(new Font("Serif", Font.BOLD, 20));
	        JScrollPane jsp = new JScrollPane(textArea);
	        jsp.setBounds(700, 800, 500, 150);
	        jsp.setVisible(true);
	        jsp.setEnabled(true);
	        add(jsp);
		
		
		

		confirm = new JButton("Confirm");
		confirm.setBounds(700, 150, 100, 50);
		confirm.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(isClient==true)
				{
					System.out.println("Connected! Starting game.");
				}
				if(isServer==true)
				{
					System.out.println("Waiting for opponent!");
				}
				confirmed=true;
				confirm.setEnabled(false);
				automatic.setEnabled(false);
			}
		});
		add(confirm);
		
		automatic = new JButton("Automatic Set Up");
		automatic.setBounds(700, 210, 100, 50);
		automatic.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setup.automatic();
				automatic.setEnabled(false);
				confirm.setEnabled(false);
				confirmed=true;
				blank5.setVisible(true);
				blank4.setVisible(true);
				blank31.setVisible(true);
				blank32.setVisible(true);
				blank2.setVisible(true);
			}
		});
		add(automatic);
		
		infoText = new JTextArea("When placing ships, the furthest left hole of the ship\nwill be at your cursor.");
		infoText.setEditable(false);
		infoText.setFont(new Font("Serif", Font.BOLD, 16));
		infoText.setForeground(Color.WHITE);
		infoText.setBackground(Color.BLACK);
		infoText.setBounds(800, 150, 400, 45);
		add(infoText);
		
		JLabel[] topNumbers1 = new JLabel[10];
		for (int i = 0; i < 10; i++)
		{
			topNumbers1[i] = new JLabel(Integer.toString(i),JLabel.CENTER);
			topNumbers1[i].setHorizontalTextPosition(topNumbers1[i].CENTER);
			topNumbers1[i].setFont(new Font("Serif", Font.BOLD, 50));
			topNumbers1[i].setForeground(Color.WHITE);
			topNumbers1[i].setBounds(100 + gridPieceDimension*i, 200 - gridPieceDimension, gridPieceDimension, gridPieceDimension);
			add(topNumbers1[i]);
		}
		JLabel[] sideLetters1 = new JLabel[10];
		for (int i = 0; i < 10; i++)
		{
			sideLetters1[i] = new JLabel(intToAlpha(i),JLabel.CENTER);
			sideLetters1[i].setHorizontalTextPosition(sideLetters1[i].CENTER);
			sideLetters1[i].setFont(new Font("Serif", Font.BOLD, 50));
			sideLetters1[i].setForeground(Color.WHITE);
			sideLetters1[i].setBounds(100 - gridPieceDimension, 200 + gridPieceDimension*i, gridPieceDimension, gridPieceDimension);
			add(sideLetters1[i]);
		}
		JLabel[] topNumbers2 = new JLabel[10];
		for (int i = 0; i < 10; i++)
		{
			topNumbers2[i] = new JLabel(Integer.toString(i),JLabel.CENTER);
			topNumbers2[i].setHorizontalTextPosition(topNumbers2[i].CENTER);
			topNumbers2[i].setFont(new Font("Serif", Font.BOLD, 50));
			topNumbers2[i].setForeground(Color.WHITE);
			topNumbers2[i].setBounds(1200 + gridPieceDimension*i, 200 - gridPieceDimension, gridPieceDimension, gridPieceDimension);
			add(topNumbers2[i]);
		}
		JLabel[] sideLetters2 = new JLabel[10];
		for (int i = 0; i < 10; i++)
		{
			sideLetters2[i] = new JLabel(intToAlpha(i),JLabel.CENTER);
			sideLetters2[i].setHorizontalTextPosition(sideLetters2[i].CENTER);
			sideLetters2[i].setFont(new Font("Serif", Font.BOLD, 50));
			sideLetters2[i].setForeground(Color.WHITE);
			sideLetters2[i].setBounds(1200 - gridPieceDimension, 200 + gridPieceDimension*i, gridPieceDimension, gridPieceDimension);
			add(sideLetters2[i]);
		}
		JLabel title = new JLabel("Battleship", JLabel.CENTER);
		title.setHorizontalTextPosition(title.CENTER);
		title.setFont(new Font("Serif", Font.BOLD, 50));
		title.setForeground(Color.WHITE);
		title.setBounds(700,20,500,60);
		add(title);
		JLabel credit = new JLabel("Tommy Shay & Eric Gerardi", JLabel.CENTER);
		credit.setHorizontalTextPosition(credit.CENTER);
		credit.setFont(new Font("Serif", Font.BOLD, 20));
		credit.setForeground(Color.WHITE);
		credit.setBounds(700,950,500,60);
		add(credit);
		
		
		
		
		blank5 = new JLabel();
		blank5.setIcon(new ImageIcon("src/images/blackrect.png"));
		blank5.setBounds(800,200,300,60);
		add(blank5,0);
		blank5.setVisible(false);
		blank4 = new JLabel();
		blank4.setIcon(new ImageIcon("src/images/blackrect.png"));
		blank4.setBounds(800,260,300,60);
		add(blank4,0);
		blank4.setVisible(false);
		blank31 = new JLabel();
		blank31.setIcon(new ImageIcon("src/images/blackrect.png"));
		blank31.setBounds(800,320,300,60);
		add(blank31,0);
		blank31.setVisible(false);
		blank32 = new JLabel();
		blank32.setIcon(new ImageIcon("src/images/blackrect.png"));
		blank32.setBounds(800,380,300,60);
		add(blank32,0);
		blank32.setVisible(false);
		blank2 = new JLabel();
		blank2.setIcon(new ImageIcon("src/images/blackrect.png"));
		blank2.setBounds(800,440,300,60);
		add(blank2,0);
		blank2.setVisible(false);
		
		
		
		
		getContentPane().setBackground( Color.BLACK );
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}


	private String intToAlpha(int j) {
		int i = j + 1;
		return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException 
	{
        String clientOrServer = JOptionPane.showInputDialog("Are you the client or server?");
        if(clientOrServer.equals("client"))
        {
        	isClient=true;
        	//String serverAddress = JOptionPane.showInputDialog("Enter the IP of the Server:");
        	gameClient = new client();
        	gameClient.connect();
        	
        	/*while(true)
        	{
        		gameClient.clientShot();
        	}*/

        }
        else if(clientOrServer.equals("server"))
        {
        	isServer=true;
        	gameServer = new server();
        	gameServer.connect();
        }
        else
        {
        	System.out.println("Invalid choice! Exiting program!");
        	return;
        }
	}
	
	public static void addToMessageBox(String s)
	{
		System.out.println(s);
	}
}





