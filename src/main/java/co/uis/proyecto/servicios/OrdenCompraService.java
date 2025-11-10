package co.uis.proyecto.servicios;

import co.uis.proyecto.entidades.OrdenCompra;
import co.uis.proyecto.repositorios.OrdenCompraRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class OrdenCompraService {
  private final OrdenCompraRepository repo;

  public List<OrdenCompra> listar( ){ return repo.findAll(); }
  public OrdenCompra obtener(Integer id){ 
    Objects.requireNonNull(id, "id");
    return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Orden de compra no encontrada")); }

  @Transactional public OrdenCompra crear(OrdenCompra o){ 
    Objects.requireNonNull(o, "o");
    OrdenCompra saved = repo.save(o);
    return Objects.requireNonNull(saved, "Orden de compra no puede ser null");
   }

  @Transactional
  public OrdenCompra actualizar(Integer id, OrdenCompra o){
    OrdenCompra a = obtener(id);
    a.setNumero(o.getNumero());
    a.setProveedorNombre(o.getProveedorNombre());
    a.setFecha(o.getFecha());
    a.setTotal(o.getTotal());
    return repo.save(a);
  }

  @Transactional public void eliminar(Integer id){ repo.deleteById(Objects.requireNonNull(id, "id")); }
}