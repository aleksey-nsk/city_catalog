package dao;

import model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDao {

    private final static String DB_URL = "jdbc:h2:/Users/u19571276/Documents/work_projects/cities";
    private final static String DB_USERNAME = "sa";
    private final static String DB_PASSWORD = "";

    private PreparedStatement createPreparedStatement(QueryType type, Connection con, City city) throws SQLException {
        PreparedStatement ps;

        if (type == QueryType.INSERT) {
            String sqlInsert = "INSERT INTO TOWN (name, region, district, population, foundation) VALUES (?, ?, ?, ?, ?)";
            System.out.println(sqlInsert);
            ps = con.prepareStatement(sqlInsert);
            ps.setString(1, city.getName());
            ps.setString(2, city.getRegion());
            ps.setString(3, city.getDistrict());
            ps.setLong(4, city.getPopulation());
            ps.setInt(5, city.getFoundation());
        } else if (type == QueryType.SELECT_ALL) {
            String sqlSelectAll = "SELECT * FROM TOWN";
            System.out.println(sqlSelectAll);
            ps = con.prepareStatement(sqlSelectAll);
        } else if (type == QueryType.SELECT_BY_NAME_AND_REGION) {
            String sqlSelectOne = "SELECT COUNT(*) AS count FROM TOWN WHERE name = ? AND region = ?";
            ps = con.prepareStatement(sqlSelectOne);
            ps.setString(1, city.getName());
            ps.setString(2, city.getRegion());
        } else if (type == QueryType.DELETE) {
            String sqlDelete = "TRUNCATE TABLE TOWN";
            System.out.println(sqlDelete);
            ps = con.prepareStatement(sqlDelete);
        } else if (type == QueryType.CREATE) {
            String sqlCreate = "create table if not exists TOWN\n" +
                    "(\n" +
                    "    id         bigserial PRIMARY KEY,\n" +
                    "    name       text,\n" +
                    "    region     text,\n" +
                    "    district   text,\n" +
                    "    population bigint,\n" +
                    "    foundation int\n" +
                    ");";
            System.out.println(sqlCreate);
            ps = con.prepareStatement(sqlCreate);
        } else {
            throw new RuntimeException("Неизвестный тип запроса: " + type);
        }

        return ps;
    }

    public CityDao() {
        System.out.println("CityDao constructor: create table TOWN if not exist");

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = createPreparedStatement(QueryType.CREATE, con, null)) {

            boolean b = ps.execute();
            System.out.println(b);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(City city) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = createPreparedStatement(QueryType.INSERT, con, city)) {

            int rowCount = ps.executeUpdate();
            System.out.println("row count affected = " + rowCount + "\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<City> findAll() {
        List<City> list = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = createPreparedStatement(QueryType.SELECT_ALL, con, null);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String region = rs.getString("region");
                String district = rs.getString("district");
                long population = rs.getLong("population");
                int foundation = rs.getInt("foundation");
                City city = new City(id, name, region, district, population, foundation);
                list.add(city);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean isCityExistByNameAndRegion(City city) {
        boolean isCityExist = false;

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = createPreparedStatement(QueryType.SELECT_BY_NAME_AND_REGION, con, city);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int count = rs.getInt("count");
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

    public void deleteAll() {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = createPreparedStatement(QueryType.DELETE, con, null)) {

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
