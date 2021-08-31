import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
/**
*	Klasa Polygon odpowiadajaca za rysowanie i modyfikowanie trojkatow	
*	@see Shape
*	@see Oval
*	@see Rectangle
*/
public class Polygon extends Shape
{
	private static final long serialVersionUID = 1L;
	/**
	*	Konstruktor odpowiedzialny za utworzenie
	*	obiektu typu Shape dla klas Polygon
	*
	* 	@param coordy_x wspolrzedne x-owe wierzcholkow figury
	*	@param coordy_y wspolrzedne y-kowe wierzcholkow figury
	*	@param tab_length liczba wierzcholkow (tutaj stale 3)
	*	@param c kolor figury
	* 	@param filling przyjmuje 2 wartosci, "border"- rysuje wylacznie obramowke, "fill"- wypelnia figure zadanym kolorem
	*/
	public Polygon(int []coordy_x,int []coordy_y,int tab_length, Color c,String filling) 
	{
        	super(coordy_x,coordy_y,tab_length, c, filling);
    	}
	/**
	*	Metoda odpowiadaja za rysowanie figury na panelu
	*	@param g2d obiekt typu Graphics2D, ktory ma zostac narysowany
	*
	*/
    	@Override
    	public void paint(Graphics2D g2d) 
	{
        	if(getColor()==Color.WHITE)
		{
			g2d.setColor(Color.BLACK);
			filling="border";
		}
		else
		{
        		g2d.setColor(getColor());
		}
		if(filling.equals("border"))
		{
			g2d.drawPolygon(coordy_x,coordy_y,tab_length);
		}
		else if(filling.equals("fill"))
		{
			g2d.fillPolygon(coordy_x,coordy_y,tab_length);
		}
    	}
	/**
	*	Metoda odpowiadaja za sprawdzenie, czy punkt znajduje sie w figurze
	*	@param point punkt, dla ktorego ma sie odbyc sprawdzenie
	*
	*/
	@Override
	boolean contains(Point point)
	{	
		int point_x = point.x-getX0();
		int point_y = point.y-getY0();
		boolean point_ab = (getX1()-getX0())*point_y-(getY1()-getY0())*point_x > 0;
		if((getX2()-getX0())*point_y-(getY2()-getY0())*point_x > 0 == point_ab) 
		{
			return false;
		}
		if((getX2()-getX1())*(point.y-getY1())-(getY2()-getY1())*(point.x-getX1()) > 0 != point_ab)
		{
			return false;
		}
		return true;
	}
}
