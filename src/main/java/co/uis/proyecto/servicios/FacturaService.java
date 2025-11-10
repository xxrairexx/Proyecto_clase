package co.uis.proyecto.servicios;

import co.uis.proyecto.entidades.Factura;
import co.uis.proyecto.repositorios.FacturaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class FacturaService {
  private final FacturaRepository repo;

  public List<Factura> listar( ){ return repo.findAll(); }
  public Factura obtener(Integer id){ 
    Objects.requireNonNull(id, "id");
    return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Factura no encontrada")); }

  @Transactional public Factura crear(Factura f){ 
    Objects.requireNonNull(f, "f");
    Factura saved = repo.save(f);
    return Objects.requireNonNull(saved, "Factura no puede ser null");
   }

  @Transactional
  public Factura actualizar(Integer id, Factura f){
    Factura a = obtener(id);
    a.setNumero(f.getNumero());
    a.setClienteNombre(f.getClienteNombre());
    a.setFecha(f.getFecha());
    a.setTotal(f.getTotal());
    return repo.save(a);
  }

  @Transactional public void eliminar(Integer id){ repo.deleteById(Objects.requireNonNull(id, "id")); }
}