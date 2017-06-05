package com.gameexchange.gameexchange.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Conversacion.
 */
@Entity
@Table(name = "conversacion")
public class Conversacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creado")
    private ZonedDateTime creado;

    @ManyToOne
    private UserExt usuario1;

    @ManyToOne
    private UserExt usuario2;

    @ManyToOne
    private Producto producto;

    @OneToMany(mappedBy = "conversacion")
    @JsonIgnore
    private Set<Mensaje> mensajes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreado() {
        return creado;
    }

    public Conversacion creado(ZonedDateTime creado) {
        this.creado = creado;
        return this;
    }

    public void setCreado(ZonedDateTime creado) {
        this.creado = creado;
    }

    public Set<Mensaje> getMensajes() {
        return mensajes;
    }

    public Conversacion mensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
        return this;
    }

    public Conversacion addMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
        mensaje.setConversacion(this);
        return this;
    }

    public Conversacion removeMensaje(Mensaje mensaje) {
        mensajes.remove(mensaje);
        mensaje.setConversacion(null);
        return this;
    }

    public void setMensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }


    public UserExt getUsuario1() {
        return usuario1;
    }
    public void setUsuario1(UserExt usuario1) {
        this.usuario1 = usuario1;
    }

    public UserExt getUsuario2() {
        return usuario2;
    }
    public void setUsuario2(UserExt usuario2) {
        this.usuario2 = usuario2;
    }

    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Conversacion conversacion = (Conversacion) o;
        if (conversacion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, conversacion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Conversacion{" +
            "id=" + id +
            ", creado='" + creado + "'" +
            '}';
    }
}
