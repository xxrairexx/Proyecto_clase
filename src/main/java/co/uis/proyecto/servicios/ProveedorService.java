package co.uis.proyecto.servicios;

import co.uis.proyecto.entidades.Proveedor;
import co.uis.proyecto.repositorios.ProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class ProveedorService {
  private final ProveedorRepository repo;

  public List<Proveedor> listar( ){ return repo.findAll(); }
  public Proveedor obtener(Integer id){ 
    Objects.requireNonNull(id, "id");
    return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado")); }

  @Transactional public Proveedor crear(Proveedor p){   
    Objects.requireNonNull(p, "p");
    Proveedor saved = repo.save(p);
    return Objects.requireNonNull(saved, "Proveedor no puede ser null");
   }

  @Transactional
  public Proveedor actualizar(Integer id, Proveedor p){
    Proveedor a = obtener(id);
    a.setNombre(p.getNombre());
    a.setContacto(p.getContacto());
    a.setTelefono(p.getTelefono());
    a.setCiudad(p.getCiudad());
    return repo.save(a);
  }

  @Transactional public void eliminar(Integer id){ repo.deleteById(Objects.requireNonNull(id, "id")); }
}