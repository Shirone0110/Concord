package models;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import concord.Channel;
import concord.ConcordClient;
import concord.DirectConversation;
import concord.Message;
import concord.Server;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import views.ContentController;
import views.CreateAccountController;
import views.DCController;
import views.LoginController;
import views.ServerController;
import views.UserController;

public class ViewTransitionModel implements ViewTransitionModelInterface
{
	BorderPane mainView;
	ConcordModel concordModel;
	ConcordClient client;
	UserViewTransitionModel userModel;
	
	public ViewTransitionModel(BorderPane view, ConcordClient c, ConcordModel cModel)
	{
		mainView = view;
		concordModel = cModel;
		client = c;
	    userModel = new UserViewTransitionModel(view, client);
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
			cont.setModel(this, client);
		} 
		catch (IOException e)
		{
			showError();
			e.printStackTrace();
		}	
	}

	@Override
	public void showContent()
	{			
		try
		{
			concordModel.setServers(client.getServerByUserId());
			//for (Server s: client.getServerByUserId())
			//	concordModel.getServers().add(s);
		} 
		catch (RemoteException e1)
		{
			showError();
			e1.printStackTrace();
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewTransitionModel.class
				.getResource("../views/ContentAlterView.fxml"));
		try
		{
			BorderPane view = loader.load();
			mainView.setCenter(view);
			ContentController cont = loader.getController();
			cont.setModel(this, concordModel, client);
			showDc();
		} 
		catch (IOException e)
		{
			showError();
			//e.printStackTrace();
		}
	}
	
	@Override
	public void showServer(Server s)
	{
		try
		{
			ArrayList<Channel> chanList = client.getChannelByUserId(s.getServerId());
			concordModel.setChannels(chanList);
			if (chanList.size() > 0)
				concordModel.setMessages(chanList.get(0).getMessages());
		} catch (RemoteException e)
		{
			showError();
		}
		concordModel.setUsers(s.getUsers());
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewTransitionModel.class
				.getResource("../views/ServerAlterView.fxml"));
		try
		{
			BorderPane view = loader.load();
			BorderPane content = (BorderPane) mainView.lookup("#contentPane");
			content.setCenter(view);
			ServerController cont = loader.getController();
			cont.setModel(this, concordModel, client);
		} 
		catch (IOException e)
		{
			showError();
		}		
	}

	@Override
	public void showDc()
	{
		try
		{
			concordModel.setDcs(client.getDcByUserId());
		} catch (RemoteException e1)
		{
			showError();
			e1.printStackTrace();
		}
		concordModel.getMessages().clear();
		if (concordModel.getDcs().size() != 0)
		{
			concordModel.setMessages(concordModel.getDcs().get(0).getMessages());
		}
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewTransitionModel.class
			  .getResource("../views/DcAlterView.fxml"));
		try
		{
			BorderPane view = loader.load();
			BorderPane content = (BorderPane) mainView.lookup("#contentPane");
			content.setCenter(view);
			DCController cont = loader.getController();
			cont.setModel(this, concordModel, client);
		} 
		catch (IOException e)
		{
			showError();
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
			showError();
			e.printStackTrace();
		}
	}

	@Override
	public void showCreate()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewTransitionModel.class
				.getResource("../views/CreateAccountView.fxml"));
		try
		{
			BorderPane view = loader.load();
			mainView.setCenter(view);
			CreateAccountController cont = loader.getController();
			cont.setModel(this, client);
		} 
		catch (IOException e)
		{
			showError();
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
