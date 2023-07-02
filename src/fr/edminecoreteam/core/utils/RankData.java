package fr.edminecoreteam.core.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.bukkit.entity.Player;

import fr.edminecoreteam.core.api.MySQL;

public class RankData 
{
	private Player p;
	
    public RankData(Player p) 
    {
        this.p = p;
    }
    
    public void createRank() 
    {
        if (!hasRank()) 
        {
        	try 
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("INSERT INTO ed_ranks (player_name, player_uuid, player_rank_id, player_rank_name, player_rank_type, player_modulable_rank, player_rank_purchase_date, player_rank_deadline_date, player_rank_palier) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, p.getName()); /*player_name*/
                preparedStatement.setString(2, p.getUniqueId().toString().replaceAll("-", "")); /*player_uuid*/
                preparedStatement.setInt(3, 0); /*player_rank_id*/
                preparedStatement.setString(4, "aucun"); /*player_rank_name*/
                preparedStatement.setString(5, "static"); /*player_rank_type*/
                preparedStatement.setInt(6, 0); /*player_modulable_rank*/
                preparedStatement.setString(7, null); /*player_rank_purchase_date*/
                preparedStatement.setString(8, null); /*player_rank_deadline_date*/
                preparedStatement.setString(9, null); /*player_rank_deadline_date*/
                preparedStatement.execute();
                preparedStatement.close();
            }
            catch (SQLException e) 
            {
                e.toString();
            }
        }
    }
    
