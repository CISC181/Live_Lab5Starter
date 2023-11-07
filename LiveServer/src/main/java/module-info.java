module LiveServer {
	exports app;
	exports app.hub;
	
	requires LiveBLL;
	requires LiveJabber;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.media;

	opens app.controller to javafx.fxml;
	
	
	
	
}