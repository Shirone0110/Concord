package views;

import java.rmi.RemoteException;

import concord.ConcordClient;
import concord.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class CreateDcController 
{
	Stage stage;
	ConcordClient client;
	User target;

    @FXML
    private MenuButton userDropDown;

	public void setModel(Stage s, ConcordClient c) throws RemoteException
	{
		stage = s;
		client = c;
		userDropDown.getItems().clear();
		for (Integer id: client.getAllUser())
		{
			int userId = id.intValue();
			User u = client.findUserById(userId);
			MenuItem item = new MenuItem(u.getUserName());
			item.setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) 
			    {
			    	target = u;
			    }
			});
			userDropDown.getItems().add(item);
		}
	}
	

    @FXML
    void onCancelClick(ActionEvent event) 
    {
    	stage.close();
    }

    @FXML
    void onCreateClick(ActionEvent event) throws RemoteException 
    {
    	client.createDc(client.getUser().getUserId(), target.getUserId());
    	stage.close();
    }
}
