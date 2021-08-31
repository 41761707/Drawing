import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.GridLayout;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton; 
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.ClassNotFoundException;
/**
*	Klasa odpowiedzialna za wyswietlanie,modyfikowanie,zapisywanie i wczytywanie figur na panelu
*	@see Window
*	@see Rectangle
*	@see Oval
*	@see Polygon
*	@see Test
*/
public class MyPanel extends JPanel 
{
    private static final long serialVersionUID = 1L;
	/** Tworzymy obiekt typu Window aby nasz panel mogl porozumiewac sie z menu glownym w celu ustalenia, ktory tryb dzialania i ktora figura jest aktualnie wybrana */
	private Window window;
	/** Odpowiednio wspolrzedna x-owa poczatkowa, y-kowa poczatkowa, x-owa koncowa, y-kowa koncowa.
	* Taki podzial wykorzystywany jest przy rysowaniu figur aby zachowac plynnosc
	*/
	private int x1=0;
	private int y1=0;
	private int x2=0;
	private int y2=0;
	/** Obecne wartosci x-owe i y-kowe dla wszystkich figur*/
	private int current_x=0;
	private int current_y=0;
	private int current_x0=0;
	private int current_y0=0;
	private int current_x1=0;
	private int current_y1=0;
	private int current_x2=0;
	private int current_y2=0;
	/** wspolrzedne x-owe i y-kowe wierzcholkow trojkata*/
	private int[] coordy_x={0,0,0};
	private int[] coordy_y={0,0,0};
	/** zmienna majaca na celu informowanie pozostalych funkcji, czy myszka jest aktualnie ciagnieta (z wcisnietym lewym przycskiem myszy) po panelu. Wykorzystywana do plynnego rysowania figury */
	private boolean MOUSE_DRAGGED=false;
	/*Aktualnie rozpatrywana figura, zmienna wykorzystywana przy modyfikowaniu figury*/
	private Shape current_shape;
	/*Lista figur, dzieki ktorej na panelu moze znajdowac sie wiecej niz jedna figura w tym samym czasie*/
    	private List<Shape> shapes= new ArrayList<Shape>();
	MyPanel(Window window)
	{
		/** tworzenie popmenu metoda identyczna do tworzenia ogolnego menu w pliku window.java*/
		setBackground(Color.WHITE);
		this.window = window;
		/** Nasluchujemy akcje i ruchy wykonywane przez myszke */
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				/** 					*	jestesmy w trybie rysowania pobieramy poczatkowe wartosci x i y oraz 					*	ustawiamy zmienna MOUSE_DRAGGED na prawda*/
				String mode=window.dzialanie();
				if(mode.equals("rysowanie"))
				{
					x1=e.getX();
					y1=e.getY();
					MOUSE_DRAGGED=true;
				}
			}
		});
		addMouseMotionListener(new MouseAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				/**	Pobieramy aktualne wspolrzedne myszki i wywolujemy funkcje repaint, 					* ktora plynnie rysuje figure na naszym panelu */
				String figura=window.figura();
				String mode=window.dzialanie();
				if(mode.equals("rysowanie"))
				{
					x2=e.getX();
					y2=e.getY();
					repaint();
				}
			}
		});
		addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent e)
			{
				/**	Gdy lewy przycisk myszy zostanie puszczony, ostatecznie pobieramy 					*	aktualne wspolrzedne myszy i wysylamy je do specjalnej funkcji 					*	What_to_draw(), majacej za zadanie dodanie utworzonej figury do naszej
				*	tablicy figur oraz ustawiamy zmienna MOUSE_DRAGGED na falsz */ 
				String mode=window.dzialanie();
				if(mode.equals("rysowanie"))
				{
					x2=e.getX();
					y2=e.getY();
					MOUSE_DRAGGED=false;
					What_to_draw();
				}
			}
		});
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				/** W zaleznosci od trybu wykonujemy inne dzialania przy kliknieciu mysza
				*/
				String mode=window.dzialanie();
				String figura=window.figura();
				/** 	Jesli wybrany tryb to modyfikowanie to odwracamy tablice figur i szukamy
				*	pierwszej figury dla ktorej kursor znajduje sie w jej obrebie
				*	Odwracanie tablicy jest potrzebne ze wzgledu na mechanizm dodawania 
				*	elementow do JPanelu
				*/
				if(mode.equals("modyfikowanie"))
				{
					Collections.reverse(shapes);
					for(Shape shape: shapes)
					{
						if(shape.contains(e.getPoint()))
						{
							current_shape=shape;
							Modyfikacja(e.getX(),e.getY(),shape,e.getButton());
							break;
								
						}
					}
					Collections.reverse(shapes);
				}
				/** Odpowiednio dla zapisywania i wczytywania wywolujemy odpowiednie metody*/
				else if(mode.equals("zapisywanie"))
				{
					try
					{
						save_image();
					}
					catch (IOException ex)
					{
						System.out.println(ex);
					}
				}
				else if(mode.equals("wczytywanie"))
				{
					try
					{
						load_image();
					}
					catch (IOException ex)
					{
						System.out.println(ex);
					}
				}
			}
		});	
	}
	/** 	Metoda odpowiadajaca za modyfikacje figur znajdujacych sie na panelu
	*	@param x wspolrzedna x-owa figury
	*	@param y wspolrzedna y-kowa figury
	*	@param shape aktualnie modyfikowana figura
	*	@param button dane na temat aktualnej pozycji kursora
	*/
	public void Modyfikacja(int x, int y, Shape shape, int button)
	{
		/** Zmienne przechowujaca tymczasowe wartosci aktualnie modyfikowanej figury */
		int temp_x=shape.getX();
		int temp_y=shape.getY();
		int temp_h=shape.getHeight();
		int temp_w=shape.getWidth();
		String temp_f=shape.getFilling();
		Color temp_c=shape.getColor();
		/** Zmienne majace za zadanie przecowywanie informacji o aktualnych akcjach wykonywanych przez mysz */
		MouseListener m_l;
		MouseMotionListener m_m_l;
		if(button == MouseEvent.BUTTON3)
		{
			Color newColor = JColorChooser.showDialog(null, "Wybierz kolor", Color.WHITE);
			shape.setFilling("fill");
			shape.setColor(newColor);
			repaint();
		}
		else if(button==MouseEvent.BUTTON2)
		{
			/** Jesli kliknieto kolko figura ulega powiekszeniu */
			if(shape instanceof Polygon)
			{
				shape.setX0(shape.getX0()-10);
				shape.setX1(shape.getX1()+10);
				shape.setX2(shape.getX2());
				shape.setY0(shape.getY0()+10);
				shape.setY1(shape.getY1()+10);
				shape.setY2(shape.getY2()-10);
				repaint();
			}
			else
			{
				temp_x=temp_x-15;
				if(temp_x<0)
				{
					temp_x=0;
				}
				temp_y=temp_y-15;
				if(temp_y<0)
				{
					temp_y=0;
				}
				temp_h=temp_h+30;
				temp_w=temp_w+30;
				shape.setX(temp_x);
				shape.setY(temp_y);
				shape.setWidth(temp_w);
				shape.setHeight(temp_h);
				repaint();
			}
		}
			if(button==MouseEvent.BUTTON1)
			{
				/*	Jesli kliknieto lewy przycisk myszy figura ulega przesuwaniu
				*	caly proces przypomina rysowanie figury z jedyna roznica taka, ze
				*	w tym przypadku figury juz istnieja wiec jedynie nadpisujemy ich
				*	poszczegolne wartosci.
				*	Dodatkowo aby przerwac nasluchiwanie dzialan dla danego elementu
				*	w funkcji MouseRelease usuwamy zawartosc zmiennych m_l i m_m_l
				*/
				addMouseListener(m_l= new MouseAdapter()
				{
					public void mousePressed(MouseEvent e)
					{
							String mode=window.dzialanie();
							if(mode.equals("modyfikowanie") &&shape.contains(e.getPoint()))
							{
								x1=e.getX();
								y1=e.getY();
								if(shape instanceof Polygon)
								{
									current_x0=shape.getX0();
									current_x1=shape.getX1();
									current_x2=shape.getX2();
									current_y0=shape.getY0();
									current_y1=shape.getY1();
									current_y2=shape.getY2();
								}
								else
								{
									current_x=shape.getX();
									current_y=shape.getY();
								}
							}
					}
				});
				addMouseMotionListener(m_m_l=new MouseAdapter()
				{
					public void mouseDragged(MouseEvent e)
					{
						String mode=window.dzialanie();
						if(mode.equals("modyfikowanie") && shape.contains(e.getPoint()))
						{
							/** Przesuniecie figury to tak naprawde przesuniecie kazdego jej punktu o dany wektor, ktory jest rowny odleglosci punktu poczatkowego od aktualnej pozycji myszy */
							x2=e.getX();
							y2=e.getY();
							if(shape instanceof Polygon)
							{
								shape.setX0(current_x0+(x2-x1));
								shape.setX1(current_x1+(x2-x1));
								shape.setX2(current_x2+(x2-x1));
								shape.setY0(current_y0+(y2-y1));
								shape.setY1(current_y1+(y2-y1));
								shape.setY2(current_y2+(y2-y1));
								repaint();
							}
							else
							{
								shape.setX(current_x+(x2-x1));
								shape.setY(current_y+(y2-y1));
								repaint();
							}
						}
						return;

					}
				});
				addMouseListener(new MouseAdapter()
				{
					public void mouseReleased(MouseEvent e)
					{
						removeMouseListener(m_l);
						removeMouseMotionListener(m_m_l);
						
					}
				});
			}
	}
	/** Funkcja dodajaca zadana figure do listy figur*/
	public void What_to_draw()
	{
		int x = Math.min(x1,x2);
                int y = Math.min(y1,y2);
		String figura=window.figura();
		if(figura.equals("prostokat"))
		{
			/** 	Bez rozpatrzenia tych przypadkow bylibysmy w stanie narysowac figure tylko w 				*	"IV" cwiartce ukladow wspolrzednych. Przedstawione ponizej ify odpowiadaja
			*	odbiciom figur wzgledem osi X lub Y w zaleznosci zmiennych x1,y1,x2,y2
			*/
			if(x2>x1 && y1>y2)
			{
				addShape(new Rectangle(x1,y2,Math.abs(x1-x2),Math.abs(y1-y2),Color.BLACK,"border"));
			}
			else if(x2>x1 && y2>y1)
			{
				addShape(new Rectangle(x1,y1,Math.abs(x1-x2),Math.abs(y1-y2),Color.BLACK,"border"));
			}
			else if(x1>x2 && y2>y1)
			{
				addShape(new Rectangle(x2,y1,Math.abs(x1-x2),Math.abs(y1-y2),Color.BLACK,"border"));
			}
			else
			{
				addShape(new Rectangle(x2,y2,Math.abs(x1-x2),Math.abs(y1-y2),Color.BLACK,"border"));
			}
		}
		else if(figura.equals("kolo"))
		{
			/** 	To samo co dla prostokata z tym, ze tutaj wykonujemy dodatkowe obliczenia
			*	aby zapewnic iz nasza figura bedzie rysowala sie jako okrag a nie elipsa */	
			int circle = Math.min(Math.abs(x1 - x2), Math.abs(y1 - y2));
			if(x2>x1 && y1>y2)
			{
				x = x1;
                                y = y1 - circle;
			}
			else if(x2>x1 && y2>y1)
			{
				x = x1;
                                y = y1;
			}
			else if(x1>x2 && y2>y1)
			{
				x = x1 - circle;
                                y = y1;
			}
			else
			{
				x = x1 - circle;
                                y = y1 - circle;
			}
			addShape(new Oval(x,y,circle,circle,Color.BLACK,"border"));
		}
		else if(figura.equals("trojkat"))
		{
			int width=Math.abs(x1-x2);
			int height=Math.abs(y1-y2);
			coordy_x[0]=x;
			coordy_x[1]=x+width;
			coordy_x[2]=x+width/2;
			coordy_y[0]=y+height;
			coordy_y[1]=y+height;
			coordy_y[2]=y;
			addShape(new Polygon(coordy_x,coordy_y,3,Color.BLACK,"border"));
		}
	}
	/** 	Metoda majaca za zadanie dodanie zadanej figury do listy figur oraz ostateczne wyswietlenie 
	* 	jej na ekranie
	*	@param shape figura, ktora ma zostac dodana do listy figur
	*/
	public void addShape(Shape shape) 
	{
      		shapes.add(shape);
      		repaint();
   	}
	/** 	Metoda majaca za zadanie zapis zawartosci panelu do pliku
	*	@throws IOexception nie mozna bylo utworzyc wskazanego pliku 
	*/
	public void save_image() throws IOException
	{
		try
		{
			String filename = JOptionPane.showInputDialog(null,"Podaj nazwe pliku");
			FileOutputStream fos= new FileOutputStream(filename);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(shapes);
			oos.close();
		}
		catch (IOException e)
		{
			System.out.println("Nie mozna zapisac zdjecia");
		}
	}
	/**	Metoda majaca za zadanie wczytanie zawartosci pliku do panelu
	*	@throws IOexception nie mozna bylo otworzyc wskazanego pliku
	*	@throws ClassNotFoundException Wywolane gdy program probuje wczytac klase gdy jest wyspecyfikowana
	*/
	public void load_image() throws IOException
	{
		try
		{
			String filename = JOptionPane.showInputDialog(null,"Podaj nazwe pliku");
			FileInputStream fis=new FileInputStream(filename);
			ObjectInputStream ois=new ObjectInputStream(fis);
			List<Shape> shapes_temp=new ArrayList<Shape>();
			for (Shape shape : shapes)
			{
				shapes_temp.add(shape);
			}
			shapes.clear();
			shapes=(ArrayList<Shape>) ois.readObject();
			for (Shape shape : shapes_temp)
			{
				shapes.add(shape);
			}
			ois.close();
			repaint();
		}
		catch (ClassNotFoundException | IOException e)
		{
			System.out.println("Nie mozna wczytac zdjecia");
		}
	}
	/** 	Metoda ustalajaca preferowana wielkosc panelu
	*	@return wielkosc panelu
	*/
    @Override
    public Dimension getPreferredSize() 
	{
        	return new Dimension(800, 600);
   	}
	/** 	Funkcja odpowiedzialna za rysowanie figury na panelu
	*	@param g aktualnie rozpatrywana figura
	*/
    @Override
    protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		String figura=window.figura();
		String mode=window.dzialanie();
			/* Jesli zmienna MOUSE_DRAGGED jest ustawiona na true, rysujemy tymczasowe figury
			*  Ma to za zadanie zachowac plynnosc rysunku */
			if(MOUSE_DRAGGED)
			{
				/** To samo co w metodzie What_to_draw() z tym ze tutaj nie dodajemy do listy */
				if(figura.equals("prostokat"))
				{
					if(x2>x1 && y1>y2)
					{
						g.drawRect(x1,y2,Math.abs(x1-x2),Math.abs(y1-y2));
					}
					else if(x2>x1 && y2>y1)
					{
						g.drawRect(x1,y1,Math.abs(x1-x2),Math.abs(y1-y2));
					}
					else if(x1>x2 && y2>y1)
					{
						g.drawRect(x2,y1,Math.abs(x1-x2),Math.abs(y1-y2));
					}
					else
					{
						g.drawRect(x2,y2,Math.abs(x1-x2),Math.abs(y1-y2));
					}
				}
				else if(figura.equals("kolo"))
				{
					int x = Math.min(x1,x2);
				        int y = Math.min(y1,y2);
					int circle = Math.min(Math.abs(x1 - x2), Math.abs(y1 - y2));
					if(x2>x1 && y1>y2)
					{
						x = x1;
				                y = y1 - circle;
					}
					else if(x2>x1 && y2>y1)
					{
						x = x1;
				                y = y1;
					}
					else if(x1>x2 && y2>y1)
					{
						x = x1 - circle;
				                y = y1;
					}
					else
					{
						x = x1 - circle;
				                y = y1 - circle;
					}
					g.drawOval(x,y,circle,circle);
				}
				else if(figura.equals("trojkat"))
				{
					int x = Math.min(x1,x2);
                			int y = Math.min(y1,y2);
					int width=Math.abs(x1-x2);
					int height=Math.abs(y1-y2);
					coordy_x[0]=x;
					coordy_x[1]=x+width;
					coordy_x[2]=x+width/2;
					coordy_y[0]=y+height;
					coordy_y[1]=y+height;
					coordy_y[2]=y;
					g.drawPolygon(coordy_x,coordy_y,3);
				}
			}
			/*Wywolanie funkcji rysujacej dla kazdej figury znajdujacej sie w liscie figur*/
			for (Shape shape : shapes) 
			{
				Graphics2D g2d = (Graphics2D) g.create();
				shape.paint(g2d);
				g2d.dispose();
			}
    	}
}

