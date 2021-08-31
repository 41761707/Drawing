import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
/**
*	Klasa Oval odpowiadajaca za rysowanie i modyfikowanie okregow	
*	@see Shape
*	@see Rectangle
*	@see Polygon
*/
public class Oval extends Shape 
{
	private static final long serialVersionUID = 1L;
	/**
	*	Konstruktor odpowiedzialny za utworzenie
	*	obiektu typu Oval
	*
	* 	@param x wspolrzedna x-owa figury
	*	@param y wspolrzedna y-kowa figury
	*	@param width szerokosc figury
	* 	@param height wysokosc figury
	*	@param c kolor figury
	* 	@param filling przyjmuje 2 wartosci, "border"- rysuje wylacznie obramowke, "fill"- wypelnia figure zadanym kolorem
	*/
    	public Oval(int x, int y, int width, int height, Color c,String filling) 
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
        		g2d.drawOval(getX(), getY(), getWidth(), getHeight());
		}
		else if(filling.equals("fill"))
		{
			g2d.fillOval(getX(), getY(), getWidth(), getHeight());
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
		/** sprawdzamy czy dany punkt lezy w mniejszej odleglosci od srodka okregu niz promien*/
		int radius = getHeight()/2;
                int centerX = getX() + radius;
                int centerY = getY() + radius;
                int distance = (int)Math.sqrt((Math.pow(point.x - centerX, 2)) + (Math.pow(point.y - centerY, 2)));
                if (distance > radius)
                {
                        return false;
                } 
                return true;
	}
}
