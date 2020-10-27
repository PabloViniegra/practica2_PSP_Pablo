package controller;

import views.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseManagement {
    public final String ERROR_PROPERTIES = "Falta el archivo properties de la Base de Datos";
    public Connection returnConnection() {
        File file = new File("bbdd.properties");
        Properties properties = new Properties();
        String db;
        String host;
        String url;
        String user;
        String password;
        Connection conn = null;
        if (!properties.isEmpty())
            System.out.println(ERROR_PROPERTIES);

        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
            db = properties.getProperty("database");
            host = properties.getProperty("dbhost");
            user = properties.getProperty("dbuser");
            password = properties.getProperty("dbpassword");
            url = "jdbc:mysql://" + host + "/" + db;

            conn = DriverManager.getConnection(url, user, password);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
        }
        return conn;
    }

    public void readFromDatabaseInMainThread () {
        try (Connection conn = returnConnection()) {
            long timeInit = System.currentTimeMillis();
            int count = 0;
            String query = "SELECT * FROM EMPLEADOS";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                System.out.println("ID: " + results.getString("ID") + " EMAIL: " + results.getString("EMAIL") + " SALARIO: " + results.getString("INGRESOS"));
                count += results.getInt("INGRESOS");
            }
            System.out.println("La suma de los ingresos es: " + count);
            System.out.println("Duración del proceso: " + System.currentTimeMillis() + timeInit + " milisegundos.");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(0);
        }
    }
}
