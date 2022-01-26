package dao;

import model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDao {

    private static final String DB_URL = "jdbc:postgresql://localhost:15432/postgres";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";

    public CityDao() {
        String sqlCreate = "create table if not exists city\n" +
                "(\n" +
                "    id         bigserial PRIMARY KEY,\n" +
                "    name       text,\n" +
                "    region     text,\n" +
                "    district   text,\n" +
                "    population bigint,\n" +
                "    foundation int\n" +
                ");";
//        System.out.println(sqlCreate);

        try (
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCreate)
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(City city) {
        String sqlInsert = "insert into city(name, region, district, population, foundation) values(?, ?, ?, ?, ?)";
//        System.out.println(sqlInsert);

        try (
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)
        ) {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getRegion());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setLong(4, city.getPopulation());
            preparedStatement.setInt(5, city.getFoundation());

            int rowCount = preparedStatement.executeUpdate();
//            System.out.println("row count affected: " + rowCount + "\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isCityExistByNameAndRegion(City city) {
        boolean isCityExist = false;

        String sqlSelectCount = "select count(*) as count from city where name = ? and region = ?";
//        System.out.println(sqlSelectCount);

        try (
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectCount)
        ) {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getRegion());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count > 0) {
                    isCityExist = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isCityExist;
    }

    public List<City> findAll() {
        List<City> list = new ArrayList<>();

        String sqlSelectAll = "select * from city";
//        System.out.println(sqlSelectAll);

        try (
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectAll);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String region = resultSet.getString("region");
                String district = resultSet.getString("district");
                long population = resultSet.getLong("population");
                int foundation = resultSet.getInt("foundation");

                City city = new City(id, name, region, district, population, foundation);
                list.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteAll() {
        String sqlDelete = "truncate table city";
//        System.out.println(sqlDelete);

        try (
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
