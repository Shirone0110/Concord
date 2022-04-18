package views;

import concord.ConcordClient;
import javafx.fxml.FXML;
import models.ViewTransitionModelInterface;

public class MainController
{
	ViewTransitionModelInterface model;
	ConcordClient client;
	
	public void setModel(ViewTransitionModelInterface m, ConcordClient model2)
	{
		model = m;
		client = model2;
	}
	
	@FXML
	void nothing()
	{
		
	}
}	
