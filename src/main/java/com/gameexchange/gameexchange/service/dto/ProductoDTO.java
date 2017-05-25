package com.gameexchange.gameexchange.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gameexchange.gameexchange.domain.Foto;
import com.gameexchange.gameexchange.domain.User;
import com.gameexchange.gameexchange.domain.Venta;
import com.gameexchange.gameexchange.domain.Videojuego;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by DAM on 3/5/17.
 */
public class ProductoDTO {

    private Long id;
    private String descripcion;
    private ZonedDateTime creado;
    private Double precio;
    private String nombre;
    private Videojuego videojuego;
    private User usuario;
    private List<Foto> fotos = new ArrayList<>();
    private List<Venta> ventas = new ArrayList<>();
    private Foto fotoPrincipal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getCreado() {
        return creado;
    }

    public void setCreado(ZonedDateTime creado) {
        this.creado = creado;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Videojuego getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public Foto getFotoPrincipal() {
        return fotoPrincipal;
    }

    public void setFotoPrincipal(Foto fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }
}
