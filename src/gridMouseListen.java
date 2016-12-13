import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;


public class gridMouseListen {
	static MouseListener ml = new MouseListener()
    {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == 3 && battleship.confirmed == false) //Let's Rotate
			{
				int currRow;
				int currCol;
				boolean allowShipToRotate = true;
				HoldRowAndCol[] currRowAndCol;
				Ship rightClickedShip;
				
				for (int row = 0; row < 10; row++)
				{
					for (int col = 0; col < 10; col++)
					{
			            if (e.getSource() == battleship.defenseGrid[row][col]) //Find source row and col
			            {
			            	currRow = row;
			            	currCol = col;
			            	if(String.valueOf(battleship.defenseGrid[currRow][currCol].getIcon()) != "src/images/boardpiece.png") //If source is not board piece
			            	{
			            		rightClickedShip = ImgLink.whichShipName(String.valueOf(battleship.defenseGrid[currRow][currCol].getIcon()));
			            		
			            		//Get Row and Col of all holes of ship
			            		int[] rowOfHole = new int[rightClickedShip.getLength()];
			            		int[] colOfHole = new int[rightClickedShip.getLength()];
			            		for (int i = 0; i < rightClickedShip.getLength(); i++)
			            		{
			            			rowOfHole[i] = rightClickedShip.getRowOfHole(i);
			            			colOfHole[i] = rightClickedShip.getColOfHole(i);
			            		}
			            		
			            		
			            		currRowAndCol = new HoldRowAndCol[rightClickedShip.getLength()];
			            		for (int holeIndex = 1; holeIndex < rightClickedShip.getLength(); holeIndex++) //Set allowShipToRotate = false if it is not allowed to rotate
			            		{
			            			currRowAndCol[holeIndex] = gridCheck.translateGridPiece(rowOfHole[holeIndex], colOfHole[holeIndex], rightClickedShip.getOrientation(), holeIndex);
			            		//	System.out.println("currRowAndCol[" + holeIndex + "].row: " + currRowAndCol[holeIndex].getRow() + "      currRowAndCol[" + holeIndex + "].col: " + currRowAndCol[holeIndex].getCol());
			            			
			            			if (currRowAndCol[holeIndex].getRow() >= 0 && currRowAndCol[holeIndex].getRow() <= 9 && currRowAndCol[holeIndex].getCol() >= 0 && currRowAndCol[holeIndex].getCol() <= 9) // If row or col in bounds
			            			{
				            			if (battleship.gridOccupied[currRowAndCol[holeIndex].getRow()][currRowAndCol[holeIndex].getCol()] == true) // If grid piece is occupied
				            			{
				            				allowShipToRotate = false;
				            			}
			            			}	
			            			else // If row or col out of bounds
			            			{
			            				allowShipToRotate = false;
			            			}
			            		}
			            		
			            		if (allowShipToRotate == true)
			            		{
			            			//Erase old spots
			            			for (int r = 0; r < 10; r++)
			        				{
			        					for (int c = 0; c < 10; c++)
			        					{
			        						if (battleship.gridOccupiedByShip[r][c] == rightClickedShip.getName())
			        						{
			            						if (!(rightClickedShip.getRowOfHole(0) == r && rightClickedShip.getColOfHole(0) == c))
			            						{
												battleship.defenseGrid[r][c].setIcon(new ImageIcon(battleship.boardPiecePNG));  // Set hole on grid to boardPiece
												battleship.gridOccupied[r][c] = false; // Update the boolean array gridOccupied
												battleship.gridOccupiedByShip[r][c] = null;
			            						}
			        						}
			        					}
			        				}
			            			
			            			//Rotate
			            			System.out.println("Rotated " + rightClickedShip.getName());
			            			if (rightClickedShip.getOrientation() == "Horizontal")
			            			{
			            				rightClickedShip.setOrientation("Vertical");
			            			}
			            			else
			            			{
			            				rightClickedShip.setOrientation("Horizontal");
			            			}
			            			for (int holeIndex = 1; holeIndex < rightClickedShip.getLength(); holeIndex++)
			            			{	//System.out.println("currRowAndCol[" + holeIndex + "].row: " + currRowAndCol[holeIndex].getRow() + "      currRowAndCol[" + holeIndex + "].col: " + currRowAndCol[holeIndex].getCol());
			            			    battleship.defenseGrid[currRowAndCol[holeIndex].getRow()][currRowAndCol[holeIndex].getCol()].setIcon(rightClickedShip.getHole(holeIndex));  // Set hole on grid
										battleship.gridOccupied[currRowAndCol[holeIndex].getRow()][currRowAndCol[holeIndex].getCol()] = true; // Update the boolean array gridOccupied
										battleship.gridOccupiedByShip[currRowAndCol[holeIndex].getRow()][currRowAndCol[holeIndex].getCol()] = rightClickedShip.getName();
										rightClickedShip.setRowOfHole(currRowAndCol[holeIndex].getRow(), holeIndex);
										rightClickedShip.setColOfHole(currRowAndCol[holeIndex].getCol(), holeIndex);
										battleship.defenseGrid[currRowAndCol[holeIndex].getRow()][currRowAndCol[holeIndex].getCol()].addMouseMotionListener(MouseMotion.mml);
			            			}
			            		}
			            	}
			            	
			            	return;
			            }
					} 		
			         
				}  
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
    };
}
