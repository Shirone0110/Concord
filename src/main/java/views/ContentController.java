package views;

import concord.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import models.ConcordModel;
import models.ViewTransitionModelInterface;

public class ContentController
{
	ViewTransitionModelInterface model;
	ConcordModel concordModel;
	
    @FXML
    private ListView<Server> serverListView;
	
	public void setModel(ViewTransitionModelInterface m, ConcordModel cModel)
	{
		model = m;
		concordModel = cModel;
		serverListView.setEditable(true);
		
		serverListView.setCellFactory(new Callback<ListView<Server>, ListCell<Server>>()
				{
					@Override
					public ListCell<Server> call(ListView<Server> param)
					{	
						return new ServerCell(param);
					}
				});
		
		
		serverListView.setItems(concordModel.getServers());
	}
	
	/*@FXML
    void onClickSettings(ActionEvent event) 
	{
		model.showUser();
    }
	
	@FXML
    void onClickDC(MouseEvent event) 
	{
		model.showDc();
    }

    @FXML
    void onClickServer(ActionEvent event) 
    {
    	model.showServer();
    }*/

}
