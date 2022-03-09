package concord;

public class Role
{
	private boolean addMember;
	private boolean addModerator;
	private boolean addChannel;
	private boolean addAdmin; 
	private boolean removeMember;
	private boolean removeModerator;
	private boolean removeAdmin;
	private boolean removeChannel;
	private boolean changeRole;
	
	public Role(String p)
	{
		addMember = p.charAt(0) == '1' ? true : false;
		addModerator = p.charAt(1) == '1' ? true : false;
		addChannel = p.charAt(2) == '1' ? true : false;
		addAdmin = p.charAt(3) == '1' ? true : false;
		removeMember = p.charAt(4) == '1' ? true : false;
		removeModerator = p.charAt(5) == '1' ? true : false;
		removeAdmin = p.charAt(6) == '1' ? true : false;
		removeChannel = p.charAt(7) == '1' ? true : false;
		changeRole = p.charAt(8) == '1' ? true : false;
	}
	
	public Role()
	{
		this("000000000");
	}

	/**
	 * @return the addMember
	 */
	public boolean isAddMember()
	{
		return addMember;
	}

	/**
	 * @param addMember the addMember to set
	 */
	public void setAddMember(boolean addMember)
	{
		this.addMember = addMember;
	}
	
	/**
	 * @return the addModerator
	 */
	public boolean isAddModerator()
	{
		return addModerator;
	}

	/**
	 * @param addModerator the addModerator to set
	 */
	public void setAddModerator(boolean addModerator)
	{
		this.addModerator = addModerator;
	}

	/**
	 * @return the addChannel
	 */
	public boolean isAddChannel()
	{
		return addChannel;
	}

	/**
	 * @param addChannel the addChannel to set
	 */
	public void setAddChannel(boolean addChannel)
	{
		this.addChannel = addChannel;
	}

	/**
	 * @return the addAdmin
	 */
	public boolean isAddAdmin()
	{
		return addAdmin;
	}

	/**
	 * @param addAdmin the addAdmin to set
	 */
	public void setAddAdmin(boolean addAdmin)
	{
		this.addAdmin = addAdmin;
	}

	/**
	 * @return the removeMember
	 */
	public boolean isRemoveMember()
	{
		return removeMember;
	}

	/**
	 * @param removeMember the removeMember to set
	 */
	public void setRemoveMember(boolean removeMember)
	{
		this.removeMember = removeMember;
	}

	/**
	 * @return the removeModerator
	 */
	public boolean isRemoveModerator()
	{
		return removeModerator;
	}

	/**
	 * @param removeModerator the removeModerator to set
	 */
	public void setRemoveModerator(boolean removeModerator)
	{
		this.removeModerator = removeModerator;
	}

	/**
	 * @return the removeAdmin
	 */
	public boolean isRemoveAdmin()
	{
		return removeAdmin;
	}

	/**
	 * @param removeAdmin the removeAdmin to set
	 */
	public void setRemoveAdmin(boolean removeAdmin)
	{
		this.removeAdmin = removeAdmin;
	}

	/**
	 * @return the removeChannel
	 */
	public boolean isRemoveChannel()
	{
		return removeChannel;
	}

	/**
	 * @param removeChannel the removeChannel to set
	 */
	public void setRemoveChannel(boolean removeChannel)
	{
		this.removeChannel = removeChannel;
	}

	public boolean isChangeRole()
	{
		return changeRole;
	}

	public void setChangeRole(boolean changeRole)
	{
		this.changeRole = changeRole;
	}
	
	
}
