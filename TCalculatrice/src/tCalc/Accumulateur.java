package tCalc;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Accumulateur implements IAccumulateur{

	/*D�claration des attributs permettant aussi de signaler les changement de valeur dynamiquement via "property".*/
	private Pile memoire; // pile utilis� par la calculatrice
	private IntegerProperty n=new SimpleIntegerProperty(this, "n", 0); //nombre d'element present sur la pile
	private StringProperty current_nbr =new SimpleStringProperty(this,"current_nbr",""); //chaine de caractere o� on stock la valeur en cours de saisie par l'utilisateur
	private Boolean is_doted = false; //permet de savoir si le nombre en cours de saisie contient un "." ou non.
	private BooleanProperty neg =new SimpleBooleanProperty(this, "neg", false) ; //permet de savoir si la valeur en cours de saisie devra etre interpretée comme + ou - au moment du push. 
	public Accumulateur(){
		memoire = new Pile();
	}


	///////////////////////////////////////////// GETTERS AND SETTERS :
	///////////////////////////////////////////////////////////////////

	public int getN() {
		return n.get();
	}
	public void setN(int n) {
		this.n.set(n);
	}
	public IntegerProperty nProperty(){
		return n;
	}

	public String getCurrentNbr(){
		return current_nbr.get();
	}
			/*M�thode getCurrentNbrDouble() qui agit sur le nombre en cours de saisie, 
			* s'il n'y a rien on retourn 0.0, sinon s'il contient un "." , ajout d'un 0 
			* pour traiter l'erreur qui pourrait survenir si on saisie " 2. " ou " .12 " 
			* devant et derri�re le nombre. Si le nombre est n�gatif, ajout d'un "-" devant  
			* le nombre. Enfin, transformation de la valeur string en un double.*/ 
	public Double getCurrentNbrDouble() {
		String s =getCurrentNbr();
		if (s.isEmpty()) 
		{
			return (0.0) ;
		}
		else 
		{ 
			if (is_doted)
			{
				s= "0"+s+"0";
			}
			if (neg.get())
			{
				s="-"+s;
			}
			return Double.valueOf(s); 
		}
	}
	public void setCurrentNbr(String newString){
		this.current_nbr.set(newString);
	}
	public StringProperty currentNbrProperty(){
		return current_nbr;
	}
	public BooleanProperty negProperty()
	{
		return neg;
	}
	public ArrayList<String> getMemoireListString()//creation d'une liste de String avec les �l�ements de la pile 
												//(liste utilis�e pour la methode change() de l'interface graphique)
	{
		return new ArrayList<>(memoire.stream().map(op -> op.toString()).collect(Collectors.toList()));
	}
		
	////////////////////////////// UTILITY :////////////////
	////////////////////////////////////////////////////////
	
	/* D�claration de m�thodes utilitaires
	 * M�thode cleanBoard() pour se pr�parer � une nouvelle saisie.(d�cochage de la checkbox isNeg, et reinitialisation de la zone de saisie)*/
	 
	public void cleanBoard()
	{
		setCurrentNbr("");
		this.neg.set(false);
		is_doted=false;
	}
	/* M�thode triggerNEvent() qui stock le nombre d'�l�ment N de la pile dans une variable tmp,
	 * qui initialise N � 0 puis qui associe � N sa valeur initial stock� dans tmp.*/
	public void triggerNEvent()
	{
		int tmp = getN();
		setN(0);
		setN(tmp);
	}

	
////////// METHODES OPERATIONS SUR LES PILES OU CALCULES ARITHMETIQUES:///
/////////////////////////////////////////////////////////////////////////
	
	/* M�thode push()
	/*push le nombre en cours de saisie dans la Pile et met � jour la variable N */
	@Override
	public void push() {
		memoire.push(getCurrentNbrDouble());
		setN(getN()+1);
		cleanBoard();
	}

	/* M�thode drop()
	 * Pop le dernier �l�ment de la Pile et met � jour la variable N */
	@Override
	public void drop() {
		if (getN() > 0)
		{
			memoire.drop();
			setN(getN()-1);
		}
	}

	/* M�thode swap()
	 * Swap les 2 derniers �l�ments de la pile entre eux */
	@Override
	public void swap() {
		if (getN() >1)
		{
			Double a = memoire.pop();
			Double b = memoire.pop();
			memoire.push(a);
			memoire.push(b);
			triggerNEvent();			
		}

	}

	/* M�thode add() 
	 *  Si le nombre en cours de saisie n'est pas vide et qu'il y a au moins 
	 *  un �l�ment dans la pile, on pop la pile et on lui ajoue le nombre en
	 *  cours de saisie, on push ensuite cette somme. On signal un changement 
	 *  grace � triggerNEvent() et on cleanBoard(). 
	 *  Si l'accumulateur etait vide, on verifie si la pile contient au moins 
	 *  2 �l�ments, on va pop deux fois et additioner les deux valeurs, mettre 
	 *  � jour le nombre d'�l�ment que contient la pile et on cleanBoard() */
	@Override
	public void add() {
		if(!getCurrentNbr().isEmpty())
		{
			if (getN()>0)
			{
				memoire.push(memoire.pop()+getCurrentNbrDouble());
				triggerNEvent();
				cleanBoard();
			}
		}
		else if(getN()>1)
		{
			memoire.push(memoire.pop()+memoire.pop());
			setN(getN()-1);
			cleanBoard();
		}
	}

	///// MEME PRINCIPE QUE LA METHODE ADD //////
	@Override
	public void sub() {
		if(!getCurrentNbr().isEmpty())
		{
			if (getN()>0)
			{
				memoire.push(memoire.pop()-getCurrentNbrDouble());
				triggerNEvent();
				cleanBoard();
			}
		}
		else if(getN()>1)
		{
			memoire.push(memoire.pop()-memoire.pop());
			setN(getN()-1);
			cleanBoard();
		}
	}
	///// MEME PRINCIPE QUE LA METHODE ADD //////
	@Override
	public void mult() {
		if(!getCurrentNbr().isEmpty())
		{
			if (getN()>0)
			{
				memoire.push(memoire.pop()*getCurrentNbrDouble());
				triggerNEvent();
				cleanBoard();
			}
		}
		else if(getN()>1)
		{
			memoire.push(memoire.pop()*memoire.pop());
			setN(getN()-1);
			cleanBoard();
		}
	}

	/* M�thode div() 
	 * Meme principe que la m�thode add() + v�rification de 
	 * la valeur du d�nominateur soit differente de 0 */
	@Override
	public void div() {
		if(!getCurrentNbr().isEmpty() && getCurrentNbrDouble()!=0.0)
		{
			if (getN()>0)
			{
				memoire.push(memoire.pop()/getCurrentNbrDouble());
				triggerNEvent();
				cleanBoard();
			}
		}
		else if(getN()>1)
		{

			Double a = memoire.pop();
			Double b = memoire.pop();
			if (a!=0.0)
			{
				memoire.push(b/a);
				cleanBoard();
				setN(getN()-1);
			}
			else
			{
				// on a dû pop les valeurs avant de verifier la condition sur le dénominateur. 
				//si on ne les utilise pas, on devrait les remettre � leur place
				memoire.push(b);
				memoire.push(a);
			}
		}
	}

	@Override
	public void neg() { // la valeur neg est changée � false ou true selon ce qu'elle etait avant
		this.neg.set(!neg.get());
	}

	/*M�thode backspace()
	 * Sert � supprimer le dernier caract�re en cours de saisie
	 * On recupere la sous-chaine de caract�re sans le dernier caract�re de la chaine original saisie
	 * On verifie si le caract�re retir� est un "." et on met � jour la variable is_doted */
	@Override
	public void backspace() {
		String s=getCurrentNbr();
		if (!s.isEmpty())
		{
			setCurrentNbr(s.substring(0, s.length()-1));
			if (s.charAt(s.length()-1)=='.')
			{
				is_doted=false;
			}
		}
	}

	/*M�thode accumuller() 
	 * Accumuler des caract�res durant la saisie 
	 * en prenant soin de ne pas permettre la saisie de plus d'un "." dans notre nombre*/
	@Override
	public void accumuler(char character) {
		if (character=='.')
		{
			if(!is_doted) // on verifie si on n'a pas deja un point dans notre chaine courante
			{
				setCurrentNbr(getCurrentNbr()+character);
				is_doted=true;// on retient qu'on a maintenant un point dans notre saisie courante
			}
		}
		else
		{
			setCurrentNbr(getCurrentNbr()+character);			
		}		
	}

	/* M�thode reset()
	 * On cr�e une nouvelle pile
	 * On remet le compteur d'elements de la pile � 0*/
	@Override
	public void reset() {
		memoire = new Pile();
		setN(0);
		cleanBoard();
	}
}







