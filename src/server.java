import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;


public class server 
{
	ServerSocket listener;
	static Socket socket;
	static int rowShot = 0;
	static int colShot = 0;
	static String sunkShip;
    Ship check;
    static int shipsSunk=0;
    
	public server()
	{
		battleship serverB = new battleship();
	}
	
	public void connect() throws IOException
	{
        listener = new ServerSocket(9090);
        
        try 
        {
            while (true) 
            {
                socket = listener.accept();
                try 
                {
                	//loop while theres no winner
                	while(battleship.winner==false)
                	{
                		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                		
                		//reads in shot from client
                		String shotAt = input.readLine();
                		String[] parts = shotAt.split(" ");
                		
                		//if the grid occupied where the client shot
                		if(battleship.gridOccupied[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])] == true)
                		{
                			//send hit
                	        PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
                	        serverOut.println("hit");
                	        
                	        //add a hit to the ship that was hit
        					check = returnShip.byShipStringName(battleship.gridOccupiedByShip[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])]);
        					check.addHits();
        					
        					//display which ship opponent hit
        					System.out.println("Server: Opponent hit " + check.getName());
        					
    						String isItSunk = input.readLine();
        					
    						//if the ship has been sunk
        					if(checkIfSunk() == true)
        					{
        						System.out.println("Server: Opponent sunk " + check.getName());
        						serverOut.println("sunk a ship");
        						input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        		String answer = input.readLine();
                        		if(answer.equals("which ship"))
                        		{
                        			serverOut.println(check.getName());
                        		}
                        		String winnerOrNah = input.readLine();
                        		
                        		//if all 5 ships have been sunk
                        		if(winnerOrNah.equals("clientWon"))
                        		{
                        			JOptionPane.showMessageDialog(null, "Server: you lose!");
                        			System.exit(0);
                        		}
        					}
                	        
                	        battleship.defenseGrid[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])].setIcon(battleship.hitIcon); //Set 
                	        offenseListenerOn();
                	        
                	        //servers shot
                	        String hitMiss = input.readLine();
                	        
                	        //if server hit
                	        if(hitMiss.equals("hit"))
                	        {
                	        	System.out.println("Server: Hit a Ship!");
                    	        try {
                    	        	String soundName = "src/sounds/shotgun.wav";    
                    	        	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                    	        	Clip clip = AudioSystem.getClip();
                    	        	clip.open(audioInputStream);
                    	        	clip.start();
                    	        }
                    	        catch (Throwable uuu) {}
                	        	battleship.offenseGrid[rowShot][colShot].setIcon(battleship.hitIcon);
                	        	offenseListenerOff();
                	        	didISink();
                	        }
                	        //else server missed
                	        else
                	        {
                	        	offenseListenerOff();
                	        	System.out.println("Server: Missed!");
                    			try {
                    				String soundName = "src/sounds/cry.wav";    
                    				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                    				Clip clip = AudioSystem.getClip();
                    				clip.open(audioInputStream);
                    				clip.start();
                    			}
                    			catch (Throwable uuu) {}
                	        	battleship.offenseGrid[rowShot][colShot].setIcon(battleship.missIcon);
                	        }
                		}
                		//else client missed
                		else
                		{
                			System.out.println("Server: Opponent shot and missed!");
                			
                	        PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
                	        serverOut.println("miss");
                	        battleship.defenseGrid[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])].setIcon(battleship.missIcon);
                	        offenseListenerOn();
                	        
                	        //server shot
                	        String hitMiss = input.readLine();
                	        
                	        //if server hits
                	        if(hitMiss.equals("hit"))
                	        {
                	        	System.out.println("Server: Hit a Ship!");
                    	        try {
                    	        	String soundName = "src/sounds/shotgun.wav";    
                    	        	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                    	        	Clip clip = AudioSystem.getClip();
                    	        	clip.open(audioInputStream);
                    	        	clip.start();
                    	        }
                    	        catch (Throwable uuu) {}
                	        	battleship.offenseGrid[rowShot][colShot].setIcon(battleship.hitIcon);
                	        	offenseListenerOff();
                	        	didISink();
                	        }
                	        //else server missed
                	        else
                	        {
                	        	offenseListenerOff();
                	        	System.out.println("Server: Missed!");
                    			try {
                    				String soundName = "src/sounds/cry.wav";    
                    				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                    				Clip clip = AudioSystem.getClip();
                    				clip.open(audioInputStream);
                    				clip.start();
                    			}
                    			catch (Throwable uuu) {}
                	        	battleship.offenseGrid[rowShot][colShot].setIcon(battleship.missIcon);
                	        }
                		}
                	}
                } 
                //close the socket
                finally 
                {
                    socket.close();
                }
            }
        }
        //close the listener
        finally 
        {
            listener.close();
        }
	}
	
	//function used to send a shot
	public static void shoot(int r, int c) throws IOException
	{
        PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
        serverOut.println(r + " " + c);
	}
	
	//function to turn on the offense grid listener
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
	
	//function to turn off the offense grid listener
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
	
	//function to check if a ship has been sunk
	public boolean checkIfSunk()
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
	
	//checks if server sunk a ship
	public void didISink() throws IOException
	{
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String sunk = input.readLine();
		
		if(sunk.equals("sunk a ship"))
		{
			shipsSunk++;
			
			PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
			serverOut.println("which ship");
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String ship = input.readLine();
			
			System.out.println("Server: Sunk " + ship);
			//JOptionPane.showMessageDialog(null, "Server: Youve sunk " + ship);
			
			if(shipsSunk==5)
			{
				serverOut.println("serverWon");
				JOptionPane.showMessageDialog(null, "Server: youve won!");
				System.exit(0);
			}
			else
			{
				serverOut.println("noWinner");
			}
		}
	}
}