import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.TransferHandler;


public class MouseMotion {
	static MouseMotionListener mml = new MouseMotionListener()
    {
    	@Override
    	public void mouseDragged(MouseEvent e) 
    	{
    		if(battleship.confirmed==false)
    		{
    			JComponent jc = (JComponent) e.getSource();
    			TransferHandler th = jc.getTransferHandler();
    			th.exportAsDrag(jc, e, TransferHandler.MOVE);
    			battleship.isPressed = true;
    		}

    	}
	
    	
    	@Override
    	public void mouseMoved(MouseEvent e) 
    	{
    		// TODO Auto-generated method stub
    		
    	}
    
    };
}
