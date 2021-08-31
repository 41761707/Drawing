/**
* Test jest odpowiedzalny za rozruch programu
* "Prosty Edytor graficzny"
*
* @author  Radoslaw Wojtczak
* @version 1.1
* @since  2020-05-22
*/
public class Test
{
/**
*	Metoda odpowiedzialna za utworzenie
*	obiektu JFrame oraz utworzeniu i dodaniu
*	obiektu JPanel do JFrame
* 	@param args standardowy parametr funkcji main
*/
	public static void main(String args[])
	{
		Window o=new Window();
		o.add(new MyPanel(o));
		o.pack();
	}
}
