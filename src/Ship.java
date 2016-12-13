import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.TransferHandler;


class Ship extends JLabel{
		private String name;
		private int length;
		private String orientation;
		private boolean sunk;
		private ImageIcon fullIcon;
		private ImageIcon [] hole;
		static private boolean wasPlaced = false; 
		
		private int shipHits=0;
		private int[] rowOfHole;
		private int[] colOfHole;
		private int[] oldRowOfHole;
		private int[] oldColOfHole;
		
		Ship(String shipName, int shipLength)
		{
			name = shipName;
			length = shipLength;
			sunk = false;
			//System.out.println(length);
			orientation = "Horizontal";
			rowOfHole = new int[shipLength];
			colOfHole = new int[shipLength];
			oldRowOfHole = new int[shipLength];
			oldColOfHole = new int[shipLength];
			for(int i = 0; i < length; i++)
			{
				rowOfHole[i] = 100;
				colOfHole[i] = 100;
				oldRowOfHole[i] = 100;
				oldColOfHole[i] = 100;
			}
		}
		
		
		// Getters
		public int getLength()
		{
			return length;
		}
		public String getName()
		{
			return name;
		}
		public ImageIcon getHole(int i) {
			return hole[i];
		}
		public String getOrientation () {
			return orientation;
		}
		public ImageIcon getFullIcon() {
			return fullIcon;
		}
		public int getRowOfHole(int hole) {
			return rowOfHole[hole];
		}
		public int getColOfHole(int hole) {
			return colOfHole[hole];
		}
		public int getOldRowOfHole(int hole) {
			return oldRowOfHole[hole];
		}
		public int getOldColOfHole(int hole) {
			return oldColOfHole[hole];
		}
		public boolean getSunk() {
			return sunk;
		}
		public int getShipHits()
		{
			return shipHits;
		}
		
		public void addHits()
		{
			shipHits++;
		}

		
		// Setters
		public void setHoles(ImageIcon imgIcon[]) {
			hole = new ImageIcon[imgIcon.length];
			for(int i = 0; i < imgIcon.length; i++)
			{
				hole[i] = imgIcon[i];
			}
		}
		public void setOrientation (String orient) {
			orientation = orient;
		}
		public void setFullIcon(ImageIcon imgIcon)
		{
			fullIcon = imgIcon;
		}
		public void setRowOfHole(int row, int hole) {
			oldRowOfHole[hole] = rowOfHole[hole];
			if (rowOfHole[hole] == 100)
			{
				oldRowOfHole[hole] = row;
			}
			rowOfHole[hole] = row;
		}
		public void setColOfHole(int col, int hole) {
			oldColOfHole[hole] = colOfHole[hole];
			if (colOfHole[hole] == 100)
			{
				oldColOfHole[hole] = col;
			}
			colOfHole[hole] = col;
		}
}
