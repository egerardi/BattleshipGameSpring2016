
public class ImgLink {
	public static Ship whichShipName(String strFilePath) {
		if (strFilePath == battleship.ship5Link)
		{
			return battleship.Ship5;
		}
		if (strFilePath == battleship.ship4Link)
		{
			return battleship.Ship4;
		}
		if (strFilePath == battleship.ship31Link)
		{
			return battleship.Ship31;
		}
		if (strFilePath == battleship.ship32Link)
		{
			return battleship.Ship32;
		}
		if (strFilePath == battleship.ship2Link)
		{
			return battleship.Ship2;
		}
		
		
		for(int i = 0; i < battleship.ship5IndividualLinks.length; i++)
		{
			if (strFilePath == battleship.ship5IndividualLinks[i])
			{
				return battleship.Ship5;
			}
		}
		for(int i = 0; i < battleship.ship4IndividualLinks.length; i++)
		{
			if (strFilePath == battleship.ship4IndividualLinks[i])
			{
				return battleship.Ship4;
			}
		}
		for(int i = 0; i < battleship.ship31IndividualLinks.length; i++)
		{
			if (strFilePath == battleship.ship31IndividualLinks[i])
			{
				return battleship.Ship31;
			}
		}
		for(int i = 0; i < battleship.ship32IndividualLinks.length; i++)
		{
			if (strFilePath == battleship.ship32IndividualLinks[i])
			{
				return battleship.Ship32;
			}
		}
		for(int i = 0; i < battleship.ship2IndividualLinks.length; i++)
		{
			if (strFilePath == battleship.ship2IndividualLinks[i])
			{
				return battleship.Ship2;
			}
		}
	
		Ship ShipEmpty = new Ship("Empty", 0);
		return ShipEmpty;
	}
	
	public static boolean isACompleteShip(String strFilePath) {
		if (strFilePath == battleship.ship5Link ||
				strFilePath == battleship.ship4Link ||
				strFilePath == battleship.ship31Link ||
				strFilePath == battleship.ship32Link ||
				strFilePath == battleship.ship2Link)
		{
			return true;
		}
		return false;
	}
	
	
	public static boolean isAShipHole(String strFilePath) {
		for(int i = 0; i < battleship.ship5IndividualLinks.length; i++)
		{
			if (strFilePath == battleship.ship5IndividualLinks[i])
			{
				return true;
			}
		}
		for(int i = 0; i < battleship.ship4IndividualLinks.length; i++)
		{
			if (strFilePath == battleship.ship4IndividualLinks[i])
			{
				return true;
			}
		}
		for(int i = 0; i < battleship.ship31IndividualLinks.length; i++)
		{
			if (strFilePath == battleship.ship31IndividualLinks[i])
			{
				return true;
			}
		}
		for(int i = 0; i < battleship.ship32IndividualLinks.length; i++)
		{
			if (strFilePath == battleship.ship32IndividualLinks[i])
			{
				return true;
			}
		}
		for(int i = 0; i < battleship.ship2IndividualLinks.length; i++)
		{
			if (strFilePath == battleship.ship2IndividualLinks[i])
			{
				return true;
			}
		}
	
		return false;
	}
}
