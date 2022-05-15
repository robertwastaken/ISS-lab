package ro.ubbcluj.map.monitangaj.repository;



import ro.ubbcluj.map.monitangaj.domain.Sarcina;
import ro.ubbcluj.map.monitangaj.domain.StatusSarcina;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class SarciniRepository implements Repository<Long, Sarcina>{
    private final String url;
    private final String username;
    private final String password;

    public SarciniRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Sarcina findOne(Long aLong) {
        Sarcina sarcina = null;
        StatusSarcina status;
        String sql = "select * from sarcini where id_sarcina = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            Long id = resultSet.getLong("id_sarcina");
            String descriere = resultSet.getString("descriere");
            int statusint= resultSet.getInt("status");
            if(statusint == 1)
                status = StatusSarcina.COMPLETA;
            else
                status = StatusSarcina.INCOMPLETA;
            Long idAngajat = resultSet.getLong("id_angajat");

            sarcina = new Sarcina(descriere, status, idAngajat);
            sarcina.setId(id);
            return sarcina;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return sarcina;
    }

    @Override
    public Iterable<Sarcina> findAll() {
        StatusSarcina status;
        Set<Sarcina> sarcini = new HashSet<>();

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select * from sarcini");
            ResultSet resultSet = statement.executeQuery()){

            while(resultSet.next()){
                Long id = resultSet.getLong("id_sarcina");
                String descriere = resultSet.getString("descriere");
                int statusint= resultSet.getInt("status");
                if(statusint == 1)
                    status = StatusSarcina.COMPLETA;
                else
                    status = StatusSarcina.INCOMPLETA;
                Long idAngajat = resultSet.getLong("id_angajat");

                Sarcina sarcina = new Sarcina(descriere, status, idAngajat);
                sarcina.setId(id);

                sarcini.add(sarcina);
            }
            return sarcini;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return sarcini;
    }

    @Override
    public void save(Sarcina entity) {
        String sql = "insert into sarcini(descriere, status, id_angajat) values(?, ?, ?)";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, entity.getDescriere());
            if(entity.getStatus() == StatusSarcina.COMPLETA)
                statement.setInt(2, 1);
            else
                statement.setInt(2, 0);
            statement.setLong(3, entity.getIdAngajat());

            statement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long aLong) {
        String sql = "delete from sarcini where id_sarcina = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, aLong);
            statement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Sarcina entity) {
        String sql = "update sarcini set descriere = ?, status = ?, id_angajat = ? where id_sarcina = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, entity.getDescriere());
            if(entity.getStatus() == StatusSarcina.COMPLETA)
                statement.setInt(2, 1);
            else
                statement.setInt(2, 0);
            statement.setLong(3, entity.getIdAngajat());
            statement.setLong(4, entity.getId());

            statement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Long getSize() {
        Long numberOfElem = null;

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select count(id_sarcina) from sarcini");
            ResultSet resultSet = statement.executeQuery()){

            resultSet.next();
            numberOfElem = resultSet.getLong("count");

            return numberOfElem;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return numberOfElem;
    }
}
