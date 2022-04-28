package models;

import java.rmi.RemoteException;

import concord.Server;

public interface ViewTransitionModelInterface
{
	public void showLogin();
	public void showContent() throws RemoteException;
	public void showUser();
	public void showServer(Server s);
	public void showDc();
}
