package br.com.fiap.metaflow.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mf_checkins")
public class Checkin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  @Column(nullable = false)
  private LocalDate data;

  @Column(nullable = false)
  private Integer humor;

  @Column(nullable = false)
  private Integer qualidadeSono;

  @Column(nullable = false)
  private Integer nivelEstresse;

  @Column(nullable = false)
  private Integer produtividade;

  @Column(nullable = false)
  private Integer tempoTrabalho;

  @Column(nullable = false)
  private Integer tempoAprendizado;

  @Column(nullable = false)
  private Integer tempoLazer;

  @Column
  private String anotacoes;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public Integer getHumor() {
    return humor;
  }

  public void setHumor(Integer humor) {
    this.humor = humor;
  }

  public Integer getQualidadeSono() {
    return qualidadeSono;
  }

  public void setQualidadeSono(Integer qualidadeSono) {
    this.qualidadeSono = qualidadeSono;
  }

  public Integer getNivelEstresse() {
    return nivelEstresse;
  }

  public void setNivelEstresse(Integer nivelEstresse) {
    this.nivelEstresse = nivelEstresse;
  }

  public Integer getProdutividade() {
    return produtividade;
  }

  public void setProdutividade(Integer produtividade) {
    this.produtividade = produtividade;
  }

  public Integer getTempoTrabalho() {
    return tempoTrabalho;
  }

  public void setTempoTrabalho(Integer tempoTrabalho) {
    this.tempoTrabalho = tempoTrabalho;
  }

  public Integer getTempoAprendizado() {
    return tempoAprendizado;
  }

  public void setTempoAprendizado(Integer tempoAprendizado) {
    this.tempoAprendizado = tempoAprendizado;
  }

  public Integer getTempoLazer() {
    return tempoLazer;
  }

  public void setTempoLazer(Integer tempoLazer) {
    this.tempoLazer = tempoLazer;
  }

  public String getAnotacoes() {
    return anotacoes;
  }

  public void setAnotacoes(String anotacoes) {
    this.anotacoes = anotacoes;
  }

}