package co.uis.proyecto.servicios;

import co.uis.proyecto.entidades.Producto;
import co.uis.proyecto.repositorios.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class ProductoService {
  private final ProductoRepository repo;

  public List<Producto> listar( ){ return repo.findAll(); }
  public Producto obtener(Integer id){ 
    Objects.requireNonNull(id, "id");
    return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado")); }

  @Transactional public Producto crear(Producto p){ 
    Objects.requireNonNull(p, "p");
    Producto saved = repo.save(p);
    return Objects.requireNonNull(saved, "Producto no puede ser null");
   }

  @Transactional
  public Producto actualizar(Integer id, Producto p){
    Producto a = obtener(id);
    a.setNombre(p.getNombre());
    a.setDescripcion(p.getDescripcion());
    a.setPrecio(p.getPrecio());
    a.setStock(p.getStock());
    return repo.save(a);
  }

  @Transactional public void eliminar(Integer id){ repo.deleteById(Objects.requireNonNull(id, "id")); }
}