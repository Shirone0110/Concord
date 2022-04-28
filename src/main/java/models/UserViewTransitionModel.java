package models;

import java.io.IOException;

import concord.ConcordClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import views.BlockController;
import views.UserInfoController;

public class UserViewTransitionModel implements UserViewTransitionModelInterface
{
	//replaced SplitPane
	BorderPane mainView;
	ConcordClient client;
	
	public UserViewTransitionModel(BorderPane view, ConcordClient c)
	{
		mainView = view;
	}

	@Override
	public void showUserInfo()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(UserViewTransitionModel.class
				.getResource("../views/UserInfoView.fxml"));
		try
		{
			VBox view = loader.load();
			BorderPane angie = (BorderPane) mainView.lookup("#rightSide");
			angie.setCenter(view);
			UserInfoController cont = loader.getController();
			cont.setViewModel(this, client);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

	@Override
	public void showBlock()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(UserViewTransitionModel.class
				.getResource("../views/BlockList.fxml"));
		try
		{
			VBox view = loader.load();
			BorderPane angie = (BorderPane) mainView.lookup("#rightSide");
			angie.setCenter(view);
			BlockController cont = loader.getController();
			cont.setViewModel(this);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override 
	public void showError()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("Oops! There was an error.");

		alert.showAndWait();
	}

	
	@Override 
	public void showWarning(String text)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText(null);
		alert.setContentText(text);

		alert.showAndWait();
	}
	
}
