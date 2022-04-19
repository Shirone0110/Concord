package models;

import java.rmi.RemoteException;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public interface ViewTransitionModelInterface
{
	public void showLogin();
	public void showContent() throws RemoteException;
	public void showUser();
	public void showServer();
	public void showDc();
	public ObservableList<Label> getServers();
}
