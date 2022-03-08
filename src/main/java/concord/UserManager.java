package concord;

import java.util.ArrayList;

public class UserManager
{
	ArrayList <User> userList;
	int numberOfUser;
	
	public UserManager()
	{
		userList = new ArrayList<User>();
		numberOfUser = 0;
	}
	
	public void addUser(String username, String realname, String pwd)
	{
		userList.add(new User(username, realname, pwd, numberOfUser));
		numberOfUser++;
	}
	
	public void removeUser(int id)
	{
		userList.remove(id);
	}
}
