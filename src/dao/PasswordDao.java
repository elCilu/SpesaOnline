package dao;

import utils.HashUtil;

import java.sql.SQLException;
import java.util.Arrays;

public class PasswordDao extends BaseDao {
    private static final String INSERT = "insert into password values (%s, %s)";

    static void insertPassword(String password) {
        try {
            byte[] salt = HashUtil.createSalt();
            connection.createStatement().executeQuery(
                    String.format(INSERT, HashUtil.generateHash(password, salt), Arrays.toString(salt)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
