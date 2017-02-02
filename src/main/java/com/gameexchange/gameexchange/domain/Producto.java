package com.gameexchange.gameexchange.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "creado")
    private ZonedDateTime creado;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    private Videojuego videojuego;

    @ManyToOne
    private User usuario;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private Set<Foto> fotos = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private Set<Venta> ventas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getCreado() {
        return creado;
    }

    public Producto creado(ZonedDateTime creado) {
        this.creado = creado;
        return this;
    }

    public void setCreado(ZonedDateTime creado) {
        this.creado = creado;
    }

    public Double getPrecio() {
        return precio;
    }

    public Producto precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Videojuego getVideojuego() {
        return videojuego;
    }

    public Producto videojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
        return this;
    }

    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
    }

    public User getUsuario() {
        return usuario;
    }

    public Producto usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Set<Foto> getFotos() {
        return fotos;
    }

    public Producto fotos(Set<Foto> fotos) {
        this.fotos = fotos;
        return this;
    }

    public Producto addFoto(Foto foto) {
        fotos.add(foto);
        foto.setProducto(this);
        return this;
    }

    public Producto removeFoto(Foto foto) {
        fotos.remove(foto);
        foto.setProducto(null);
        return this;
    }

    public void setFotos(Set<Foto> fotos) {
        this.fotos = fotos;
    }

    public Set<Venta> getVentas() {
        return ventas;
    }

    public Producto ventas(Set<Venta> ventas) {
        this.ventas = ventas;
        return this;
    }

    public Producto addVenta(Venta venta) {
        ventas.add(venta);
        venta.setProducto(this);
        return this;
    }

    public Producto removeVenta(Venta venta) {
        ventas.remove(venta);
        venta.setProducto(null);
        return this;
    }

    public void setVentas(Set<Venta> ventas) {
        this.ventas = ventas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Producto producto = (Producto) o;
        if (producto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, producto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + id +
            ", descripcion='" + descripcion + "'" +
            ", creado='" + creado + "'" +
            ", precio='" + precio + "'" +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
