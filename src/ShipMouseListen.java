import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ShipMouseListen {
	static MouseListener ml = new MouseListener()
    {

		@Override
		public void mouseClicked(MouseEvent e) 
		{
			if(e.getButton() == 3 && battleship.confirmed == false)
			{
				Ship rightClickedShip;
				
				try { //Try to get pressedShip directly from source (if full ship)
					rightClickedShip = (Ship) e.getSource();
				}
				catch (Throwable t) { //Else get pressedShip from battleship.gridOccupiedByShip[][]
					String gridRowCol = ((Component)e.getSource()).getName();
					int r = Character.getNumericValue(gridRowCol.charAt(4)); //Get row of placement
					int c = Character.getNumericValue(gridRowCol.charAt(6)); //Get col of placement
					rightClickedShip = returnShip.byShipStringName(battleship.gridOccupiedByShip[r][c]);
				}
				
				
				
				//If the source is the full ship (not placed on the board), then change orientation
				try {
					if (ImgLink.isACompleteShip(String.valueOf(((Ship) e.getSource()).getFullIcon())))
					{
						String orientation = rightClickedShip.getOrientation();
						if (orientation == "Horizontal")
						{
							rightClickedShip.setOrientation("Vertical");
						}
						else
						{
							rightClickedShip.setOrientation("Horizontal");
						}
					}
				}
				catch (Throwable t) { }
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			for (int row = 0; row <= 9; row++) // After ship drop and exit, turn setActive back on defenseGrid unless occupied
			{
				for (int col = 0; col <= 9; col++)
				{
					if (battleship.gridOccupied[row][col] == true)
					{
						battleship.defenseGrid[row][col].getDropTarget().setActive(false);
					}
					else 
					{
						battleship.defenseGrid[row][col].getDropTarget().setActive(true);
					}
				}
			}
			
//			battleship.Ship5.getDropTarget().setActive(true);
//			battleship.Ship4.getDropTarget().setActive(true);
//			battleship.Ship31.getDropTarget().setActive(true);
//			battleship.Ship32.getDropTarget().setActive(true);
//			battleship.Ship2.getDropTarget().setActive(true);
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Ship pressedShip;
			
			try { //Try to get pressedShip directly from source (if full ship)
				pressedShip = (Ship) e.getSource();
			}
			catch (Throwable t) { //Else get pressedShip from battleship.gridOccupiedByShip[][]
				String gridRowCol = ((Component)e.getSource()).getName();
				int r = Character.getNumericValue(gridRowCol.charAt(4)); //Get row of placement
				int c = Character.getNumericValue(gridRowCol.charAt(6)); //Get col of placement
				pressedShip = returnShip.byShipStringName(battleship.gridOccupiedByShip[r][c]);
			}
			
			
			String currOrientation = pressedShip.getOrientation();
			int currLength = pressedShip.getLength();
			
			
			//Prevent out of bounds (out right, out bottom)
			int shipStartRow;
			int shipEndRow;
			int shipStartCol;
			int shipEndCol;
			if (currOrientation == "Horizontal")
			{
				shipStartRow = 0;
				shipEndRow = 9;
				shipStartCol = 9 - pressedShip.getLength() + 2;
				shipEndCol = 9;
			}
			else
			{
				shipStartRow = 9 - pressedShip.getLength() + 2;
				shipEndRow = 9;
				shipStartCol = 0;
				shipEndCol = 9;
			}
			for (int row = shipStartRow; row <= shipEndRow; row++) 
			{
				for (int col = shipStartCol; col <= shipEndCol; col++)
				{
					battleship.defenseGrid[row][col].getDropTarget().setActive(false);
				}
			}
			
			
			
			//Prevent overlap
			for (int row = 0; row <= 9; row++) 
			{
				for (int col = 0; col <= 9; col++)
				{
					if(battleship.gridOccupied[row][col] == true)
					{
						battleship.defenseGrid[row][col].getDropTarget().setActive(false);
						
						if (currOrientation == "Horizontal")
						{
							int currLeng = currLength - 1;
							while (currLeng > 0)
							{
								if(col - currLeng >= 0)
								{
									battleship.defenseGrid[row][col - currLeng].getDropTarget().setActive(false);
								}
								currLeng--;
							}
						}
						else
						{
							int currLeng = currLength - 1;
							while (currLeng > 0)
							{
								if(row - currLeng >= 0)
								{
									battleship.defenseGrid[row - currLeng][col].getDropTarget().setActive(false);
								}	
								currLeng--;
							}
						}
						
					}
				}
			}
			
			
			
			//Prevent dropping on ship (off board)
			battleship.Ship5.getDropTarget().setActive(false);
			battleship.Ship4.getDropTarget().setActive(false);
			battleship.Ship31.getDropTarget().setActive(false);
			battleship.Ship32.getDropTarget().setActive(false);
			battleship.Ship2.getDropTarget().setActive(false);
		}

		@Override
		public void mouseReleased(MouseEvent e) {	
		}
    	
    };
}
