import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

import javax.swing.JMenu;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
/**
*	Klasa odpowiedzialna za utworzenie ramki oraz menu glownego
*	@see MyPanel
*	@see Rectangle
*	@see Oval
*	@see Polygon
*	@see Test
*/
public class Window extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	/** Zmienna menu odpowiadajaca za wybor figury */
	private JMenu menu;  
	/** Zmienne odpowiadajace za wybor opcji */
        private JRadioButton oval, rectangle, polygon,draw,edit,save,load;
	/** Przyciski dialogowe dostarczajace dodatkowe informacje o programie */
	private JButton info,how_to_use;
	/** Grupy przyciskow odpowiadajace za poprawne funkcjonowanie menu
	* group laczy elementy Figury menu, mode laczy elementy save,load,edit,draw
	*/
	private ButtonGroup group,mode;
	/** Zmienna informujaca o obecnie wybranej figurze */
	public String current_figure="NULL";
	/** Zmienna informujaca o obecnie wybranym trybie dzialania */
	public String operation_mode="NULL";
	Window()
	{
		/** Ustalamy wielkosc i tytul okna*/
		super("Prosty Edytor Graficzny");
		setSize(800,600); 
		/*Tworzenie menu glownego */
      		JMenuBar menubar=new JMenuBar();  
          	menu=new JMenu("Figury menu");   
          	oval=new JRadioButton("Kolo");  
          	rectangle=new JRadioButton("Prostokat");  
          	polygon=new JRadioButton("Trojkat");
		draw=new JRadioButton("Rysowanie");
		edit=new JRadioButton("Modyfikowanie");
		save=new JRadioButton("Zapisz");
		load=new JRadioButton("Wczytaj");
		info=new JButton("Info");
		how_to_use= new JButton("Instrukcja");
		mode=new ButtonGroup();
		mode.add(draw);
		mode.add(edit);
		mode.add(save);
		mode.add(load);
          	menu.add(oval); 
		menu.add(rectangle); 
		menu.add(polygon);    
          	menubar.add(menu);
		menubar.add(draw);
		menubar.add(edit); 
		menubar.add(save);
		menubar.add(load); 
		menubar.add(info);
		menubar.add(how_to_use);
		setJMenuBar(menubar);
		group = new ButtonGroup();
    		group.add(oval);
    		group.add(rectangle);
		group.add(polygon);
		how_to_use.setActionCommand("instrukcja");
		oval.setActionCommand("kolo");
		info.setActionCommand("info");
		rectangle.setActionCommand("prostokat");
		polygon.setActionCommand("trojkat");
		edit.setActionCommand("modyfikowanie");
		draw.setActionCommand("rysowanie");
		save.setActionCommand("zapisywanie");
		load.setActionCommand("wczytywanie");
		how_to_use.addActionListener(this);
		info.addActionListener(this);
		save.addActionListener(this);
		load.addActionListener(this);
		oval.addActionListener(this);
    		rectangle.addActionListener(this);
    		polygon.addActionListener(this);
		draw.addActionListener(this);
		edit.addActionListener(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout());
		setVisible(true);
		pack();
	}
	/** Funkcja odpowiadajaca za przechwytywanie zdarzen zwiazanych z opcjami
	    @param e akcja, ktora zostala wykonana
	*/
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		/** Metoda ma za zadanie stwierdzic, ktora akcja zostala wykonana i przyporzadkowac do odpowiedniej zmiennej odpowiedni tekst, ktory otrzymuje dzieki wczesniejszemu zainicjowaniu setActionCommand */
	        String powod=e.getActionCommand();
		if(powod.equals("kolo"))
		{
			current_figure="kolo";
		}
		else if(powod.equals("prostokat"))
		{
			current_figure="prostokat";
		}
		else if(powod.equals("trojkat"))
		{
			current_figure="trojkat";
		}
		String which_mode=e.getActionCommand();
		if(which_mode.equals("modyfikowanie"))
		{
			operation_mode="modyfikowanie";
		}
		else if(which_mode.equals("rysowanie"))
		{
			operation_mode="rysowanie";
		}
		else if(which_mode.equals("zapisywanie"))
		{
			
			operation_mode="zapisywanie";
		}
		else if(which_mode.equals("wczytywanie"))
		{
			operation_mode="wczytywanie";
		}
		else if(which_mode.equals("info"))
		{
			JOptionPane.showMessageDialog(null,"Prosty edytor graficzny \nProgram wykonany przez Radoslawa Wojtczaka\njako zadanie na Kurs Programowania na studia na wydziale Podstawowych Problemow Techniki. \nProgram ow sluzy do tworzenia, modyfikowania(powiekszanie,zmiana kolorow, zmiana polozenia) \noraz zapisywania i wczytywania z pliku trzech podstawowych figur- kol, trojkatow i prostokatow","Info",1);
		}
		else if(which_mode.equals("instrukcja"))
		{
			JOptionPane.showMessageDialog(null,"Program zawiera 7 przyciskow znajdujacych sie w menu glowym \nFigury menu- po kliknieciu tego przycisku pojawiaja sie 3 dostepne do rysowania figury. \nAby rozpoczac rysowanie nalezy wybrac ktoras z opcji \nRysowanie- wlaczenie tej opcji pozwala na rysowanie figur w programie.\nGdy opcja jest odnaczona rysowanie jest niemozliwe.\nModyfikowanie- wlaczenie tej opcji pozwala na dostepne modyfikacje narysowanych figur.\nW sklad tych modyfikacji wchodza: \nPrzesuwanie figury przy pomocy klikniecia a nastepnie przytrzymania lewego przycisku myszy \nPowiekszenie figury przy pomocy srodkowego przyciksu myszy(kolko nalezy kliknac aby otrzymac zalozony efekt)\nZmiana koloru figury przy pomocy prawego przycisku myszy- po kliknieciu pojawia sie panel z dostepnymi kolorami \nKolor wybieramy klikajac lewym przyciskiem mszy.\nZapisz- klikniecie tej opcji A NASTEPNIE KLIKNIECIE W LOSOWE MIEJSCE NA PANELU spowoduje zapis zawartosci do pliku save1.png\nWczytaj- klikniecie tej opcji A NASTEPNIE KLIKNIECIE W LOSOWE MIEJSCA NA PANELU spowoduje wczytanie zawartosci z pliku save1.png.\nInfo- informacje o autorze,nazwie,przeznaczeniu programu.\nInstrukcja obslugi-wyswietla obecny tekst ","Instrukcja obslugi",1);
		}
		
	}
	/** 	Funkcja informujaca o tym jaka figura jest obecnie wybrana
		@return current_figure- obecnie wybrana figura z menu
	*/
	public String figura()
	{
		return current_figure;
	}
	/** 	Funkcja informujaca o tym jaki tryb dzialania jest obecnie wybrany
		@return current_figure- obecnie wybrany tryb dzialania
	*/
	public String dzialanie()
	{
		return operation_mode;
	}
}
