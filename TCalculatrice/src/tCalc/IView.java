package tCalc;

import java.util.List;

public interface IView {
	void affiche();
	void change(List<String> data);
	void change(String accu);
}
