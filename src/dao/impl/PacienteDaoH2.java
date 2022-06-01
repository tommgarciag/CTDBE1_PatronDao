package dao.impl;


import dao.IDao;
import model.Domicilio;
import model.Paciente;

import java.sql.*;
import java.time.LocalDate;

public class PacienteDaoH2 implements IDao<Paciente> {
    @Override
    public Paciente guardar(Paciente paciente) {
        Connection connection=null;
        try{
            //agregar el domicilio a la BD mediante el otro DAO
            DomicilioDaoH2 domicilioDaoH2= new DomicilioDaoH2();
            domicilioDaoH2.guardar(paciente.getDomicilio());

            connection=getConnection();
            PreparedStatement ps=connection.prepareStatement("INSERT INTO PACIENTES " +
                    "(NOMBRE, DNI, FECHA, DOMICILIO_ID) VALUES (?,?,?,?)");
            ps.setString(1,paciente.getNombre());
            ps.setInt(2,paciente.getDni());
            ps.setDate(3, Date.valueOf(paciente.getFecha()));
            ps.setInt(4,paciente.getDomicilio().getId());
            ps.execute();
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


        return paciente;
    }

    @Override
    public Paciente buscar(int id) {
        Connection connection=null;
        Paciente paciente = null;

        try{


            connection=getConnection();
            PreparedStatement ps=connection.prepareStatement("SELECT * FROM PACIENTES WHERE ID=?");
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idPaciente = rs.getInt("ID");
                String nombre = rs.getString("NOMBRE");
                int dni = rs.getInt("DNI");
                LocalDate fecha = rs.getDate("FECHA").toLocalDate();
                //agregar el domicilio a la BD mediante el otro DAO
                int FKdomicilio = rs.getInt("DOMICILIO_ID");
                Domicilio domicilio = null;
                DomicilioDaoH2 domicilioDaoH2= new DomicilioDaoH2();
                domicilio = domicilioDaoH2.buscar(FKdomicilio);

                paciente = new Paciente(idPaciente, nombre, dni, fecha, domicilio);
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
        System.out.println(paciente.toString());
        return paciente;
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/C15SPacientes","sa","");
    }
}
