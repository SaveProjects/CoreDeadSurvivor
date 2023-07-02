package fr.edminecoreteam.core.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.edminecoreteam.core.api.MySQL;

public class Data 
{
	private String p;
	private String table;
	
	public Data(String p) {
		this.p = p;
		this.table = "ed_deadsurvivor";
	}
	
	public void createData() 
    {
        if (!hasData()) 
        {
            try 
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("INSERT INTO " + table + " (player_name, player_played_games, player_killer_victoires, player_killer_defaites, player_killer_kills, player_killer_frappes, player_survivor_victoires, player_survivor_defaites, player_survivor_morts, player_survivor_reacteurs, player_survivor_sauver, player_skin_select, player_weapon_select) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, p); /*player_name*/
                preparedStatement.setInt(2, 0); /*player_played_games*/
                preparedStatement.setInt(3, 0); /*player_killer_victoires*/
                preparedStatement.setInt(4, 0); /*player_killer_defaites*/
                preparedStatement.setInt(5, 0); /*player_killer_kills*/
                preparedStatement.setInt(6, 0); /*player_killer_frappes*/
                preparedStatement.setInt(7, 0); /*player_survivor_victoires*/
                preparedStatement.setInt(8, 0); /*player_survivor_defaites*/
                preparedStatement.setInt(9, 0); /*player_survivor_morts*/
                preparedStatement.setInt(10, 0); /*player_survivor_reacteurs*/
                preparedStatement.setInt(11, 0); /*player_survivor_sauver*/
                preparedStatement.setInt(12, 0); /*player_skin_select*/
                preparedStatement.setInt(13, 0); /*player_weapon_select*/
                preparedStatement.execute();
                preparedStatement.close();
            }
            catch (SQLException e) 
            {
                e.toString();
            }
        }
    }
	
	public boolean hasData() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_played_games FROM " + table + " WHERE player_name = ?");
            preparedStatement.setString(1, p);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
        catch (SQLException e) 
        {
            e.toString();
            return false;
        }
    }
	
	public int getPlayedGames() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_played_games FROM " + table + " WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt("player_played_games");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
	
	public int getKillerWin() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_killer_victoires FROM " + table + " WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt("player_killer_victoires");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
	
	public int getSurvivorWin() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_survivor_victoires FROM " + table + " WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt("player_killer_victoires");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
	
	public int getKillerLose() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_killer_defaites FROM " + table + " WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt("player_killer_defaites");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
	
	public int getSurvivorLose() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_survivor_defaites FROM " + table + " WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt("player_survivor_defaites");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
	
	public int getKillerKills() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_killer_kills FROM " + table + " WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt("player_killer_kills");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
	
	public int getKillerFrappes() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_killer_frappes FROM " + table + " WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt("player_killer_frappes");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
	
	public int getSurvivorMorts() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_survivor_morts FROM " + table + " WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt("player_survivor_morts");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
	
	public int getSurvivorReacteurs() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_survivor_reacteurs FROM " + table + " WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt("player_survivor_reacteurs");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
	
	public int getSurvivorSauver() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_survivor_sauver FROM " + table + " WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt("player_survivor_sauver");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
	
	public int getSkin()
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_skin_select FROM " + table + " WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt("player_skin_select");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
	
	public int getWeapon()
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_weapon_select FROM " + table + " WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt("player_weapon_select");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
	
	
	public void addPlayedGames(int amount) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_played_games = player_played_games + ? WHERE player_name = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
	
	public void addKillerVictoires(int amount) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_killer_victoires = player_killer_victoires + ? WHERE player_name = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
	
	public void addSurvivorVictoires(int amount) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_survivor_victoires = player_survivor_victoires + ? WHERE player_name = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
	
	public void addKillerLose(int amount) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_killer_defaites = player_killer_defaites + ? WHERE player_name = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
	
	public void addSurvivorLose(int amount) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_survivor_defaites = player_survivor_defaites + ? WHERE player_name = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
	
	public void addKillerKills(int amount) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_killer_kills = player_killer_kills + ? WHERE player_name = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
	
	public void addKillerFrappes(int amount) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_killer_frappes = player_killer_frappes + ? WHERE player_name = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
	
	public void addSurvivorMorts(int amount) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_survivor_morts = player_survivor_morts + ? WHERE player_name = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
	
	public void addSurvivorReacteurs(int amount) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_survivor_reacteurs = player_survivor_reacteurs + ? WHERE player_name = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
	
	public void addSurvivorSauver(int amount) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_survivor_sauver = player_survivor_sauver + ? WHERE player_name = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
	
	public void setSkin(int amount) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_skin_select = ? WHERE player_name = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
	
	public void setWeapon(int amount) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_weapon_select = ? WHERE player_name = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
	
}
