package tCalc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controleur {
	
	private Accumulateur a;
	private InterfaceGraphique ig ;

	public Controleur() {
		a= new Accumulateur();		
		a.currentNbrProperty().addListener((o,ov,nv)->{ // declaration lambda de la fonction event handler. 
														//o : objet , ov : ancienne valeur , nv : nouvelle valeur
			System.out.println("detected modif in cnbr: "+ov+" --> "+nv);
			if (nv == "") // si on n'a pas de saisie courante
			{
				ig.change("0");// on �crit sur l'ecran 0
			}
			else
			{
				ig.change(nv);// on affiche la valeur qui doit �tre �crite
			}
		});
		a.nProperty().addListener((o,ov,nv)->{
			System.out.println("detected modif in n: "+ov+" --> "+nv);
			ig.change(a.getMemoireListString());
		});
		a.negProperty().addListener((o,ov,nv)->{
			System.out.println("detected modif in neg: "+ov+" --> "+nv);
			ig.change(nv);
		});
	}
	// appel de fonctions de l'accumulateur selon le boutton qui a declanch� l'Event
	@FXML
	protected void handleCharacterButton (ActionEvent e)
	{
		a.accumuler(((Button)e.getSource()).getText().charAt(0));
	}
	@FXML
	protected void handleNegCheckBox (ActionEvent e)
	{
		a.neg();
	}
	@FXML
	protected void handlePushButton (ActionEvent e)
	{
		a.push();
	}
	@FXML
	protected void handleAddButton (ActionEvent e)
	{
		a.add();
	}
	@FXML
	protected void handleSubButton (ActionEvent e)
	{
		a.sub();
	}
	@FXML
	protected void handleMultButton (ActionEvent e)
	{
		a.mult();
	}
	@FXML
	protected void handleDivButton (ActionEvent e)
	{
		a.div();
	}
	@FXML
	protected void handleBackSpaceButton (ActionEvent e)
	{
		a.backspace();
	}
	@FXML
	protected void handleResetButton (ActionEvent e)
	{
		a.reset();
	}
	@FXML
	protected void handleDropButton (ActionEvent e)
	{
		a.drop();
	}
	@FXML
	protected void handleSwapButton (ActionEvent e)
	{
		a.swap();
	}
	
	//Setter pour donner une valeur � l'attribut InterfaceGraphique
	protected void setIg(InterfaceGraphique i)
	{
		this.ig = i;
	}
}
