module TCalculatrice {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens tCalc to javafx.graphics, javafx.fxml;
}
