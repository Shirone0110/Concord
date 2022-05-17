package views;

import java.rmi.RemoteException;

import concord.ConcordClient;
import concord.InvalidActionException;
import concord.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import models.ConcordModel;

public class ManageUserController
{

	ConcordModel model;
	Stage stage;
	ConcordClient client;
	int serverId;
	User addTarget, delTarget;
	
    @FXML
    private MenuButton addUserDropDown;

    @FXML
    private MenuButton delUserDropDown;
    
    public void setModel(ConcordModel m, Stage s, ConcordClient c, int sid) throws RemoteException
    {
    	model = m;
    	stage = s;
    	client = c;
    	serverId = sid;
    	
    	addUserDropDown.getItems().clear();
		for (Integer id: client.getAllUser())
		{
			int userId = id.intValue();
			User u = client.findUserById(userId);
			boolean exist = false;
			for (User existedU: model.getUsers())
			{
				if (existedU.getUserId() == userId) 
				{
					exist = true;
					break;
				}
			}
			if (exist) continue;
			MenuItem item = new MenuItem(u.getUserName());
			item.setId(u.getUserName());
			item.setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) 
			    {
			    	addTarget = u;
			    	addUserDropDown.setText(u.getUserName());
			    }
			});
			addUserDropDown.getItems().add(item);
		}
		
		delUserDropDown.getItems().clear();
		for (User u: model.getUsers())
		{
			if (u.getUserId() == client.getUser().getUserId()) continue;
			MenuItem item = new MenuItem(u.getUserName());
			item.setId(u.getUserName());
			item.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e)
				{
					delTarget = u;
					delUserDropDown.setText(u.getUserName());
				}
			});
			delUserDropDown.getItems().add(item);
		}
    }

    @FXML
    void onClickCancel(ActionEvent event) 
    {
    	stage.close();
    }

    @FXML
    void onClickSubmit(ActionEvent event) throws RemoteException
    {
    	if (addTarget != null)
    	{
    		try
			{
				client.invite(addTarget.getUserId(), serverId);	
			} catch (InvalidActionException e)
			{
				e.printStackTrace();
			}
    	}
    	
    	if (delTarget != null)
    	{
    		try
			{
				client.removeMember(delTarget.getUserId(), serverId);
			}
    		catch (InvalidActionException e)
			{
				
				e.printStackTrace();
			}
    	}
    	stage.close();
    }

}
