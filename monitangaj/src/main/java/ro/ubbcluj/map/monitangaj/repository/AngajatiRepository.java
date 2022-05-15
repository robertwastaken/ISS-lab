package ro.ubbcluj.map.monitangaj.repository;



import ro.ubbcluj.map.monitangaj.domain.Angajat;
import ro.ubbcluj.map.monitangaj.domain.StatusSarcina;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AngajatiRepository implements Repository<Long, Angajat>{
    private final String url;
    private final String username;
    private final String password;

    public AngajatiRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Angajat findOne(Long aLong) {
        Angajat angajat = null;
        String sql = "select * from angajati where id_angajat = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            Long id = resultSet.getLong("id_angajat");
            String nume = resultSet.getString("nume");
            String prenume = resultSet.getString("prenume");
            String prezenta = resultSet.getString("prezenta");

            angajat = new Angajat(nume, prenume);
            angajat.setId(id);
            angajat.setPrezenta(prezenta);
            return angajat;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return angajat;
    }

    @Override
    public Iterable<Angajat> findAll() {

        Set<Angajat> angajati = new HashSet<>();

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select * from angajati");
            ResultSet resultSet = statement.executeQuery()){

            while(resultSet.next()){
                Long id = resultSet.getLong("id_angajat");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String prezenta = resultSet.getString("prezenta");

                Angajat angajat = new Angajat(nume, prenume);
                angajat.setId(id);
                angajat.setPrezenta(prezenta);
                angajati.add(angajat);
            }

            return angajati;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return angajati;
    }

    @Override
    public void save(Angajat entity) {
        String sql = "insert into angajati(nume, prenume) values(?, ?)";
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, entity.getNume());
            statement.setString(2, entity.getPrenume());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long aLong) {

        String sql = "delete from angajati where id_angajat = ?";

        try(Connection connection = DriverManager.getConnection(url, username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, aLong);
            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Angajat entity) {
        String sql = "update angajati set nume = ?, prenume = ? where id_angajat = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, entity.getNume());
            statement.setString(2, entity.getPrenume());
            statement.setLong(3, entity.getId());

            statement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Long getSize() {
        Long numberOfElem = null;

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select count(id_angajat) from users");
            ResultSet resultSet = statement.executeQuery()){

            resultSet.next();
            numberOfElem = resultSet.getLong("count");

            return numberOfElem;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfElem;
    }
}