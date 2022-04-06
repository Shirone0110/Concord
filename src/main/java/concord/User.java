package concord;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1196493987973256632L;
	private String profileData;
	private String userName;
	private String realName;
	private String password;
	private String profilePic; 
	private int userId;
	private ArrayList <User> blocks;
	private boolean isOnline;
	
	public User(String username, String realname, String pwd, int id)
	{
		userName = username;
		realName = realname;
		password = pwd;
		userId = id;
		profileData = "";
		profilePic = "";
		blocks = new ArrayList<User>();
		isOnline = true;
	}
	
	public User()
	{
		new User("default", "default", "default", 0);
	}

	public String getProfileData()
	{
		return profileData;
	}

	public void setProfileData(String data)
	{
		profileData = data;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String username)
	{
		userName = username;
	}

	public String getRealName()
	{
		return realName;
	}

	public void setRealName(String realname)
	{
		realName = realname;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String pwd)
	{
		password = pwd;
	}

	public String getProfilePic()
	{
		return profilePic;
	}

	public void setProfilePic(String pfp)
	{
		profilePic = pfp;
	}

	public int getUserId()
	{
		return userId;
	}
	
	public void setUserId(int id)
	{
		userId = id;
	}

	public ArrayList<User> getBlocks()
	{
		return blocks;
	}
	
	public void setBlocks(ArrayList<User> b)
	{
		blocks = b;
	}

	public void addBlock(User user)
	{
		blocks.add(user);
	}
	
	public void removeBlock(User user)
	{
		blocks.remove(user);
	}

	public boolean isOnline()
	{
		return isOnline;
	}

	public void setOnline(boolean status)
	{
		isOnline = status;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this) return true;
		if (!(obj instanceof User)) return false;
		User u = (User) obj;
		if (userId == u.getUserId()) return true;
		return false;
	}
	
}
