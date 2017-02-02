package com.gameexchange.gameexchange.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Mensaje.
 */
@Entity
@Table(name = "mensaje")
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creado")
    private ZonedDateTime creado;

    @Column(name = "texto")
    private String texto;

    @ManyToOne
    private Conversacion conversacion;

    @ManyToOne
    private User emisor;

    @ManyToOne
    private User receptor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreado() {
        return creado;
    }

    public Mensaje creado(ZonedDateTime creado) {
        this.creado = creado;
        return this;
    }

    public void setCreado(ZonedDateTime creado) {
        this.creado = creado;
    }

    public String getTexto() {
        return texto;
    }

    public Mensaje texto(String texto) {
        this.texto = texto;
        return this;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Conversacion getConversacion() {
        return conversacion;
    }

    public Mensaje conversacion(Conversacion conversacion) {
        this.conversacion = conversacion;
        return this;
    }

    public void setConversacion(Conversacion conversacion) {
        this.conversacion = conversacion;
    }

    public User getEmisor() {
        return emisor;
    }

    public Mensaje emisor(User user) {
        this.emisor = user;
        return this;
    }

    public void setEmisor(User user) {
        this.emisor = user;
    }

    public User getReceptor() {
        return receptor;
    }

    public Mensaje receptor(User user) {
        this.receptor = user;
        return this;
    }

    public void setReceptor(User user) {
        this.receptor = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mensaje mensaje = (Mensaje) o;
        if (mensaje.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mensaje.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Mensaje{" +
            "id=" + id +
            ", creado='" + creado + "'" +
            ", texto='" + texto + "'" +
            '}';
    }
}
