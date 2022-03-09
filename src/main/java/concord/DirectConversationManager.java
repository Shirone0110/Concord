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
	
	public void createDc(User a, User b)
	{
		DirectConversation dc = new DirectConversation(numberOfDc);
		dc.addUser(a);
		dc.addUser(b);
		dcList.add(dc);
		numberOfDc++;
	}
	
	public void deleteDc(int id)
	{
		dcList.remove(id);
	}
	
	public ArrayList<DirectConversation> getDcList()
	{
		return dcList;
	}
	
	public ArrayList<DirectConversation> getPastConversation(User u)
	{
		ArrayList<DirectConversation> ans = new ArrayList<DirectConversation>();
		for (int i = 0; i < dcList.size(); i++)
		{
			DirectConversation tmp = dcList.get(i);
			if (tmp.users.contains(u)) ans.add(tmp);
		}
		return ans;
	}
}
