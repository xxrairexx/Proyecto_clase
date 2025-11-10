package co.uis.proyecto.servicios;

import co.uis.proyecto.entidades.Permiso;
import co.uis.proyecto.repositorios.PermisoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class PermisoService {
  private final PermisoRepository repo;

  public List<Permiso> listar( ){ return repo.findAll(); }
  public Permiso obtener(Integer id){
    Objects.requireNonNull(id, "id");
    return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Permiso no encontrado"));
  }

  @Transactional
  public Permiso crear(Permiso p){
    Objects.requireNonNull(p, "p");
    Permiso saved = repo.save(p);
    return Objects.requireNonNull(saved, "Permiso no puede ser null");
  }

  @Transactional
  public Permiso actualizar(Integer id, Permiso p){
    Permiso a = obtener(id);
    a.setRecurso(p.getRecurso());
    a.setAccion(p.getAccion());
    a.setPermitido(p.getPermitido());
    a.setNotas(p.getNotas());
    return repo.save(a);
  }
  @Transactional public void eliminar(Integer id){ repo.deleteById(Objects.requireNonNull(id)); }
}