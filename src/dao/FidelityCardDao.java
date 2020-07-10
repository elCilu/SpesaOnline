package dao;

import models.FidelityCard;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FidelityCardDao extends BaseDao{
    private static final String INSERT_CARD = "insert into loyaltyCards (emissionDate, idUser) values (?, ?)";

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
}
