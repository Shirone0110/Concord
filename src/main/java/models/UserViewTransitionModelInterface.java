package models;

public interface UserViewTransitionModelInterface
{
	public void showUserInfo();
	public void showBlock();
	void showError();
	void showWarning(String text);
}
