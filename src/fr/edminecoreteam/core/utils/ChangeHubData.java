package fr.edminecoreteam.core.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.edminecoreteam.core.Core;
import fr.edminecoreteam.core.api.MySQL;

public class ChangeHubData 
{
	private String table;
	private String group;
	private String serverName;
	private Core core;
	
	public ChangeHubData(String group)
	{
		this.serverName = group;
		this.group = group;
		this.table = "ed_servers";
		this.core = Core.getInstance();
	}
	
	public List<String> getServer() 
    {
        try 
        {
        	String response;
        	List<String> serverList = new ArrayList<String>();
    		for(int i = 1; i < core.srvNumber + 1; i++)
    		{
    			if (i == 0) { return null; }
    			int ServerNumber = i;
    			response = group + ServerNumber;
    			PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT server_name FROM " + table + " WHERE server_name = ? AND server_status = '1'");
                preparedStatement.setString(1, response);
                ResultSet rs = preparedStatement.executeQuery();
                String result = "";
                while (rs.next()) 
                {
                	result = rs.getString("server_name");
                	serverList.add(result);
                }
                preparedStatement.close();
    		}
            return serverList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public int getServerPageNumber() {
		int Page = 1;
		int ServerOnPage = 0;
	        try {
	        	String response;
	        	for(int i = 1; i < core.srvNumber + 1; i++)
	    		{
	    			if (i == 0) { return 0; }
	    			int ServerNumber = i;
	    			response = group + ServerNumber;
	    			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT server_name FROM " + table + " WHERE server_name = ? AND server_status = '1'");
	    			ps.setString(1, response);
		            ResultSet rs = ps.executeQuery();
		            while (rs.next()) {
		                if (ServerOnPage == 10)
		                {
		                	ServerOnPage = 0;
		                	++Page;
		                }
		                else 
		                {
		                	++ServerOnPage;
		                }
		            }
		            ps.close();
	    		}
	            return Page;
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 0;
	}
	
	public List<String> getServerForPage(int Page) {
		int serverPerPage = 10;
		int serverOnPage = 0;
		int sqlPage = 1;
		List<String> serverList = new ArrayList<String>();
		List<String> serverPageList = new ArrayList<String>();
        try {
        	String response;
        	for(int i = 1; i < core.srvNumber + 1; i++)
    		{
    			if (i == 0) { return null; }
    			int ServerNumber = i;
    			response = group + ServerNumber;
    			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT server_name FROM " + table + " WHERE server_name = ? AND server_status = '1'");
                ps.setString(1, response);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (sqlPage != Page)
                    {
                    	if (serverPerPage != serverOnPage)
                        {
                    		serverList.add(rs.getString("server_name"));
                        	++serverOnPage;
                        }
                        if(serverPerPage == serverOnPage)
                        {
                        	serverOnPage = 0;
                        	++sqlPage;
                        	serverList.add(rs.getString("server_name"));
                        	++serverOnPage;
                        }
                    }
                    else if (sqlPage == Page)
                    {
                    	if (serverPerPage != serverOnPage)
                        {
                    		serverPageList.add(rs.getString("server_name"));
                        	++serverOnPage;
                        }
                        if(serverPerPage == serverOnPage)
                        {
                        	serverPageList.add(rs.getString("server_name"));
                        	++serverOnPage;
                        }
                    }
                }
                ps.close();
    		}
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return serverPageList;
	}
	
	public String getServerMotd() 
    {
        try 
        {
    		PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT server_motd FROM " + table + " WHERE server_name = ?");
            preparedStatement.setString(1, serverName);
            ResultSet rs = preparedStatement.executeQuery();
            String result = "";
            while (rs.next()) 
            {
                result = rs.getString("server_motd");
            }
            preparedStatement.close();
            return result;
    	}
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return "";
    }
	
	public int getServerOnlines() 
    {
        try 
        {
    		PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT server_onlines FROM " + table + " WHERE server_name = ?");
            preparedStatement.setString(1, serverName);
            ResultSet rs = preparedStatement.executeQuery();
            int result = 0;
            while (rs.next()) 
            {
                result = rs.getInt("server_onlines");
            }
            preparedStatement.close();
            return result;
    	}
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return 0;
    }
	
	public int getServerID() 
    {
        try 
        {
    		PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT server_id FROM " + table + " WHERE server_name = ?");
            preparedStatement.setString(1, serverName);
            ResultSet rs = preparedStatement.executeQuery();
            int result = 0;
            while (rs.next()) 
            {
                result = rs.getInt("server_id");
            }
            preparedStatement.close();
            return result;
    	}
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return 0;
    }
	
	public void setStatus(int status) {
        try {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_servers SET server_status = ? WHERE server_name = ?");
            preparedStatement.setInt(1, status);
            preparedStatement.setString(2, serverName);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void setMOTD(String motd) {
        try {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_servers SET server_motd = ? WHERE server_name = ?");
            preparedStatement.setString(1, motd);
            preparedStatement.setString(2, serverName);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
}
