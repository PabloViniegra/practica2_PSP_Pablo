package controller;

import views.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseManagement extends Thread {
    public final String ERROR_PROPERTIES = "Falta el archivo properties de la Base de Datos";
    private int initial;
    private int last;
    private int total;

    public DatabaseManagement(int initial, int last) {
        this.initial = initial;
        this.last = last;
    }

    public DatabaseManagement() {

    }

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

    public void readFromDatabaseInMainThread() {
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
            System.out.println("Duración del proceso: " + (System.currentTimeMillis() - timeInit) + " milisegundos.");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(0);
        }
    }

    public synchronized void readFromDatabaseWithThreads() {
        try (Connection conn = returnConnection()) {


            String query = "SELECT * FROM EMPLEADOS WHERE ID BETWEEN " + this.initial + " AND " + this.last;
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                System.out.println("ID: " + results.getString("ID") + " EMAIL: " + results.getString("EMAIL") + " SALARIO: " + results.getString("INGRESOS"));
                this.total += results.getInt("INGRESOS");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void run() {

        readFromDatabaseWithThreads();
    }

    public int requestNumberOfRegisters() {
        int numberOfRegisters = 0;
        try (Connection conn = returnConnection()) {
            Statement statement = conn.createStatement();
            String query = "SELECT COUNT(*) FROM EMPLEADOS";
            ResultSet result = statement.executeQuery(query);
            if (result.next())
                numberOfRegisters = result.getInt("count(*)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return numberOfRegisters;
    }

    public int getInitial() {
        return initial;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
