package models;

import concord.Server;

public interface ViewTransitionModelInterface
{
	public void showLogin();
	public void showContent();
	public void showUser();
	public void showServer(Server s);
	public void showDc();
	public void showCreate();
	public void showError();
	public void showWarning(String text);
}
