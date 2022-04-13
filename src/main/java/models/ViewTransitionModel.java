package models;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import views.ContentController;
import views.DCController;
import views.LoginController;
import views.ServerController;
import views.UserController;

public class ViewTransitionModel implements ViewTransitionModelInterface
{
	BorderPane mainView;
	ConcordModel model;
	UserViewTransitionModel userModel;
	
	public ViewTransitionModel(BorderPane view, ConcordModel m)
	{
		mainView = view;
		model = m;
	    userModel = new UserViewTransitionModel(view, m);
	}
	
	@Override
	public void showLogin()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewTransitionModel.class
				.getResource("../views/LoginView.fxml"));
		try
		{
			Pane view = loader.load();
			mainView.setCenter(view);
			LoginController cont = loader.getController();
			cont.setModel(this);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public void showContent()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewTransitionModel.class
				.getResource("../views/ContentView.fxml"));
		try
		{
			BorderPane view = loader.load();
			mainView.setCenter(view);
			ContentController cont = loader.getController();
			cont.setModel(this);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void showServer()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ContentViewTransitionModel.class
				.getResource("../views/ServerView.fxml"));
		try
		{
			SplitPane view = loader.load();
			BorderPane joe = (BorderPane) mainView.lookup("#rightSide");
			joe.setCenter(view);
			ServerController cont = loader.getController();
			cont.setModel(model);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void showDc()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewTransitionModel.class
				.getResource("../views/DirectConversationView.fxml"));
		try
		{
			VBox view = loader.load();
			BorderPane joe = (BorderPane) mainView.lookup("#rightSide");
			joe.setCenter(view);
			DCController cont = loader.getController();
			cont.setModel(model);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * @return the userModel
	 */
	public UserViewTransitionModel getUserModel()
	{
		return userModel;
	}

	@Override
	public void showUser()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(UserViewTransitionModel.class
				.getResource("../views/UserAlterView.fxml"));
		try
		{
			SplitPane view = loader.load();
			mainView.setCenter(view);
			UserController cont = loader.getController();
			cont.setViewModel(this);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
