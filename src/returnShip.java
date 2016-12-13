
public class returnShip {
	public static Ship byShipStringName (String strName) {
		if (strName == "Ship5")
		{
			return battleship.Ship5;
		}
		if (strName == "Ship4")
		{
			return battleship.Ship4;
		}
		if (strName == "Ship31")
		{
			return battleship.Ship31;
		}
		if (strName == "Ship32")
		{
			return battleship.Ship32;
		}
		if (strName == "Ship2")
		{
			return battleship.Ship2;
		}
		
		Ship ShipEmpty = new Ship("Empty", 0);
		return ShipEmpty;
	}
}
