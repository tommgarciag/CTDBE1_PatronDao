package service;

import dao.IDao;
import model.Paciente;

public class PacienteService {
    private IDao<Paciente> pacienteDAO;

    public PacienteService(IDao<Paciente> pacienteDAO) {
        this.pacienteDAO = pacienteDAO;
    }

    public Paciente guardarPaciente(Paciente paciente){
        return pacienteDAO.guardar(paciente);
    }

    public Paciente buscarPacientePorId(int id) {
        return pacienteDAO.buscar(id);
    }

}
