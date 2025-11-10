package co.uis.proyecto.servicios;

import co.uis.proyecto.entidades.Cliente;
import co.uis.proyecto.repositorios.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class ClienteService {
  private final ClienteRepository repo;

  public List<Cliente> listar(){ return repo.findAll(); }
  public Cliente obtener(Integer id){ 
    Objects.requireNonNull(id, "id");
    return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado")); }

  

  @Transactional
  public Cliente crear(Cliente c){
    Objects.requireNonNull(c, "c");
    Cliente saved = repo.save(c);
    return Objects.requireNonNull(saved, "Cliente no puede ser null");
  }

  @Transactional
  public Cliente actualizar(Integer id, Cliente c){
    Cliente a = obtener(id);
    a.setNombre(c.getNombre());
    a.setCorreo(c.getCorreo());
    a.setTelefono(c.getTelefono());
    a.setFechaRegistro(c.getFechaRegistro());
    return repo.save(a);
  }

  @Transactional public void eliminar(Integer id){ repo.deleteById(Objects.requireNonNull(id, "id")); }
}