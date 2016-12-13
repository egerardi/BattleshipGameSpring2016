import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class client 
{
	static Socket s;
	static String[] parts;
	static int rowShot =0;
	static int colShot = 0;
	static Ship check;
	static int shipsSunk=0;
	
	public client()
	{
		battleship clientB = new battleship();
		for (int row = 0; row < 10; row++)
		{
			for (int col = 0; col < 10; col++)
			{
				battleship.offenseGrid[row][col].addMouseListener(OffenseMouseListen.ml);
			}
		}
	}
	
	public void connect() throws UnknownHostException, IOException
	{
        String serverAddress = JOptionPane.showInputDialog("Enter IP Address of the server:");
        s = new Socket(serverAddress, 9090);
        
        //keep looping while theres no winner
        while(battleship.winner==false)
        {
        	BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
    		String hitMiss = input.readLine();
    		
    		//if server hit
    		if(hitMiss.equals("hit"))
    		{
    			System.out.println("Client: Hit a Ship!");
    	        battleship.offenseGrid[rowShot][colShot].setIcon(battleship.hitIcon);
    	        try {
    	        	String soundName = "src/sounds/shotgun.wav";    
    	        	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
    	        	Clip clip = AudioSystem.getClip();
    	        	clip.open(audioInputStream);
    	        	clip.start();
    	        }
    	        catch (Throwable uuu) {}

    			offenseListenerOff();
    	        checkShipSunk();
    	        readShot();
    		}
    		//else server missed
    		else
    		{
    			battleship.offenseGrid[rowShot][colShot].setIcon(battleship.missIcon);
    			System.out.println("Client: Missed!");
    			try {
    				String soundName = "src/sounds/cry.wav";    
    				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
    				Clip clip = AudioSystem.getClip();
    				clip.open(audioInputStream);
    				clip.start();
    			}
    			catch (Throwable uuu) {}

    			offenseListenerOff();
    			readShot();
    		}
        }
	} 
	
	//function to send a shot
	public static void shoot(int r, int c) throws IOException
	{	
        PrintWriter clientOut = new PrintWriter(s.getOutputStream(), true);
        clientOut.println(r + " " + c);
	}
	
	//function to turn offense grid listener on
	public static void offenseListenerOn()
	{
		for (int row = 0; row < 10; row++)
		{
			for (int col = 0; col < 10; col++)
			{
				if(battleship.offenseGrid[row][col].getIcon() == battleship.hitIcon || battleship.offenseGrid[row][col].getIcon() == battleship.missIcon)
				{
					battleship.offenseGrid[row][col].removeMouseListener(OffenseMouseListen.ml);
				}
				else
				{
					battleship.offenseGrid[row][col].addMouseListener(OffenseMouseListen.ml);
				}
			}
		}
	}
	
	//function to turn offense grid listener off
	public static void offenseListenerOff()
	{
		for (int row = 0; row < 10; row++)
		{
			for (int col = 0; col < 10; col++)
			{
				battleship.offenseGrid[row][col].removeMouseListener(OffenseMouseListen.ml);
			}
		}
	}
	
	//function to read a shot from server
	public static void readShot() throws IOException
	{
        
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String shotAt = input.readLine();
		parts = shotAt.split(" ");
		
		//if the server hit
		if(battleship.gridOccupied[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])] == true)
		{
			check = returnShip.byShipStringName(battleship.gridOccupiedByShip[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])]);
			check.addHits();
			
			System.out.println("Client: Opponent hit " + check.getName());
			
	        PrintWriter serverOut = new PrintWriter(s.getOutputStream(), true);
	        battleship.defenseGrid[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])].setIcon(battleship.hitIcon);
	        serverOut.println("hit");
	        
	        //if server sunk a ship
	        if(checkIfSunk() == true)
			{
	        	System.out.println("Client: Opponent sunk " + check.getName());
				serverOut.println("sunk a ship");
				input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        		String answer = input.readLine();
        		if(answer.equals("which ship"))
        		{
        			serverOut.println(check.getName());
        		}
        		String winnerOrNah = input.readLine();
        		
        		//if the server sunk 5 ships
        		if(winnerOrNah.equals("serverWon"))
        		{
        			JOptionPane.showMessageDialog(null, "Client: you lose!");
        			System.exit(0);
        		}
			}
	        offenseListenerOn();
		}
		//server shot and missed
		else
		{
			System.out.println("Client: Opponent shot and missed!");
			
	        PrintWriter serverOut = new PrintWriter(s.getOutputStream(), true);
	        battleship.defenseGrid[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])].setIcon(battleship.missIcon);
	        serverOut.println("miss");
	        offenseListenerOn();
		}
	}
	
	//checks if client sunk a ship
	public static void checkShipSunk() throws IOException
	{
		PrintWriter serverOut = new PrintWriter(s.getOutputStream(), true);
		serverOut.println("didISinkIt");
		
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String sunk = input.readLine();
		
		//if a ship HAS been sunk
		if(sunk.equals("sunk a ship"))
		{
			shipsSunk++;
			
			serverOut = new PrintWriter(s.getOutputStream(), true);
			serverOut.println("which ship");
			input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String ship = input.readLine();
			
			battleship.addToMessageBox("Client has sunk " + ship);
			//JOptionPane.showMessageDialog(null, "Client: Youve sunk " + ship);
			
			//if client sunk all 5 ships
			if(shipsSunk==5)
			{
				serverOut.println("clientWon");
				JOptionPane.showMessageDialog(null, "Client: youve won!");
				System.exit(0);
			}
			else
			{
				serverOut.println("noWinner");
			}
		}
	}
	
	//checks if the ship the server hit is sunk
	public static boolean checkIfSunk()
	{
		if(check.getShipHits() == check.getLength())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}