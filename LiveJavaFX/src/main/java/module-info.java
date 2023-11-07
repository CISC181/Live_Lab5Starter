module LiveJavaFX {

	exports application;
	exports application.controller;

	exports pkgMain;
	
	requires LiveBLL;
	requires LiveJabber;
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
	
	opens pkgMain to javafx.graphics;
	
	
	
}