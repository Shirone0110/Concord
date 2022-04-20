package views;

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
	void nothing()
	{
		
	}
}	
