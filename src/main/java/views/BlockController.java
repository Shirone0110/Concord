package views;

import javafx.fxml.FXML;
import models.UserViewTransitionModel;
import models.UserViewTransitionModelInterface;

public class BlockController
{
	UserViewTransitionModelInterface model;
	
	public void setViewModel(UserViewTransitionModel m)
	{
		model = m;
	}
	
	@FXML
	void doNothing()
	{
		
	}
}
