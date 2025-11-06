import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Proveedor, ProveedoresService } from '../../services/proveedores-service';

@Component({
  selector: 'app-proveedor-component',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './proveedor-component.html',
  styleUrls: ['./proveedor-component.css']
})
export class ProveedorComponent implements OnInit {

  proveedores = signal<Proveedor[]>([]);
  cargandoLista = signal(true);
  guardando = signal(false);
  error = signal<string | undefined>(undefined);
  editandoId = signal<number | null>(null);

  form!: FormGroup;

  constructor(private proveedoresService: ProveedoresService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      contacto: ['', Validators.required],
      telefono: ['', [Validators.required, Validators.pattern(/^\d{7,15}$/)]],
      ciudad: ['', Validators.required]
    });

    this.cargarProveedores();
  }

  cargarProveedores() {
    this.cargandoLista.set(true);
    this.proveedoresService.listar().subscribe({
      next: data => { 
        this.proveedores.set(data); 
        this.cargandoLista.set(false); 
      },
      error: err => { 
        console.error(err); 
        this.error.set('Error al cargar proveedores'); 
        this.cargandoLista.set(false); 
      }
    });
  }

  editar(p: Proveedor) {
    this.editandoId.set(p.id);
    this.form.reset({
      nombre: p.nombre,
      contacto: p.contacto,
      telefono: p.telefono,
      ciudad: p.ciudad
    });
  }

  cancelarEdicion() {
    this.editandoId.set(null);
    this.form.reset({
      nombre: '',
      contacto: '',
      telefono: '',
      ciudad: ''
    });
  }

  guardar() {
    if (this.form.invalid || this.guardando()) return;

    const payload: Proveedor = this.form.value;
    this.guardando.set(true);

    const id = this.editandoId();
    if (id != null) {
      // === ACTUALIZAR ===
      this.proveedoresService.actualizar(id, payload).subscribe({
        next: actualizado => {
          this.proveedores.update(arr => arr.map(x => x.id === id ? { ...actualizado } : x));
          this.cancelarEdicion();
          this.guardando.set(false);
        },
        error: err => { 
          console.error(err); 
          this.error.set('Error al actualizar proveedor'); 
          this.guardando.set(false); 
        }
      });
    } else {
      // === CREAR ===
      this.proveedoresService.crear(payload).subscribe({
        next: creado => {
          this.proveedores.update(arr => [...arr, creado]);
          this.cancelarEdicion();
          this.guardando.set(false);
        },
        error: err => { 
          console.error(err); 
          this.error.set('Error al guardar proveedor'); 
          this.guardando.set(false); 
        }
      });
    }
  }

  eliminar(p: Proveedor) {
    const confirmar = confirm(¿Eliminar al proveedor "${p.nombre}"?);
    if (!confirmar) return;

    this.proveedoresService.eliminar(p.id).subscribe({
      next: () => {
        this.proveedores.update(arr => arr.filter(x => x.id !== p.id));
      },
      error: err => {
        console.error(err);
        this.error.set('Error al eliminar proveedor');
      }
    });
  }
}
