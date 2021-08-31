
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.io.Serializable;
/**
*	Abstrakcyjna klasa Shape jest odpowiedzialna za implementowanie figur
*	
*
*/
public abstract class Shape implements Serializable
{
	private static final long serialVersionUID = 1L;
	/** odpowiednio zmienna x-owa i y-kowa zadanej figury z klasy Rectangle lub Oval*/
	private int x, y;
	/** odpowiednio szerokosc i wysokosc zadanej figury z klasy Rectangle lub Oval*/
	private int width, height;
	/** odpowiada za kolor figury*/
	private Color c;
	/** odpowiada za wypelnienie figury, "border"- sama ramka, "fill"- wypelnienie kolorem*/
	public String filling="border";
	/** odpowiednio zmienne x-owe i y-kowe figury z klasy Polygon*/
	public int[] coordy_x={0,0,0};
	public int[] coordy_y={0,0,0};
	/** liczba wierzcholkow figury z klasy Polygon */
	public int tab_length=0;
	/**
	*	Konstruktor odpowiedzialny za utworzenie
	*	obiektu typu Shape dla klas Rectangle oraz Oval
	*
	* 	@param x wspolrzedna x-owa figury
	*	@param y wspolrzedna y-kowa figury
	*	@param width szerokosc figury
	* 	@param height wysokosc figury
	*	@param c kolor figury
	* 	@param filling przyjmuje 2 wartosci, "border"- rysuje wylacznie obramowke, "fill"- wypelnia figure zadanym kolorem
	*/
    	public Shape(int x, int y, int width, int height, Color c, String filling) 
	{
		this.x = x;
		this.y = y;
		this.c = c;
		this.width = width;
		this.height = height;
		this.c=c;
		this.filling=filling;
    	}
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
	public Shape(int []coordy_x,int []coordy_y,int tab_length, Color c, String filling)
	{
		for(int i=0;i<3;i++)
		{
			this.coordy_x[i]=coordy_x[i];
			this.coordy_y[i]=coordy_y[i];
		}
		this.tab_length=tab_length;
		this.c=c;
		this.filling=filling;
   	}
	abstract boolean contains(Point point);
	/**
	*	Metoda odpowiedzialna za zmiane wspolrzednej x-owej figury dla klas Rectangle i Oval.
	* 	@param new_x nowa wspolrzedna x
	*/
	public void setX(int new_x)
	{
		x=new_x;
	}
	/**
	*	Metoda odpowiedzialna za zmiane wspolrzednej y-kowej figury dla klas Rectangle i Oval.
	* 	@param new_y nowa wspolrzedna y
	*/
	public void setY(int new_y)
	{
		y=new_y;
	}
	/**
	*	Metoda odpowiedzialna za zmiane szerokosci figury dla klas Rectangle i Oval.
	* 	@param new_width nowa szerokosc
	*/
	public void setWidth(int new_width)
	{
		width=new_width;
	}
	/**
	*	Metoda odpowiedzialna za zmiane wysokosci figury dla klas Rectangle i Oval.
	* 	@param new_height nowa wysokosc
	*/
	public void setHeight(int new_height)
	{
		height=new_height;
	}
	/**
	*	Metoda odpowiedzialna za zmiane koloru figury.
	* 	@param color nowy kolor
	*/
	public void setColor(Color color)
	{
		c=color;
	}
	/**
	*	Metoda odpowiedzialna za zmiane wypelnienia figury.
	* 	@param f nowy tryb wypelniania ("border" lub "fill")
	*/
	public void setFilling(String f)
	{
		filling=f;
	}
	/**
	*	Metoda odpowiedzialna za pobranie wypelnienia figury. 
	* 	@return zwraca aktualny tryb wypelniania ("border" lub "fill")
	*/
	public String getFilling()
	{
		return filling;
	}
	/**
	*	Metoda odpowiedzialna za pobranie wysokosci figury. 
	* 	@return zwraca aktualna wysokosc figury
	*/
    	public int getHeight() 
	{
        	return height;
    	}
	/**
	*	Metoda odpowiedzialna za pobranie szerokosci figury dla klas Rectangle i Oval. 
	* 	@return zwraca aktualna szerokosc figury
	*/
    	public int getWidth() 
	{
	        return width;
    	}
	/**
	*	Metoda odpowiedzialna za pobranie wspolrzednej x-owej figury dla klas Rectangle i Oval. 
	* 	@return zwraca aktualna wspolrzedna x-owa figury
	*/
    	public int getX() 
	{
        	return x;
    	}
	/**
	*	Metoda odpowiedzialna za pobranie wspolrzednej y-kowej figury dla klas Rectangle i Oval. 
	* 	@return zwraca aktualna wspolrzedna y-kowa figury
	*/
    	public int getY() 
	{
        	return y;
    	}
	/**
	*	Metoda odpowiedzialna za pobranie koloru figury . 
	* 	@return zwraca aktualna kolor figury
	*/
    	public Color getColor() 
	{
        	return c;
   	}
	/**
	*	Metoda odpowiedzialna za pobranie wspolrzednej x-owej pierwszego wierzcholka Polygonu. 
	* 	@return zwraca wspolrzedna x-owa pierwszego wierzcholka Polygonu
	*/
	public int getX0()
	{
		return coordy_x[0];
	}
	/**
	*	Metoda odpowiedzialna za pobranie wspolrzednej y-kowej pierwszego wierzcholka Polygonu. 
	* 	@return zwraca wspolrzedna y-kowa pierwszego wierzcholka Polygonu
	*/
	public int getY0()
	{
		return coordy_y[0];
	}
	/**
	*	Metoda odpowiedzialna za pobranie wspolrzednej x-owej drugiego wierzcholka Polygonu. 
	* 	@return zwraca wspolrzedna x-owa drugiego wierzcholka Polygonu
	*/
	public int getX1()
	{
		return coordy_x[1];
	}
	/**
	*	Metoda odpowiedzialna za pobranie wspolrzednej y-kowej drugiego wierzcholka Polygonu. 
	* 	@return zwraca wspolrzedna y-kowa drugiego wierzcholka Polygonu
	*/
	public int getY1()
	{
		return coordy_y[1];
	}
	/**
	*	Metoda odpowiedzialna za pobranie wspolrzednej x-owej trzeciego wierzcholka Polygonu. 
	* 	@return zwraca wspolrzedna x-owa trzeciego wierzcholka Polygonu
	*/
	public int getX2()
	{
		return coordy_x[2];
	}
	/**
	*	Metoda odpowiedzialna za pobranie wspolrzednej y-kowej trzeciego wierzcholka Polygonu. 
	* 	@return zwraca wspolrzedna y-kowa trzeciego wierzcholka Polygonu
	*/
	public int getY2()
	{
		return coordy_y[2];
	}
	
