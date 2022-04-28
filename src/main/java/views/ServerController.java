package views;

import concord.Channel;
import concord.ConcordClient;
import concord.Message;
import concord.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.ConcordModel;
import models.ViewTransitionModel;

public class ServerController
{
	ConcordClient client;
	ConcordModel concordModel;
	ViewTransitionModel model;
	
	@FXML
    private ListView<Channel> channelListView;

    @FXML
    private Label channelNameLabel;

    @FXML
    private ListView<Message> messageListView;

    @FXML
    private TextField messageTextField;

    @FXML
    private Label userNameLabel;

    @FXML
    private ListView<User> usersListView;
	
	public void setModel(ViewTransitionModel vModel, ConcordModel m, ConcordClient c)
	{
		model = vModel;
		client = c;
		concordModel = m;
		userNameLabel.setText(client.getUser().getUserName());
		channelListView.setItems(m.getChannels());
	}

    @FXML
    void btnPinsClicked(ActionEvent event) 
    {

    }

    @FXML
    void btnSettingsClicked(ActionEvent event) 
    {
    	model.showUser();
    }

    @FXML
    void channelListViewClicked(MouseEvent event) 
    {
    	
    }
}
