package fr.edminecoreteam.core.purchase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

import fr.edminecoreteam.core.api.MySQL;

public class StoreData 
{
	protected static String table;
	
	private String article_type;
	private String article_name;
	private String type_of_pay;
	private Float article_price;
	private int article_rarity;
	private String article_item;
	private int randomID;
	private int article_id;
	private String article_status;
	private int article_page;
	
    public StoreData(String article_type, String article_name, String type_of_pay, Float article_price, int article_rarity, String article_item) 
    {
        this.article_type = article_type;
        this.article_name = article_name;
        this.type_of_pay= type_of_pay;
        this.article_price = article_price;
        this.article_rarity = article_rarity;
        this.article_item = article_item;
        this.randomID = ThreadLocalRandom.current().nextInt(100000, 999999);
        this.article_status = "disable";
        this.article_page = 1;
    }
    
    public StoreData(String article_name) 
    {
        this.article_name = article_name;
    }
    
    public StoreData(int article_id) 
    {
        this.article_id = article_id;
    }
    
    static {
    	StoreData.table = "ed_shop_articles";
    }
    
    public int returnID() 
    {
        return randomID;
    }
    
    public String name()
    {
    	return article_name;
    }
    
    public void createArticle() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("INSERT INTO ed_shop_articles (article_type, article_sub_type, article_id, article_name, type_of_pay, article_price, article_is_in_reduction, article_reduction_percentage, article_rarity, article_item, article_status, article_page) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, article_type); /*article_type*/
            preparedStatement.setString(2, ""); /*article_sub_type*/
            preparedStatement.setInt(3, randomID); /*article_id*/
            preparedStatement.setString(4, article_name); /*article_name*/
            preparedStatement.setString(5, type_of_pay); /*type_of_pay*/
            preparedStatement.setFloat(6, article_price); /*article_price*/
            preparedStatement.setString(7, "disable"); /*article_is_in_reduction*/
            preparedStatement.setInt(8, 0); /*article_price*/
            preparedStatement.setInt(9, article_rarity); /*article_rarity*/
            preparedStatement.setString(10, article_item); /*article_item*/
            preparedStatement.setString(11, article_status); /*article_status*/
            preparedStatement.setInt(12, article_page); /*article_page*/
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
    
    public void removeArticle() 
    {
        try 
        {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM ed_shop_articles WHERE article_id = ?");
            ps.setInt(1, article_id);
            ps.execute();
            ps.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
    
    public boolean hisArticleExist() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_id FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getArticleIDByName() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_id FROM ed_shop_articles WHERE article_name = ?");
            preparedStatement.setString(1, article_name.replace(" ", "_").replace("§", "&"));
            int result = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getInt("article_id");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int getArticlePage() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_page FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            int result = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getInt("article_page");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    public String getArticleName() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_name FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            String result = "";
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getString("article_name");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return "";
        }
    }
    
    public String getArticleType() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_type FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            String result = "";
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getString("article_type");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return "";
        }
    }
    
    public String getArticleSubType() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_sub_type FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            String result = "";
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getString("article_sub_type");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return "";
        }
    }
    
    public String getTypeOfPay() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT type_of_pay FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            String result = "";
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getString("type_of_pay");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return "";
        }
    }
    
    public Float getArticlePrice() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_price FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            Float result = (float) 0.0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getFloat("article_price");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return (float) 0.0;
        }
    }
    
    public String getIsInReduction() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_is_in_reduction FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            String result = "";
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getString("article_is_in_reduction");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return "";
        }
    }
    
    public int getReductionPercentage() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_reduction_percentage FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            int result = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getInt("article_reduction_percentage");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int getArticleRarity() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_rarity FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            int result = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getInt("article_rarity");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    public String getArticleItem() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_item FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            String result = "";
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getString("article_item");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return "";
        }
    }
    
    public int getArticleItemSlot() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_item_slot FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            int result = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getInt("article_item_slot");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    public String getArticleSkull() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_skull FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            String result = "";
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getString("article_skull");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return "";
        }
    }
    
    public String getArticleStatus() 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT article_status FROM ed_shop_articles WHERE article_id = ?");
            preparedStatement.setInt(1, article_id);
            String result = "";
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
            	result = rs.getString("article_status");
            }
            preparedStatement.close();
            return result;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return "";
        }
    }
    
    public void updateArticleName(String newName) 
    {
        try 
        {
        	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_shop_articles SET article_name = ? WHERE article_id = ?");
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, article_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
	}
    
    public void updateArticlePage(int newPage) 
    {
        try 
        {
        	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_shop_articles SET article_page = ? WHERE article_id = ?");
            preparedStatement.setInt(1, newPage);
            preparedStatement.setInt(2, article_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
	}
    
    public void updateArticleSubType(String newSubType) 
    {
        try 
        {
        	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_shop_articles SET article_sub_type = ? WHERE article_id = ?");
            preparedStatement.setString(1, newSubType);
            preparedStatement.setInt(2, article_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.toString();
        }
	}
    
    public void updateTypeOfPay(String newTypeOfPay) 
    {
        try 
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_shop_articles SET type_of_pay = ? WHERE article_id = ?");
            preparedStatement.setString(1, newTypeOfPay);
            preparedStatement.setInt(2, article_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
	}
    
    public void updatePrice(Float newPrice) 
    {
        try 
        {
           	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_shop_articles SET article_price = ? WHERE article_id = ?");
            preparedStatement.setFloat(1, newPrice);
            preparedStatement.setInt(2, article_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
	}
    
    public void updateIsInReduction(String newIsInReduction) 
    {
        try 
        {
        	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_shop_articles SET article_is_in_reduction = ? WHERE article_id = ?");
            preparedStatement.setString(1, newIsInReduction);
            preparedStatement.setInt(2, article_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
	}
    
    public void updateReductionPercentage(int newReductionPercentage) 
    {
        try 
        {
        	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_shop_articles SET article_reduction_percentage = ? WHERE article_id = ?");
            preparedStatement.setInt(1, newReductionPercentage);
            preparedStatement.setInt(2, article_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
	}
    
    public void updateRarity(int newRarity) 
    {
        try 
        {
        	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_shop_articles SET article_rarity = ? WHERE article_id = ?");
            preparedStatement.setInt(1, newRarity);
            preparedStatement.setInt(2, article_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
	}
    
    public void updateItem(String newItem) 
    {
        try 
        {
        	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_shop_articles SET article_item = ? WHERE article_id = ?");
            preparedStatement.setString(1, newItem);
            preparedStatement.setInt(2, article_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
	}
    
    public void updateItemSlot(int newItemSlot) 
    {
        try 
        {
        	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_shop_articles SET article_item_slot = ? WHERE article_id = ?");
            preparedStatement.setInt(1, newItemSlot);
            preparedStatement.setInt(2, article_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
	}
    
    public void updateSkull(String newSkull) 
    {
        try 
        {
          	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_shop_articles SET article_skull = ? WHERE article_id = ?");
            preparedStatement.setString(1, newSkull);
            preparedStatement.setInt(2, article_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
	}
    
    public void updateStatus(String newStatus) 
    {
        try 
        {
           	PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_shop_articles SET article_status = ? WHERE article_id = ?");
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, article_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
	}
}
