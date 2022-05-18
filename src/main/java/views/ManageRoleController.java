package views;

import java.rmi.RemoteException;
import java.util.ArrayList;

import concord.Channel;
import concord.ConcordClient;
import concord.InvalidActionException;
import concord.Role;
import concord.RoleComponent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.ConcordModel;
import models.ViewTransitionModel;

public class ManageRoleController 
{
	
	ConcordModel model;
	Stage stage;
	ConcordClient client;
	int serverId;
	Role roleTarget;
	Channel chanTarget;
	ViewTransitionModel viewModel;
	ArrayList<CheckBox> checkList;

	@FXML
	private TextField newRoleName;
	
    @FXML
    private CheckBox chkAddMember;

    @FXML
    private CheckBox chkModifyAdmin;

    @FXML
    private CheckBox chkModifyChannel;

    @FXML
    private CheckBox chkModifyMessage;

    @FXML
    private CheckBox chkModifyMod;

    @FXML
    private CheckBox chkModifyRole;

    @FXML
    private CheckBox chkRemoveMember;

    @FXML
    private CheckBox chkSendMessage;

    @FXML
    private CheckBox chkViewChannel;

    @FXML
    private MenuButton chooseRoleDropDown;
    
    @FXML
    private MenuButton channelDropDown;
    
    @FXML
    private CheckBox chkSpecificSendMessage;

    @FXML
    private CheckBox chkSpecificViewChannel;

    public void setModel(ConcordModel m, Stage s, ConcordClient c, int sid, ViewTransitionModel vm) throws RemoteException
    {
    	checkList = new ArrayList<CheckBox>();
    	checkList.add(chkViewChannel);
    	checkList.add(chkSendMessage);
    	checkList.add(chkAddMember);
    	checkList.add(chkRemoveMember);
    	checkList.add(chkModifyAdmin);
    	checkList.add(chkModifyMod);
    	checkList.add(chkModifyChannel);
    	checkList.add(chkModifyRole);
    	checkList.add(chkModifyMessage);
    	
    	model = m;
    	stage = s;
    	client = c;
    	serverId = sid;
    	viewModel = vm;
    	roleTarget = null;
    	chooseRoleDropDown.getItems().clear();
    	
    	MenuItem item = new MenuItem("Create new Role");
    	item.setId("Create new Role");
    	item.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) 
		    {
		    	newRoleName.setEditable(true);
		    	channelDropDown.setDisable(true);
		    	chooseRoleDropDown.setText("Create new Role");
		    }
		});
		chooseRoleDropDown.getItems().add(item);
		ArrayList<String> names = client.getRoleBuilderByServerId(sid).getBasicNames();
		
		for (RoleComponent r: client.getRoleByServerId(sid))
		{
			Role cur = (Role) r;
			item = new MenuItem(r.getName());
	    	item.setId(r.getName());
	    	item.setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) 
			    {
			    	roleTarget = cur;
			    	for (int i = 0; i < checkList.size(); i++)
			    	{
			    		checkList.get(i).setSelected(cur.getBasicPermission(names.get(i)));
			    	}
			    	newRoleName.setEditable(false);
			    	channelDropDown.setDisable(false);
			    	chooseRoleDropDown.setText(roleTarget.getName());
			    }
			});
			chooseRoleDropDown.getItems().add(item);
		}
		
		channelDropDown.getItems().clear();
		for (Channel chan: client.getChannelByUserId(serverId)) 
		{
			item = new MenuItem(chan.getChannelName());
			item.setId(chan.getChannelName());
			item.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e)
				{
					chanTarget = chan;
					channelDropDown.setText(chan.getChannelName());
					if (roleTarget != null)
					{
						chkSpecificSendMessage.setSelected(
								roleTarget.getChannelPermission(chan.getChannelName()).getSendMessage().getAllowed());
						chkSpecificViewChannel.setSelected(
								roleTarget.getChannelPermission(chan.getChannelName()).getViewChannel().getAllowed());
					}
				}
			});
			channelDropDown.getItems().add(item);
		}
    }
    
    @FXML
    void onClickCancel(ActionEvent event) 
    {
    	stage.close();
    }

    @FXML
    void onClickSave(ActionEvent event) 
    {
    	try
    	{
    		ArrayList<Boolean> permission = new ArrayList<Boolean>();
    		for (CheckBox chkbox: checkList)
    		{
    			permission.add(chkbox.isSelected());
    		}
    		if (roleTarget != null)	// if edit role
    		{
        		client.updateRole(roleTarget.getName(), serverId, permission);
        		if (chanTarget != null)
        		{
        			client.updateChannelRole(
        					roleTarget.getName(), chanTarget.getChannelId(), serverId, 
        					chkSpecificViewChannel.isSelected(), chkSpecificSendMessage.isSelected());
        		}
    		}
    		// if create new role
        	else if (newRoleName.getText() == "")	
        	{
        		viewModel.showWarning("Please provide new role name.");
        	}
        	else
        	{
        		client.createRole(serverId, newRoleName.getText(), permission);
        	}
    	}
    	catch (InvalidActionException | RemoteException e)
		{
			viewModel.showError();
		}
    	stage.close();
    }

}
