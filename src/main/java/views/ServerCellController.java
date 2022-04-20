package views;

import concord.Server;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ServerCellController
{
	ServerCell model;
	
	@FXML
    private Label serverNameLabel;
	
	public void setModel(ServerCell model)
	{
		System.out.println(model);
		this.model = model;
		Server s = model.getItem();
		if (s != null)
		{
			System.out.println("a" + s.getServerName());
			//serverNameLabel.textProperty().set(s.getServerName());
			serverNameLabel.textProperty().bindBidirectional(new SimpleStringProperty(model.getItem().getServerName()));
		}
	}
}
