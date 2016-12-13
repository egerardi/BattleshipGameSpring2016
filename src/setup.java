import java.util.Random;


public class setup {
	
	public static void automatic () {
		Random rand = new Random();
		int randRow;
		int randCol;
		int randOrientation;
		Ship[] shipArray = {battleship.Ship5, battleship.Ship4, battleship.Ship31, battleship.Ship32, battleship.Ship2};
		boolean inBounds;
		boolean noOverLap;
		String[][] names = new String [10][10];
		
		
		for (int i = 0; i < shipArray.length; i++)
		{
			inBounds = false;
			noOverLap = true;
			
			do {
				do {
					randRow = 0 + rand.nextInt(10 - 0);
					randCol = 0 + rand.nextInt(10 - 0);
					randOrientation = 0 + rand.nextInt(2 - 0);
					
					if (randOrientation == 0) // Horizontal
					{
						if ((randCol + shipArray[i].getLength()) < 10)
						{
							inBounds = true;
						}
					}
					else
					{
						if ((randRow + shipArray[i].getLength()) < 10)
						{
							inBounds = true;
						}
					}
				} while (inBounds == false);
			
				

					for (int index = 0; index < shipArray[i].getLength(); index++)
					{
						if (randOrientation == 0) // Horizontal
						{
							//System.out.println(randRow + "  " + (randCol + index) + "  " + i);
							if (randCol + index < 10)
							{
								if (names[randRow][randCol + index] == null)
								{
									noOverLap = true;
								}
								else
								{
									noOverLap = false;
								}
							}
							else
							{
								noOverLap = false;
							}
						}
						else
						{
							//System.out.println((randRow + index) + "  " + randCol + "  " + i);
							if (randRow + index < 10)
							{
								if (names[randRow + index][randCol] == null)
								{
									noOverLap = true;
								}
								else
								{
									noOverLap = false;
								}
							}
							else
							{
								noOverLap = false;
							}
						}
						
						if (noOverLap == false)
						{
							break;
						}
					}
			} while (noOverLap == false);
			
			
			
			for (int holeIndex = 0; holeIndex < shipArray[i].getLength(); holeIndex++)
			{
				if (randOrientation == 0) // Horizontal
				{
					names[randRow][randCol + holeIndex] = shipArray[i].getName();
					
					battleship.defenseGrid[randRow][randCol + holeIndex].setIcon(shipArray[i].getHole(holeIndex));  // Set hole on grid
					battleship.gridOccupied[randRow][randCol + holeIndex] = true; // Update the boolean array gridOccupied
					battleship.gridOccupiedByShip[randRow][randCol + holeIndex] = shipArray[i].getName();
					shipArray[i].setRowOfHole(randRow, holeIndex);
					shipArray[i].setColOfHole(randCol + holeIndex, holeIndex);					
				}
				else
				{
					names[randRow + holeIndex][randCol] = shipArray[i].getName();
					
					battleship.defenseGrid[randRow + holeIndex][randCol].setIcon(shipArray[i].getHole(holeIndex));  // Set hole on grid
					battleship.gridOccupied[randRow + holeIndex][randCol] = true; // Update the boolean array gridOccupied
					battleship.gridOccupiedByShip[randRow + holeIndex][randCol] = shipArray[i].getName();
					shipArray[i].setRowOfHole(randRow + holeIndex, holeIndex);
					shipArray[i].setColOfHole(randCol, holeIndex);	
				}
			}
			
		}

		
		
//		for (int row = 0; row < 10; row++) 
//		{
//			for (int col = 0; col < 10; col++)
//			{
//				System.out.print(names[row][col] + "  ");
//			}
//			System.out.println();
//		}
	}
}
