package com.gameexchange.gameexchange.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creado")
    private ZonedDateTime creado;

    @Column(name = "valoracion_cliente")
    private Integer valoracionCliente;

    @Column(name = "valoreacion_vendedor")
    private Integer valoreacionVendedor;

    @Column(name = "comentario_cliente")
    private String comentarioCliente;

    @Column(name = "comentario_vendedor")
    private String comentarioVendedor;

    @ManyToOne
    private User vendedor;

    @ManyToOne
    private User cliente;

    @ManyToOne
    private Producto producto;

    @OneToMany(mappedBy = "venta")
    @JsonIgnore
    private Set<Incidencia> incidencias = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreado() {
        return creado;
    }

    public Venta creado(ZonedDateTime creado) {
        this.creado = creado;
        return this;
    }

    public void setCreado(ZonedDateTime creado) {
        this.creado = creado;
    }

    public Integer getValoracionCliente() {
        return valoracionCliente;
    }

    public Venta valoracionCliente(Integer valoracionCliente) {
        this.valoracionCliente = valoracionCliente;
        return this;
    }

    public void setValoracionCliente(Integer valoracionCliente) {
        this.valoracionCliente = valoracionCliente;
    }

    public Integer getValoreacionVendedor() {
        return valoreacionVendedor;
    }

    public Venta valoreacionVendedor(Integer valoreacionVendedor) {
        this.valoreacionVendedor = valoreacionVendedor;
        return this;
    }

    public void setValoreacionVendedor(Integer valoreacionVendedor) {
        this.valoreacionVendedor = valoreacionVendedor;
    }

    public String getComentarioCliente() {
        return comentarioCliente;
    }

    public Venta comentarioCliente(String comentarioCliente) {
        this.comentarioCliente = comentarioCliente;
        return this;
    }

    public void setComentarioCliente(String comentarioCliente) {
        this.comentarioCliente = comentarioCliente;
    }

    public String getComentarioVendedor() {
        return comentarioVendedor;
    }

    public Venta comentarioVendedor(String comentarioVendedor) {
        this.comentarioVendedor = comentarioVendedor;
        return this;
    }

    public void setComentarioVendedor(String comentarioVendedor) {
        this.comentarioVendedor = comentarioVendedor;
    }

    public User getVendedor() {
        return vendedor;
    }

    public Venta vendedor(User user) {
        this.vendedor = user;
        return this;
    }

    public void setVendedor(User user) {
        this.vendedor = user;
    }

    public User getCliente() {
        return cliente;
    }

    public Venta cliente(User user) {
        this.cliente = user;
        return this;
    }

    public void setCliente(User user) {
        this.cliente = user;
    }

    public Producto getProducto() {
        return producto;
    }

    public Venta producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Set<Incidencia> getIncidencias() {
        return incidencias;
    }

    public Venta incidencias(Set<Incidencia> incidencias) {
        this.incidencias = incidencias;
        return this;
    }

    public Venta addIncidencia(Incidencia incidencia) {
        incidencias.add(incidencia);
        incidencia.setVenta(this);
        return this;
    }

    public Venta removeIncidencia(Incidencia incidencia) {
        incidencias.remove(incidencia);
        incidencia.setVenta(null);
        return this;
    }

    public void setIncidencias(Set<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Venta venta = (Venta) o;
        if (venta.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, venta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Venta{" +
            "id=" + id +
            ", creado='" + creado + "'" +
            ", valoracionCliente='" + valoracionCliente + "'" +
            ", valoreacionVendedor='" + valoreacionVendedor + "'" +
            ", comentarioCliente='" + comentarioCliente + "'" +
            ", comentarioVendedor='" + comentarioVendedor + "'" +
            '}';
    }
}
