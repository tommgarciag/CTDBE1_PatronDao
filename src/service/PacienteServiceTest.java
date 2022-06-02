package service;

import dao.impl.PacienteDaoH2;
import model.Domicilio;
import model.Paciente;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PacienteServiceTest {
    @Test
    public void guardarYBuscarPacienteTest() {
        Connection connection = null;

        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/C15SPacientes;" +
                    "INIT=RUNSCRIPT FROM 'create.sql'", "sa", "");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // preparar nuestro domicilio y paciente de prueba
        Domicilio domicilioParaAgregar= new Domicilio("Calle B", 84, "Salta","Salta");
        Paciente pacienteParaAgregar= new Paciente("Ezequiel", 54181, LocalDate.of(2022,05,31),
                domicilioParaAgregar);
        PacienteService pacienteService= new PacienteService(new PacienteDaoH2());

        // usar el service
        pacienteService.guardarPaciente(pacienteParaAgregar);
        Paciente pacienteEncontrado = pacienteService.buscarPacientePorId(3);

        // hacer el assert
        assertEquals(pacienteParaAgregar.getDni(), pacienteEncontrado.getDni());




    }


    @Test
    public void eliminarPacienteID1Test() {
        // preparar nuestro domicilio y paciente de prueba
        PacienteService pacienteService= new PacienteService(new PacienteDaoH2());

        // usar el service
        pacienteService.eliminarPacientePorId(3);


        // hacer el assert
        assertTrue(pacienteService.buscarPacientePorId(3) == null);
    }

    @Test
    public void buscarTodosPacientes() {
        // preparar nuestro domicilio y paciente de prueba
        PacienteService pacienteService= new PacienteService(new PacienteDaoH2());

        // usar el service
        List<Paciente> pacienteList = pacienteService.buscarTodosLosPacientes();
        for (Paciente p:pacienteList) {
            System.out.println(p.toString());
        }


        // hacer el assert
        assertTrue(pacienteList.size()>0);
    }

}