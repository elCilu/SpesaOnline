package dao;

import models.FidelityCard;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FidelityCardDao extends BaseDao{
    private static final String INSERT_CARD = "insert into loyaltyCards (emissionDate, idUser) values (?, ?)";
    private static final String UPDATE_POINTS = "update loyaltyCards set points = ? where idUser = ?";
    private static final String GETPOINTS_BY_IDUSER = "select points from loyaltyCards where idUser = ?";
    private FidelityCardDao() {
    }

    public static int insertCard(FidelityCard cardModel) {
        int result = 0;
        System.out.print("Inserting card into Loyalty Card table... ");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_CARD);
            statement.setDate(1, new Date(cardModel.getEmissionDate().getTime()));
            statement.setInt(2, cardModel.getIdUser());
            result = statement.executeUpdate();
            System.out.println("Loyalty inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //ritorna punti carta
    public static int getPoints(int idUser) {
        int points = -1;   //se -1 errore nel db

        try{
            PreparedStatement statement = connection.prepareStatement(GETPOINTS_BY_IDUSER);
            statement.setInt(1, idUser);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                points = resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return points;
    }

    //aggiorna punti di un prodotto
    /**
     @param points punti già calcolati (puntiPrecedenti + puntiDaAggiungere)
     **/
    public static int updateQuantity(int idUser, int points) {
        int result = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_POINTS);
            statement.setInt(1, points);
            statement.setInt(2, idUser);
            result = statement.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
