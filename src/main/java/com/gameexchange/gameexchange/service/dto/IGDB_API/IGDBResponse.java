package com.gameexchange.gameexchange.service.dto.IGDB_API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JonGarcia on 27/05/2017.
 */
public class IGDBResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("genres")
    @Expose
    private List<Integer> genres = null;
    @SerializedName("cover")
    @Expose
    private Cover cover;


    public IGDBResponse() {  }

    public IGDBResponse(Integer id, String name, List<Integer> genres, Cover cover) {
        super();
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.cover = cover;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
}
