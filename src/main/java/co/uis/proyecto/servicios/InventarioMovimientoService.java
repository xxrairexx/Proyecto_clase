package co.uis.proyecto.servicios;

import co.uis.proyecto.entidades.InventarioMovimiento;
import co.uis.proyecto.repositorios.InventarioMovimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
@Service @RequiredArgsConstructor
public class InventarioMovimientoService {
  private final InventarioMovimientoRepository repo;

  public List<InventarioMovimiento> listar( ){ return repo.findAll(); }
  public InventarioMovimiento obtener(Integer id){ 
    Objects.requireNonNull(id, "id");    return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado")); }

  @Transactional public InventarioMovimiento crear(InventarioMovimiento m){ 
    Objects.requireNonNull(m, "m");
    InventarioMovimiento saved = repo.save(m);
    return Objects.requireNonNull(saved, "Movimiento no puede ser null");
   }

  @Transactional
  public InventarioMovimiento actualizar(Integer id, InventarioMovimiento m){
    InventarioMovimiento a = obtener(id);
    a.setProductoCodigo(m.getProductoCodigo());
    a.setTipo(m.getTipo());
    a.setCantidad(m.getCantidad());
    a.setFecha(m.getFecha());
    return repo.save(a);
  }

  @Transactional public void eliminar(Integer id){ repo.deleteById(Objects.requireNonNull(id, "id")); }
}