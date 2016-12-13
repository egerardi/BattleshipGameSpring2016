import javax.swing.TransferHandler;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class ShipTransfer extends TransferHandler {

	ShipTransfer() {
		super("icon");
	}

	
	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		
		if (action == MOVE) 
		{ //If moving ship around on board, Erase ship last location
			((JLabel) source).setText("");
			
			
			String gridRowCol = source.getName(); //Get source name, store to later get row and col
			
			if (gridRowCol.length() == 7) //If moving ship around on board
			{
				int r = Character.getNumericValue(gridRowCol.charAt(4)); //Get row of placement
				int c = Character.getNumericValue(gridRowCol.charAt(6)); //Get col of placement
				String currShipName = battleship.gridOccupiedByShip[r][c]; //Get current ship name (currShipName)
				Ship currShip = returnShip.byShipStringName(currShipName); //Use currShipName to get current Ship
				//System.out.println("currShipName: " + currShip.getName() + "---");

				
				for(int i = 0; i < currShip.getLength(); i++) //For each hole of ship (length), 
				{
						int oldRow = currShip.getOldRowOfHole(i);
						int oldCol = currShip.getOldColOfHole(i);

						int newRow = currShip.getRowOfHole(i);
						int newCol = currShip.getColOfHole(i);

						if (oldRow < 100 && oldCol < 100) //If the ship was previously set somewhere on the board
						{ //Erase ship
							battleship.defenseGrid[oldRow][oldCol].setIcon(new ImageIcon(battleship.boardPiecePNG));  // Set hole on grid to boardPiece
							battleship.gridOccupied[oldRow][oldCol] = false; // Update the boolean array gridOccupied
							battleship.gridOccupiedByShip[oldRow][oldCol] = null;						
						}
				}
			}
			
			if (source.getName() == "Ship5")
			{
				battleship.blank5.setVisible(true);
			}
			if (source.getName() == "Ship4")
			{
				battleship.blank4.setVisible(true);
			}
			if (source.getName() == "Ship31")
			{
				battleship.blank31.setVisible(true);
			}
			if (source.getName() == "Ship32")
			{
				battleship.blank32.setVisible(true);
			}
			if (source.getName() == "Ship2")
			{
				battleship.blank2.setVisible(true);
			}
			
			source.setVisible(true);
		}
		
		/*for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
					System.out.print(battleship.gridOccupiedByShip[i][j] + "  ");

			}
			System.out.println();
		}*/
		
	}

	@Override
	public int getSourceActions(JComponent c) {
		return MOVE;
	}
}
