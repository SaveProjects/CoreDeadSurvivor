package fr.edminecoreteam.core.purchase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.edminecoreteam.core.api.MySQL;

public class PageData 
{
	private String table;
	private String p;
	
	public PageData(String p)
	{
		this.p = p;
		this.table = "ed_shop_purchase";
	}
	
	public int getPageNumber(String type) {
		int Page = 1;
		int PlayerOnPage = 0;
	        try {
	            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT article_id FROM " + table + " WHERE player_name = ?");
	            ps.setString(1, p);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	            	StoreData data = new StoreData(rs.getInt("article_id"));
	                if (PlayerOnPage == 10)
	                {
	                	if (data.getArticleSubType().equalsIgnoreCase(type))
	                	{
	                		PlayerOnPage = 0;
		                	++Page;
	                	}
	                }
	                else 
	                {
	                	if (data.getArticleSubType().equalsIgnoreCase(type))
	                	{
	                		++PlayerOnPage;
	                	}
	                }
	            }
	            ps.close();
	            return Page;
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 0;
	}
	
	public List<Integer> getForPage(int Page, String type) {
		int friendPerPage = 10;
		int friendOnPage = 0;
		int sqlPage = 1;
		List<Integer> friendList = new ArrayList<Integer>();
		List<Integer> friendPageList = new ArrayList<Integer>();
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT article_id FROM " + table + " WHERE player_name = ?");
            ps.setString(1, p);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	StoreData data = new StoreData(rs.getInt("article_id"));
                if (sqlPage != Page)
                {
                	if (friendPerPage != friendOnPage)
                    {
                		if (data.getArticleSubType().equalsIgnoreCase(type))
                		{
                			friendList.add(rs.getInt("article_id"));
                        	++friendOnPage;
                		}
                    }
                    if(friendPerPage == friendOnPage)
                    {
                    	if (data.getArticleSubType().equalsIgnoreCase(type))
                		{
                    		friendOnPage = 0;
                        	++sqlPage;
                        	friendList.add(rs.getInt("article_id"));
                        	++friendOnPage;
                		}
                    }
                }
                else if (sqlPage == Page)
                {
                	if (friendPerPage != friendOnPage)
                    {
                		if (data.getArticleSubType().equalsIgnoreCase(type))
                		{
                			friendPageList.add(rs.getInt("article_id"));
                        	++friendOnPage;
                		}
                    }
                    if(friendPerPage == friendOnPage)
                    {
                    	if (data.getArticleSubType().equalsIgnoreCase(type))
                		{
                    		friendPageList.add(rs.getInt("article_id"));
                        	++friendOnPage;
                		}
                    }
                }
            }
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return friendPageList;
	}
}
