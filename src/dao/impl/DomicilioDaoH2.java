package dao.impl;

import dao.IDao;
import model.Domicilio;

import java.sql.*;

public class DomicilioDaoH2 implements IDao<Domicilio> {
    @Override
    public Domicilio guardar(Domicilio domicilio) {
        Connection connection=null;
        try{
            connection=getConnection();
            PreparedStatement ps= connection.prepareStatement("INSERT INTO DOMICILIOS " +
                            "(CALLE, NUMERO, LOCALIDAD, PROVINCIA) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,domicilio.getCalle());
            ps.setInt(2,domicilio.getNumero());
            ps.setString(3,domicilio.getLocalidad());
            ps.setString(4, domicilio.getProvincia());
            ps.execute();
            ResultSet key= ps.getGeneratedKeys();
            while (key.next()){
                domicilio.setId(key.getInt(1));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                connection.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return domicilio;
    }

    @Override
    public Domicilio buscar(int id) {
        Connection connection = null;
        Domicilio domicilio = null;

        try {
            connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM DOMICILIOS WHERE ID=?");

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idDomicilio = rs.getInt("ID");
                String calle = rs.getString("CALLE");
                int numero = rs.getInt("NUMERO");
                String localidad = rs.getString("LOCALIDAD");
                String provincia = rs.getString("PROVINCIA");
                domicilio = new Domicilio(idDomicilio, calle, numero, localidad, provincia);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }



        return domicilio;
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/C15SPacientes","sa","");
    }
}

