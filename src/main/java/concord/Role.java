package concord;

public class Role
{
	private boolean modifyMember;
	private boolean modifyModerator;
	private boolean modifyChannel;
	private boolean modifyAdmin; 
	private boolean modifyRole;
	private String permission;
	
	public Role(String p)
	{
		modifyMember = p.charAt(0) == '1' ? true : false;
		modifyModerator = p.charAt(1) == '1' ? true : false;
		modifyChannel = p.charAt(2) == '1' ? true : false;
		modifyAdmin = p.charAt(3) == '1' ? true : false;
		modifyRole = p.charAt(4) == '1' ? true : false;
		permission = p;
	}
	
	public Role()
	{
		this("00000");
	}

	/**
	 * @return the addMember
	 */
	public boolean isModifyMember()
	{
		return modifyMember;
	}

	/**
	 * @param addMember the addMember to set
	 */
	public void setModifyMember(boolean addMember)
	{
		this.modifyMember = addMember;
	}
	
	/**
	 * @return the addModerator
	 */
	public boolean isModifyModerator()
	{
		return modifyModerator;
	}

	/**
	 * @param addModerator the addModerator to set
	 */
	public void setModifyModerator(boolean addModerator)
	{
		this.modifyModerator = addModerator;
	}

	/**
	 * @return the addChannel
	 */
	public boolean isModifyChannel()
	{
		return modifyChannel;
	}

	/**
	 * @param addChannel the addChannel to set
	 */
	public void setModifyChannel(boolean addChannel)
	{
		this.modifyChannel = addChannel;
	}

	/**
	 * @return the addAdmin
	 */
	public boolean isModifyAdmin()
	{
		return modifyAdmin;
	}

	/**
	 * @param addAdmin the addAdmin to set
	 */
	public void setModifyAdmin(boolean addAdmin)
	{
		this.modifyAdmin = addAdmin;
	}

	public boolean isModifyRole()
	{
		return modifyRole;
	}

	public void setModifyRole(boolean changeRole)
	{
		this.modifyRole = changeRole;
	}
	
	public String getPermission()
	{
		return permission;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this) return true;
		if (!(obj instanceof Role)) return false;
		Role r = (Role) obj;
		if (permission == r.getPermission()) return true;
		return false;
	}
}
