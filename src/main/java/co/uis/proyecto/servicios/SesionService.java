package co.uis.proyecto.servicios;

import co.uis.proyecto.entidades.Sesion;
import co.uis.proyecto.repositorios.SesionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class SesionService {
  private final SesionRepository repo;

  public List<Sesion> listar(){ return repo.findAll(); }
  public Sesion obtener(Integer id){ 
    Objects.requireNonNull(id, "id");
    return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Sesión no encontrada")); }

  @Transactional public Sesion crear(Sesion s){
    Objects.requireNonNull(s, "s");
    Sesion saved = repo.save(s);
    return Objects.requireNonNull(saved, "Sesión no puede ser null");
   }

  @Transactional
  public Sesion actualizar(Integer id, Sesion s){
    Sesion a = obtener(id);
    a.setUsuario(s.getUsuario());
    a.setToken(s.getToken());
    a.setIp(s.getIp());
    a.setInicio(s.getInicio());
    return repo.save(a);
  }

  @Transactional public void eliminar(Integer id){ repo.deleteById(Objects.requireNonNull(id, "id")); }
}