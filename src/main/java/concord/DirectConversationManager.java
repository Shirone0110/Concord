package concord;

import java.util.ArrayList;

public class DirectConversationManager
{
	private ArrayList <DirectConversation> dcList;
	private int numberOfDc;
	
	public DirectConversationManager()
	{
		dcList = new ArrayList<DirectConversation>();
		numberOfDc = 0;
	}
	
	public DirectConversation createDc(User a, User b)
	{
		DirectConversation dc = new DirectConversation(numberOfDc);
		dc.addUser(a);
		dc.addUser(b);
		dcList.add(dc);
		numberOfDc++;
		return dc;
	}
	
	public DirectConversation findDcById(int id)
	{
		for (DirectConversation dc: dcList)
		{
			if (dc.getDirectConvoId() == id) return dc;
		}
		return null;
	}
	
	public void deleteDc(int id)
	{
		dcList.remove(id);
	}
	
	public ArrayList<DirectConversation> getDcList()
	{
		return dcList;
	}
	
	public void setDcList(ArrayList<DirectConversation> l)
	{
		dcList = l;
	}
	
	/**
	 * @return the numberOfDc
	 */
	public int getNumberOfDc()
	{
		return numberOfDc;
	}

	/**
	 * @param numberOfDc the numberOfDc to set
	 */
	public void setNumberOfDc(int numberOfDc)
	{
		this.numberOfDc = numberOfDc;
	}

	public ArrayList<DirectConversation> getPastConversation(User u)
	{
		ArrayList<DirectConversation> ans = new ArrayList<DirectConversation>();
		for (int i = 0; i < dcList.size(); i++)
		{
			DirectConversation tmp = dcList.get(i);
			if (tmp.getUsers().contains(u)) ans.add(tmp);
		}
		ans.sort((dc1, dc2) -> dc1.getLastTimestamp().compareTo(dc2.getLastTimestamp()));
		return ans;
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
		
		DirectConversationManager other = (DirectConversationManager) obj;
		
		if (numberOfDc != other.numberOfDc) return false;
		
		for (DirectConversation dc: dcList)
		{
			if (!other.contains(dc)) 
				return false;
		}
		return true;
	}
	
	public boolean contains(DirectConversation dcOther)
	{
		for (DirectConversation dc: dcList)
		{
			if (dc.equals(dcOther)) 
				return true;
		}
		return false;
	}
}
