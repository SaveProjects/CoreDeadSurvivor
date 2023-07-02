package fr.edminecoreteam.core.purchase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.edminecoreteam.core.api.MySQL;

public class PurchaseData 
{
	private String pS;
    
    public PurchaseData(String pS) 
    {
        this.pS = pS;
    }
    
    public void addArticle(int id, String deadLine) 
    {
        	try 
        	{
            	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);  
                Date resultdate = new Date();
                String date = sdf.format(resultdate);
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("INSERT INTO ed_shop_purchase (player_name, article_id, purchase_date, deadline_date) VALUES (?, ?, ?, ?)");
                preparedStatement.setString(1, pS); /*player_name*/
                preparedStatement.setInt(2, id); /*article_id*/
                preparedStatement.setString(3, date); /*purchase_date*/
                preparedStatement.setString(4, deadLine); /*deadline_date*/
                preparedStatement.execute();
                preparedStatement.close();
	        }
	        catch (SQLException e) 
	        {
	            e.printStackTrace();
	        }
    }
    
    public String getArticle(int id) {
        try {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_name FROM ed_shop_purchase WHERE article_id = ? AND player_name = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, pS);
            ResultSet rs = preparedStatement.executeQuery();
            String s = "";
            while (rs.next()) {
                s = rs.getString("player_name");
            }
            preparedStatement.close();
            return s;
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public boolean haesArticle(int id) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_id FROM ed_shop_purchase WHERE player_name = ?");
            preparedStatement.setString(1, pS);
            preparedStatement.setInt(2, id);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
}
