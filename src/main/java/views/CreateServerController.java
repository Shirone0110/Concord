package views;

import java.rmi.RemoteException;

import concord.ConcordClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.ViewTransitionModelInterface;

public class CreateServerController 
{
    @FXML
    private TextField newNameTextField;

    @FXML
    private RadioButton radbtnNo;

    @FXML
    private RadioButton radbtnYes;

    private ToggleGroup privGroup;
    Stage stage;
    ConcordClient client;
    
    public void setModel(Stage s, ConcordClient c)
    {
    	stage = s;
    	client = c;
    	privGroup = new ToggleGroup();
    	radbtnYes.setToggleGroup(privGroup);
    	radbtnNo.setToggleGroup(privGroup);
    	radbtnYes.setSelected(true);
    }
    
    @FXML
    void onClickCancel(ActionEvent event) 
    {
    	stage.close();
    }

    @FXML
    void onClickCreate(ActionEvent event) throws RemoteException 
    {
    	client.createServer(client.getUser().getUserId(), newNameTextField.getText(), radbtnYes.isSelected());
    	stage.close();
    }
}
