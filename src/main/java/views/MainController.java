package views;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import models.ViewTransitionModelInterface;

public class MainController
{
	ViewTransitionModelInterface model;
	
	public void setModel(ViewTransitionModelInterface m)
	{
		model = m;
	}
	
	@FXML
	public void exitApplication(ActionEvent event) 
	{
	   Platform.exit();
	}
}	
