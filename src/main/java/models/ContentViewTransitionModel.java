package models;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import views.DCController;
import views.ServerController;

public class ContentViewTransitionModel implements ContentViewTransitionModelInterface
{
	BorderPane mainView;
	ConcordModel model;
	
	public ContentViewTransitionModel(BorderPane view, ConcordModel m)
	{
		mainView = view;
		model = m;
	}

	@Override
	public void showServer()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showDc()
	{
		// TODO Auto-generated method stub
		
	}

	
	
	
}