	/**
	*	Metoda odpowiedzialna za zmiane wspolrzednej x-owej pierwszego wierzcholka Polygonu. 
	* 	@param p nowa wspolrzedna x-owa pierwszego wierzcholka Polygonu
	*/
	public void setX0(int p)
	{
		coordy_x[0]=p;
	}
	/**
	*	Metoda odpowiedzialna za zmiane wspolrzednej y-kowej pierwszego wierzcholka Polygonu. 
	* 	@param p nowa wspolrzedna y-kowa pierwszego wierzcholka Polygonu
	*/
	public void setY0(int p)
	{
		coordy_y[0]=p;
	}
	/**
	*	Metoda odpowiedzialna za zmiane wspolrzednej x-owej drugiego wierzcholka Polygonu. 
	* 	@param p nowa wspolrzedna x-owa drugiego wierzcholka Polygonu
	*/
	public void setX1(int p)
	{
		coordy_x[1]=p;
	}
	/**
	*	Metoda odpowiedzialna za zmiane wspolrzednej y-kowej drugiego wierzcholka Polygonu. 
	* 	@param p nowa wspolrzedna y-kowa drugiego wierzcholka Polygonu
	*/
	public void setY1(int p)
	{
		coordy_y[1]=p;
	}
	/**
	*	Metoda odpowiedzialna za zmiane wspolrzednej x-owej trzeciego wierzcholka Polygonu. 
	* 	@param p nowa wspolrzedna x-owa trzeciego wierzcholka Polygonu
	*/
	public void setX2(int p)
	{
		coordy_x[2]=p;
	}
	/**
	*	Metoda odpowiedzialna za zmiane wspolrzednej y-kowej trzeciego wierzcholka Polygonu. 
	* 	@param p nowa wspolrzedna y-kowa trzeciego wierzcholka Polygonu
	*/
	public void setY2(int p)
	{
		coordy_y[2]=p;
	}
	/**
	*	Metoda obslugujaca rysowanie figury
	*	@param g2d obiekt typu Graphics2D
	*/
    	public abstract void paint(Graphics2D g2d);
}