    public void setRank(String type, int rankID, String rankName, int modulRank, int monthNumber) 
    {
		if (type.equalsIgnoreCase("tempo"))
		{
			if (monthNumber >= 1)
			{
				if(rankID >= 1 && rankID <= 5)
				{
					Calendar cal = Calendar.getInstance();
                	cal.add(Calendar.DATE, 1);
                	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);  
                	
                    Date resultpurchasedate = cal.getTime();
                    String purchaseDate = sdf.format(resultpurchasedate);
                    
                    cal.add(Calendar.MONTH, monthNumber);
					Date resultdeadlinedate = cal.getTime();
                    String deadlineDate = sdf.format(resultdeadlinedate);
					
					updateRankType("tempo");
					updateRankID(rankID);
					updateRankModule(0);
					updateDeadLineDate(deadlineDate);
                    updatePurchaseDate(purchaseDate);
					
				}
			}
			else
			{
				p.sendMessage("§cErreur, veuillez préciser un ou plusieurs mois pour le groupe temporaire...");
				return;
			}
		}
		if (type.equalsIgnoreCase("static"))
		{
			if (monthNumber == 0)
			{
				if(rankID == 0 || rankID == 6 || rankID == 7 || rankID == 8)
				{
					updateRankType("static");
					updateRankID(rankID);
					updateRankModule(0);
					updateDeadLineDate(null);
                    updatePurchaseDate(null);
				}
			}
			else
			{
				p.sendMessage("§cErreur, veuillez préciser un grade du groupe static... (0, 6-8)");
				return;
			}
		}
		if (type.equalsIgnoreCase("modul"))
		{
			if (monthNumber >= 1)
			{
				if (rankID == 9)
				{
					if (modulRank >= 1 && modulRank <= 5)
					{
						Calendar cal = Calendar.getInstance();
	                	cal.add(Calendar.DATE, 1);
	                	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);  
	                	
	                    Date resultpurchasedate = cal.getTime();
	                    String purchaseDate = sdf.format(resultpurchasedate);
	                    
	                    cal.add(Calendar.MONTH, monthNumber);
						Date resultdeadlinedate = cal.getTime();
	                    String deadlineDate = sdf.format(resultdeadlinedate);
						
						updateRankType("modul");
						updateRankID(rankID);
						updateRankModule(modulRank);
						updateDeadLineDate(deadlineDate);
	                    updatePurchaseDate(purchaseDate);
					}
				}
				else
				{
					p.sendMessage("§cErreur, veuillez préciser un grade du groupe modul... (9)");
					return;
				}
			}
			else
			{
				if (rankID == 9)
				{
					if (modulRank == 0)
					{
						updateRankType("modul");
						updateRankID(rankID);
						updateRankModule(modulRank);
						updateDeadLineDate(null);
	                    updatePurchaseDate(null);
					}
					else
					{
						p.sendMessage("§cErreur, vous pouvez qu'appliquer la grade joueur avec aucuns moins d'échéance préciser...");
						return;
					}
				}
				else
				{
					p.sendMessage("§cErreur, veuillez préciser un grade du groupe modul... (9)");
					return;
				}
			}
		}
		if (type.equalsIgnoreCase("staff"))
		{
			if (monthNumber != 0)
			{
				if(modulRank == 10 || modulRank == 11 || modulRank == 12 || modulRank == 13 || modulRank == 14 || modulRank == 15 || modulRank == 16)
				{
					Calendar cal = Calendar.getInstance();
                	cal.add(Calendar.DATE, 1);
                	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);  
                	
                    Date resultpurchasedate = cal.getTime();
                    String purchaseDate = sdf.format(resultpurchasedate);
					
					updateRankType("staff");
					updateRankModule(modulRank);
					
					if (rankID <= 5 && rankID != 0)
					{
						updateRankID(rankID);
						if (getPurchaseDate() != null)
						{
							cal.add(Calendar.MONTH, monthNumber);
							Date resultdeadlinedate = cal.getTime();
		                    String deadlineDate = sdf.format(resultdeadlinedate);
		                    updateDeadLineDate(deadlineDate);
						}
						else
						{
							cal.add(Calendar.MONTH, monthNumber);
							Date resultdeadlinedate = cal.getTime();
		                    String deadlineDate = sdf.format(resultdeadlinedate);
		                    updateDeadLineDate(deadlineDate);
		                    updatePurchaseDate(purchaseDate);
						}
					}
					else if (rankID == 0 || rankID == 6 || rankID == 7 || rankID == 8)
					{
						updateRankID(rankID);
					}
				}
				else
				{
					p.sendMessage("§cErreur, veuillez préciser un grade du groupe staff... (10-16)");
					return;
				}
			}
			else
			{
				if(modulRank == 10 || modulRank == 11 || modulRank == 12 || modulRank == 13 || modulRank == 14 || modulRank == 15 || modulRank == 16)
				{
					updateRankType("staff");
					updateRankModule(modulRank);
					
					if (rankID <= 5 && rankID != 0)
					{
						updateRankID(rankID);
						if (getPurchaseDate() != null)
						{
		                    updateDeadLineDate(null);
						}
						else
						{
		                    updateDeadLineDate(null);
		                    updatePurchaseDate(null);
						}
					}
					else if (rankID == 0 || rankID == 6 || rankID == 7 || rankID == 8)
					{
						updateRankID(rankID);
					}
				}
				else
				{
					p.sendMessage("§cErreur, veuillez préciser un grade du groupe staff... (10-16)");
					return;
				}
			}
		}
	}
    
    public boolean hasRank()
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_id FROM ed_ranks WHERE player_name = ?");
            preparedStatement.setString(1, p.getName());
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
        catch (SQLException e) 
        {
            e.toString();
            return false;
        }
    }
    
    public int getRankID() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_id FROM ed_ranks WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
            int power = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                power = rs.getInt("player_rank_id");
            }
            preparedStatement.close();
            return power;
        }
        catch (SQLException e) 
        {
        	e.toString();
            return 0;
        }
    }
    
    public void updateRankID(int rankID) 
    {
    	if (hasRank()) 
        {
            try 
            {
            	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_ranks SET player_rank_id = ? WHERE player_name = ?");
                preparedStatement.setInt(1, rankID);
                preparedStatement.setString(2, p.getName());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            catch (SQLException e) 
            {
                e.toString();
            }
        }
	}
    
    public void updateUUIDRank() 
    {
    	if (hasRank()) 
        {
            try 
            {
            	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_ranks SET player_uuid = ? WHERE player_name = ?");
                preparedStatement.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
                preparedStatement.setString(2, p.getName());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            catch (SQLException e) 
            {
                e.toString();
            }
        }
	}
    
    public void updateRankType(String type) 
    {
    	if (hasRank()) 
        {
            try 
            {
            	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_ranks SET player_rank_type = ? WHERE player_name = ?");
                preparedStatement.setString(1, type);
                preparedStatement.setString(2, p.getName());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            catch (SQLException e) 
            {
                e.toString();
            }
        }
	}
    
    public String getRankType() 
    {
    	if (hasRank()) 
        {
            try 
            {
            	 PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_type FROM ed_ranks WHERE player_uuid = ?");
                 preparedStatement.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
                 String response = "";
                 ResultSet rs = preparedStatement.executeQuery();
                 while (rs.next()) 
                 {
                	 response = rs.getString("player_rank_type");
                 }
                 preparedStatement.close();
                 return response;
            }
            catch (SQLException e) 
            {
                e.toString();
            }
        }
    	return "";
	}
    
    public void updatePurchaseDate(String date) 
    {
    	if (hasRank()) 
        {
            try 
            {
            	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_ranks SET player_rank_purchase_date = ? WHERE player_name = ?");
                preparedStatement.setString(1, date);
                preparedStatement.setString(2, p.getName());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            catch (SQLException e) 
            {
                e.toString();
            }
        }
	}
    
    public void updateDeadLineDate(String date) 
    {
    	if (hasRank()) 
        {
            try 
            {
            	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_ranks SET player_rank_deadline_date = ? WHERE player_name = ?");
                preparedStatement.setString(1, date);
                preparedStatement.setString(2, p.getName());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            catch (SQLException e) 
            {
                e.toString();
            }
        }
	}
    
    public String getPurchaseDate() 
    {
    	if (hasRank()) 
        {
            try 
            {
            	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_purchase_date FROM ed_ranks WHERE player_uuid = ?");
                preparedStatement.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
                String response = "";
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) 
                {
                	response = rs.getString("player_rank_purchase_date");
                }
                preparedStatement.close();
                return response; 
           }
           catch (SQLException e) 
           {
               e.toString();
           }
       }
    	return "";
	}
    
    public String getDeadLineDate() 
    {
    	if (hasRank()) 
        {
            try 
            {
            	 PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_deadline_date FROM ed_ranks WHERE player_uuid = ?");
                 preparedStatement.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
                 String response = "";
                 ResultSet rs = preparedStatement.executeQuery();
                 while (rs.next()) 
                 {
                	 response = rs.getString("player_rank_deadline_date");
                 }
                 preparedStatement.close();
                 return response; 
            }
            catch (SQLException e) 
            {
                e.toString();
            }
        }
    	return "";
	}
    
    public void updateRankModule(int module)
    {
    	if (hasRank()) 
        {
            try 
            {
            	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_ranks SET player_modulable_rank = ? WHERE player_name = ?");
                preparedStatement.setInt(1, module);
                preparedStatement.setString(2, p.getName());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            catch (SQLException e) 
            {
                e.toString();
            }
        }
	}
    
    public int getRankModule() 
    {
    	if (hasRank()) 
        {
            try 
            {
            	 PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_modulable_rank FROM ed_ranks WHERE player_uuid = ?");
                 preparedStatement.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
                 int response = 0;
                 ResultSet rs = preparedStatement.executeQuery();
                 while (rs.next()) 
                 {
                	 response = rs.getInt("player_modulable_rank");
                 }
                 preparedStatement.close();
                 return response;
            }
            catch (SQLException e) 
            {
                e.toString();
            }
        }
    	return 0;
	}
}
