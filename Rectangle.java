import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
/**
*	Klasa Rectangle odpowiadajaca za rysowanie i modyfikowanie okregow	
*	@see Shape
*	@see Oval
*	@see Polygon
*/
public class Rectangle extends Shape 
{
	private static final long serialVersionUID = 1L;
	/**
	*	Konstruktor odpowiedzialny za utworzenie
	*	obiektu typu Rectangle
	*
	* 	@param x wspolrzedna x-owa figury
	*	@param y wspolrzedna y-kowa figury
	*	@param width szerokosc figury
	* 	@param height wysokosc figury
	*	@param c kolor figury
	* 	@param filling przyjmuje 2 wartosci, "border"- rysuje wylacznie obramowke, "fill"- wypelnia figure zadanym kolorem
	*
	*/
    	public Rectangle(int x, int y, int width, int height, Color c,String filling) 
	{
        	super(x, y, width, height, c,filling);
   	}
	/**
	*	Metoda odpowiadaja za rysowanie figury na panelu
	*	@param g2d obiekt typu Graphics2D, ktory ma zostac narysowany
	*
	*/
    	@Override
    	public void paint(Graphics2D g2d)
	{
		g2d.setColor(getColor());
		/** Jesli kolor figury ulegnie zmiany na bialy to ustalam kolor czarny i filling "border" aby wrocic do poczatkowego stanu */
   		if(getColor()==Color.WHITE)
		{
			g2d.setColor(Color.BLACK);
			filling="border";
		}
		if(filling.equals("border"))
		{
        		g2d.drawRect(getX(), getY(), getWidth(), getHeight());
		}
		else if(filling.equals("fill"))
		{
			g2d.fillRect(getX(), getY(), getWidth(), getHeight());
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
		/** Sprawdzamy czy dany punkt lezy miedzy wierzcholkami prostokata*/
		if((point.getX()>getX() && point.getX()<(getX()+getWidth())) && (point.getY()>getY() && point.getY()<(getY()+getHeight())))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
