
public class gridCheck {

	public static HoldRowAndCol translateGridPiece(int row, int col, String orientation, int hole) {
		// Return currRowAndCol (holds row and col) for assisting the ship to rotate
		
		HoldRowAndCol currRowAndCol = new HoldRowAndCol();
		
		if (orientation == "Horizontal")
		{
			currRowAndCol.setRow(row + hole);
			currRowAndCol.setCol(col - hole);
		}
		else
		{
			currRowAndCol.setRow(row - hole);
			currRowAndCol.setCol(col + hole);
		}
		
		return currRowAndCol;		
	}
}
