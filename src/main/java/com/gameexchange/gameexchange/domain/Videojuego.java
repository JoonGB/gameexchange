package com.gameexchange.gameexchange.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Videojuego.
 */
@Entity
@Table(name = "videojuego")
public class Videojuego implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany
    @JoinTable(name = "videojuego_categoria",
               joinColumns = @JoinColumn(name="videojuegos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="categorias_id", referencedColumnName="ID"))
    private Set<Categoria> categorias = new HashSet<>();

    @OneToMany(mappedBy = "videojuego")
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();

    private String miniatura;
    private String caratula;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Videojuego nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public Videojuego categorias(Set<Categoria> categorias) {
        this.categorias = categorias;
        return this;
    }

    public Videojuego addCategoria(Categoria categoria) {
        categorias.add(categoria);
        categoria.getVideojuegos().add(this);
        return this;
    }

    public Videojuego removeCategoria(Categoria categoria) {
        categorias.remove(categoria);
        categoria.getVideojuegos().remove(this);
        return this;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Videojuego productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Videojuego addProducto(Producto producto) {
        productos.add(producto);
        producto.setVideojuego(this);
        return this;
    }

    public Videojuego removeProducto(Producto producto) {
        productos.remove(producto);
        producto.setVideojuego(null);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    public String getCaratula() {
        return caratula;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Videojuego videojuego = (Videojuego) o;
        if (videojuego.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, videojuego.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Videojuego{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
