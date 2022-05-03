package views;

import java.io.IOException;

import concord.Server;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import models.ViewTransitionModel;

public class ServerCell extends ListCell<Server>
{
	ServerCellController cont;
	Node node;
	
	public ServerCell(ListView<Server> view)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewTransitionModel.class
			  .getResource("../views/ServerCellView.fxml"));
		
		try
		{
			node = loader.load();
			cont = loader.getController();
			//cont.setModel(this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	protected void updateItem(Server s, boolean empty)
	{
		super.updateItem(s, empty);//really important! always keep!
		
		//System.out.println(s);
		//System.out.println(empty);
		if(empty || s == null)		
		{
			this.setGraphic(null);		
		}
		else
		{
			cont.setModel(this);
			this.setGraphic(node);
		}
	}
}
