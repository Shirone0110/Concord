package models;

import java.rmi.RemoteException;

public interface ViewTransitionModelInterface
{
	public void showLogin();
	public void showContent() throws RemoteException;
	public void showUser();
	public void showServer();
	public void showDc();
}
