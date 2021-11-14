package tCalc;

import java.util.Collections;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class InterfaceGraphique extends Scene implements IView{

	private ListView lv; // zone o� on affichera le contenu de la pile
	private Label lAccu;// zone o� on ecrira la valeur en cours de saisie
	private CheckBox CbNeg;// check box indiquant si la valeur en cours de saisie doit etre consid�r�e comme negative ou positive

	public InterfaceGraphique(Parent root) {
		super(root,400,600);
		// on rep�re la list view , le label , et la check box grace � leur IDs
		lv = (ListView)this.lookup("#listView1");
		lAccu=(Label)this.lookup("#label1");
		CbNeg=(CheckBox)this.lookup("#checkbox1");
	}

	@Override
	public void affiche() {
		
	}
	@Override
	public void change(List<String> data) {
		lv.getItems().clear();
		Collections.reverse(data);
		lv.getItems().addAll(data);
	}

	@Override
	public void change(String accu) {
		lAccu.setText(accu);
	}
	
	public void change(Boolean neg)
	{
		CbNeg.setSelected(neg);
	}
}
