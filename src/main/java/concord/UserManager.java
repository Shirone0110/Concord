package concord;

import java.util.ArrayList;

public class UserManager
{
	private ArrayList <User> userList;
	private int numberOfUser;
	
	public UserManager()
	{
		userList = new ArrayList<User>();
		numberOfUser = 0;
	}
	
	public ArrayList<User> getUserList()
	{
		return userList;
	}
	
	public void setUserList(ArrayList<User> u)
	{
		userList = u;
	}
	
	/**
	 * @return the numberOfUser
	 */
	public int getNumberOfUser()
	{
		return numberOfUser;
	}

	/**
	 * @param numberOfUser the numberOfUser to set
	 */
	public void setNumberOfUser(int numberOfUser)
	{
		this.numberOfUser = numberOfUser;
	}

	public User addUser(String username, String realname, String pwd)
	{
		User newUser = new User(username, realname, pwd, numberOfUser);
		userList.add(newUser);
		numberOfUser++;
		return newUser;
	}
	
	public void removeUser(int id)
	{
		userList.remove(id);
	}
	
	public User findUserById(int id) 
	{
		for (User u: userList)
		{
			if (u.getUserId() == id) return u;
		}

		return null;
	}
	
	public void verify(String username, String password) 
			throws InvalidCredentialException
	{
		boolean valid = false;
		for (User u: userList)
		{
			if (u.getUserName().equals(username) && u.getPassword().equals(password))
			{
				valid = true;
				break;
			}
		}
		if (!valid) throw new InvalidCredentialException();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserManager other = (UserManager) obj;
		if (numberOfUser != other.numberOfUser) 
			return false;
		for (User u: userList)
		{
			if (!other.contains(u)) 
				return false;
		}
		return true;
	}
	
	public boolean contains(User user)
	{
		for (User u: userList)
		{
			if (u.equals(user)) 
				return true;
		}
		return false;
	}
	
}
