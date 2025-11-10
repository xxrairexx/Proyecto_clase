package co.uis.proyecto.servicios;

import co.uis.proyecto.entidades.Perfil;
import co.uis.proyecto.repositorios.PerfilRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class PerfilService {
  private final PerfilRepository repo;

  public List<Perfil> listar( ){ return repo.findAll(); }

  public Perfil obtener(Integer id){
    Objects.requireNonNull(id, "id");
    return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Perfil no encontrado"));
  }

  @Transactional
  public Perfil crear(Perfil p){ 
    Objects.requireNonNull(p, "p");
    Perfil saved = repo.save(p);
    return Objects.requireNonNull(saved, "Perfil no puede ser null");
   }

  @Transactional
  public Perfil actualizar(Integer id, Perfil p){
    Perfil a = obtener(id);
    a.setAlias(p.getAlias());
    a.setTelefono(p.getTelefono());
    a.setDireccion(p.getDireccion());
    a.setFechaNacimiento(p.getFechaNacimiento());
    return repo.save(a);
  }

  @Transactional
  public void eliminar(Integer id){ repo.deleteById(Objects.requireNonNull(id, "id")); }
}