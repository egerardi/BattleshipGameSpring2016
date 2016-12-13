import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;



public class paintOnDrag {
	String ship5Link = "src/images/5.png";
	Image ship5Image = Toolkit.getDefaultToolkit().getImage(battleship.class.getResource(ship5Link));;
	
	
	public void paint(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;

	    g2.drawImage(ship5Image, 300, 60, battleship.b);
	}
}
