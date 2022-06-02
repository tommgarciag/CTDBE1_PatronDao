package dao.impl;


import dao.IDao;
import model.Domicilio;
import model.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteDaoH2 implements IDao<Paciente> {
    private Logger logger = Logger.getLogger(PacienteDaoH2.class);

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
        } catch (Exception e){
            e.printStackTrace();
        } finally {
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

            logger.info("Buscando el paciente con ID: " + id);
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

        } catch (Exception e){
            e.printStackTrace();
            //logger.error("No se pude encontrar el paciente con ID: " + id);
        } finally {
            try{
                connection.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }

        //System.out.println(paciente.toString());
        return paciente;
    }

    @Override
    public void eliminar(int id) {
        Connection connection=null;


        try{

            // primero busco el paciente para hacer la eliminacion con el otro dao
            Paciente pacienteBuscado = buscar(id);
            DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();
            domicilioDaoH2.eliminar(pacienteBuscado.getDomicilio().getId());


            connection=getConnection();
            PreparedStatement ps=connection.prepareStatement("DELETE FROM PACIENTES WHERE ID=?");
            ps.setInt(1,id);
            ps.execute();




            logger.warn("Eliminando el paciente con ID: " + id);


        } catch (Exception e){
            e.printStackTrace();
            //logger.error("No se pude encontrar el paciente con ID: " + id);
        } finally {
            try{
                connection.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }


    }

    @Override
    public List<Paciente> buscarTodos() {
        Connection connection=null;
        ArrayList<Paciente> pacientes = new ArrayList<>();

        try{
            connection=getConnection();
            PreparedStatement ps=connection.prepareStatement("SELECT * FROM PACIENTES;");


            logger.info("Buscando todos los pacientes.");
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

                pacientes.add(new Paciente(idPaciente, nombre, dni, fecha, domicilio));
            }

        } catch (Exception e){
            e.printStackTrace();
            //logger.error("No se pude encontrar el paciente con ID: " + id);
        } finally {
            try{
                connection.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }

        //System.out.println(paciente.toString());
        return pacientes;
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/C15SPacientes","sa","");
    }
}
