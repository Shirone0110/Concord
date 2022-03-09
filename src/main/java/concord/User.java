package concord;

import java.util.ArrayList;

public class User
{
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

	public ArrayList<User> getBlocks()
	{
		return blocks;
	}

	public void addBlock(User user)
	{
		blocks.add(user);
	}
	
	public void removeBlock(User user)
	{
		blocks.remove(user);
	}

	public boolean getStatus()
	{
		return isOnline;
	}

	public void setStatus(boolean status)
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
