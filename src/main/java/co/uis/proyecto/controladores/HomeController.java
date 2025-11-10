package co.uis.proyecto.controladores;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {


  @GetMapping public String listar(){ return "OK"; }
  
}