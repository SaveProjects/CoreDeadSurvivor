package fr.edminecoreteam.core.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerInfo 
{
	
	public static int getPlayerRank(UUID uuid) {
        try {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT grade FROM joueurs WHERE uuid_player = ?");
            preparedStatement.setString(1, uuid.toString());
            int power = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                power = rs.getInt("grade");
            }
            preparedStatement.close();
            return power;
        }
        catch (SQLException e) {
            System.out.println("Erreur de chargement des grades... (erreur de connexion mysql)");
            return 0;
        }
    }
	
	public static int getLang(UUID uuid) {
        try {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT lang FROM settings WHERE uuid_player = ?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet rs = preparedStatement.executeQuery();
            float value = 0.0f;
            while (rs.next()) {
                value = rs.getFloat("lang");
            }
            preparedStatement.close();
            return (int)value;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
