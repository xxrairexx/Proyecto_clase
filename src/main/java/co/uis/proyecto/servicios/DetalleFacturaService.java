package co.uis.proyecto.servicios;

import co.uis.proyecto.entidades.DetalleFactura;
import co.uis.proyecto.repositorios.DetalleFacturaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class DetalleFacturaService {
  private final DetalleFacturaRepository repo;

  public List<DetalleFactura> listar( ){ return repo.findAll(); }
  public DetalleFactura obtener(Integer id){ 
    Objects.requireNonNull(id, "id");
    return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Detalle de factura no encontrado")); 
  }

  @Transactional 
  public DetalleFactura crear(DetalleFactura d){ 
    Objects.requireNonNull(d, "d");
    DetalleFactura saved = repo.save(d);
    return Objects.requireNonNull(saved, "Detalle de factura no puede ser null");
   }

  @Transactional
  public DetalleFactura actualizar(Integer id, DetalleFactura d){
    DetalleFactura a = obtener(id);
    a.setFacturaNumero(d.getFacturaNumero());
    a.setProductoNombre(d.getProductoNombre());
    a.setCantidad(d.getCantidad());
    a.setPrecioUnitario(d.getPrecioUnitario());
    return repo.save(a);
  }

  @Transactional public void eliminar(Integer id){ repo.deleteById(Objects.requireNonNull(id, "id")); }
}