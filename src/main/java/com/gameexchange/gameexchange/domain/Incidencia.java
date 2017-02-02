package com.gameexchange.gameexchange.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Incidencia.
 */
@Entity
@Table(name = "incidencia")
public class Incidencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creado")
    private ZonedDateTime creado;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "titulo")
    private String titulo;

    @ManyToOne
    private User usuario;

    @ManyToOne
    private Venta venta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreado() {
        return creado;
    }

    public Incidencia creado(ZonedDateTime creado) {
        this.creado = creado;
        return this;
    }

    public void setCreado(ZonedDateTime creado) {
        this.creado = creado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Incidencia descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public Incidencia titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public User getUsuario() {
        return usuario;
    }

    public Incidencia usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Venta getVenta() {
        return venta;
    }

    public Incidencia venta(Venta venta) {
        this.venta = venta;
        return this;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Incidencia incidencia = (Incidencia) o;
        if (incidencia.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, incidencia.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Incidencia{" +
            "id=" + id +
            ", creado='" + creado + "'" +
            ", descripcion='" + descripcion + "'" +
            ", titulo='" + titulo + "'" +
            '}';
    }
}
