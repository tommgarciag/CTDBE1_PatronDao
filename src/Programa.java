import dao.impl.PacienteDaoH2;
import model.Domicilio;
import model.Paciente;
import service.PacienteService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;

public class Programa {

    public static void main(String[] args) {
        // invocar al archivo para crear la base de datos
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

        PacienteService pacienteService= new PacienteService(new PacienteDaoH2());
        Domicilio domicilioEzequiel= new Domicilio("Calle B", 84, "Salta","Salta");
        Paciente rodolfo= new Paciente("Ezequiel", 54181, LocalDate.of(2022,05,31),
                domicilioEzequiel);

        Domicilio domicilioTomas = new Domicilio("Calle Oculta", 666, "Comodoro Rivadavia", "Chubut");
        Paciente tomas = new Paciente("Tomas", 333333, LocalDate.of(2022,6, 1), domicilioTomas);

        pacienteService.guardarPaciente(rodolfo);
        pacienteService.guardarPaciente(tomas);
        pacienteService.buscarPacientePorId(1);
        pacienteService.buscarPacientePorId(2);
    }
}
