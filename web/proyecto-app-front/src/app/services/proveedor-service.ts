import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Proveedor {
  id: number;
  nombre: string;
  contacto: string;
  telefono: string;
  ciudad: string;
}

@Injectable({
  providedIn: 'root'
})
export class ProveedorService {
  private baseUrl = 'http://localhost:8080/api/proveedores'; // cambiado a plural

  constructor(private http: HttpClient) { }

  listar(): Observable<Proveedor[]> {
    return this.http.get<Proveedor[]>(this.baseUrl);
  }

  crear(proveedor: Proveedor): Observable<Proveedor> {
    return this.http.post<Proveedor>(this.baseUrl, proveedor);
  }
  
  actualizar(id: number, proveedor: Proveedor) {
    return this.http.put<Proveedor>(`${this.baseUrl}/${id}`, proveedor);
  }
  
  eliminar(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}