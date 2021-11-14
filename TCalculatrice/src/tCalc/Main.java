package tCalc;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader; 
			Controleur c = new Controleur();
			loader = new FXMLLoader(getClass().getResource("/MainApp.fxml")); // on load les noeuds d�clar�s sur le fichier FXML
            loader.setController(c); // on associe un controlleur au noeud (pour referer � l'emplacement de leurs fonctions associ�es)
            Parent root = loader.load(); // on load les noeuds sur le noeud parent
			InterfaceGraphique scene = new InterfaceGraphique(root); // cr�ation de la scene (Interface graphique) avec les noeuds regroup�s par le root
			c.setIg(scene); // on donne au controleur l'interface graphique utilis�e pour afficher les changements
			primaryStage.setScene(scene); // preparation du Stage
			primaryStage.setTitle("Calculatrice");
			primaryStage.show(); // affichage
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}