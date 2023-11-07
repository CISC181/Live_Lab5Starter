module ScrabbleServer {
	exports app;
	exports app.hub;
	
	requires ScrabbleBLL;
	requires ScrabbleJabber;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.media;

	opens app.controller to javafx.fxml;
}