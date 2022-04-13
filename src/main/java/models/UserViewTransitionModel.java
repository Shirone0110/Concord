package models;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import views.BlockController;
import views.UserController;
import views.UserInfoController;

public class UserViewTransitionModel implements UserViewTransitionModelInterface
{
	//replaced SplitPane
	BorderPane mainView;
	ConcordModel model;
	
	public UserViewTransitionModel(BorderPane view, ConcordModel m)
	{
		mainView = view;
		model = m;
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
			cont.setViewModel(this);
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

}
