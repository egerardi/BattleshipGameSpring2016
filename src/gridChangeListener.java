import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;

class gridChangeListener implements PropertyChangeListener {
	
	public void propertyChange(PropertyChangeEvent event) {
		
			if (ImgLink.isACompleteShip(String.valueOf(event.getNewValue()))) //First Ship Placement
			{
				String gridRowCol = ((Component) event.getSource()).getName();
				Ship placedShip = ImgLink.whichShipName(String.valueOf(event.getNewValue()));
				
				int r = Character.getNumericValue(gridRowCol.charAt(4)); //Get row of placement
				int c = Character.getNumericValue(gridRowCol.charAt(6)); //Get col of placement
				int shipStartRow;
				int shipStartCol;
				int shipEndRow;
				int shipEndCol;
				int holeIndex = 0;
				
				if (placedShip.getOrientation() == "Horizontal") // Based on ship orientation, set ending row and col of placed ship
				{
					shipStartRow = r;
					shipEndRow = r;
					shipStartCol = c + 1;
					shipEndCol = c + placedShip.getLength() - 1;
				}
				else
				{
					shipEndRow = r + placedShip.getLength() - 1;
					shipStartRow = r + 1;
					shipStartCol = c;
					shipEndCol = c;
				}
				
				
				//Set first hole info on grid
				battleship.defenseGrid[r][c].setTransferHandler(battleship.shipTransferHandler);
				battleship.defenseGrid[r][c].setIcon(placedShip.getFullIcon());
				battleship.defenseGrid[r][c].addMouseMotionListener(MouseMotion.mml);
				battleship.defenseGrid[r][c].addMouseListener(ShipMouseListen.ml);
				battleship.gridOccupied[r][c] = true;
				battleship.gridOccupiedByShip[r][c] = placedShip.getName();
				placedShip.setRowOfHole(r, 0);
				placedShip.setColOfHole(c, 0);
				
				//Set hole info on grid
				holeIndex++;
				
					for (int row = shipStartRow; row <= shipEndRow; row++)
					{
						for (int col = shipStartCol; col <= shipEndCol; col++)
						{
								battleship.defenseGrid[row][col].setIcon(placedShip.getHole(holeIndex));  // Set hole on grid
								battleship.gridOccupied[row][col] = true; // Update the boolean array gridOccupied
								battleship.gridOccupiedByShip[row][col] = placedShip.getName();
								placedShip.setRowOfHole(row, holeIndex);
								placedShip.setColOfHole(col, holeIndex);
								battleship.defenseGrid[row][col].addMouseMotionListener(MouseMotion.mml);
								//battleship.defenseGrid[row][col].addMouseListener(DefenseMouseListen.ml);
								//battleship.defenseGrid[row][col].setTransferHandler(battleship.shipTransferHandler); //Set the defenseGrid[r][c] to have the shipTransferHandler
								holeIndex++;
						}
					}	
					
			}
	}

}