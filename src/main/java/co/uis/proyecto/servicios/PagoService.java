package co.uis.proyecto.servicios;

import co.uis.proyecto.entidades.Pago;
import co.uis.proyecto.repositorios.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class PagoService {
  private final PagoRepository repo;

  public List<Pago> listar( ){ return repo.findAll(); }
  public Pago obtener(Integer id){ 
    Objects.requireNonNull(id, "id");
    return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Pago no encontrado")); }

  @Transactional public Pago crear(Pago p){ 
    Objects.requireNonNull(p, "p");
    Pago saved = repo.save(p);
    return Objects.requireNonNull(saved, "Pago no puede ser null");
   }

  @Transactional
  public Pago actualizar(Integer id, Pago p){
    Pago a = obtener(id);
    a.setReferencia(p.getReferencia());
    a.setFacturaNumero(p.getFacturaNumero());
    a.setMetodo(p.getMetodo());
    a.setFecha(p.getFecha());
    return repo.save(a);
  }

  @Transactional public void eliminar(Integer id){ repo.deleteById(Objects.requireNonNull(id, "id")); }
}