module ScrabbleJavaFX {

	exports application;
	exports application.controller;

	requires ScrabbleBLL;
//	requires ScrabbleServer;
	requires ScrabbleJabber;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.media;

	requires jackson.databind;
	requires jackson.core;
	requires jackson.dataformat.xml;
	requires jackson.annotations;
	
	requires java.sql;
	opens application to javafx.fxml;
	opens application.controller to javafx.fxml;
	
//	opens app.controller to javafx.fxml;
	
}