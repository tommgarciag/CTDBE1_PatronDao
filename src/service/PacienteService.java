package service;

import dao.IDao;
import dao.impl.PacienteDaoH2;
import model.Paciente;

import java.util.List;

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

    public void eliminarPacientePorId(int id) {
        pacienteDAO.eliminar(id);
    }

    public List<Paciente> buscarTodosLosPacientes() {
        return pacienteDAO.buscarTodos();
    }

}
