import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;


public class OffenseMouseListen {
	static MouseListener ml = new MouseListener()
    {

		@Override
		public void mouseClicked(MouseEvent e) 
		{
			
			if(e.getButton() == 1 && battleship.confirmed == true)
			{
				for (int row = 0; row < 10; row++)
				{
					for (int col = 0; col < 10; col++)
					{
			            if (e.getSource() == battleship.offenseGrid[row][col])
			            {
							try 
							{
								if(battleship.isClient==true)
								{
									client.rowShot=row;
									client.colShot=col;
									client.shoot(row, col);
								}
								else
								{
									server.rowShot=row;
									server.colShot=col;
									server.shoot(row,col);
								}
										
							} 
							catch (IOException e1) 
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
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