package br.com.fiap.metaflow.model;

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
@Table(name = "mf_habilidades")
public class Habilidade {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String categoria;

  @Column(nullable = false)
  private Integer nivelAtual;

  @Column(nullable = false)
  private Integer nivelDesejado;

  @Column(nullable = false)
  private Boolean emAprendizado;

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

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public Integer getNivelAtual() {
    return nivelAtual;
  }

  public void setNivelAtual(Integer nivelAtual) {
    this.nivelAtual = nivelAtual;
  }

  public Integer getNivelDesejado() {
    return nivelDesejado;
  }

  public void setNivelDesejado(Integer nivelDesejado) {
    this.nivelDesejado = nivelDesejado;
  }

  public Boolean getEmAprendizado() {
    return emAprendizado;
  }

  public void setEmAprendizado(Boolean emAprendizado) {
    this.emAprendizado = emAprendizado;
  }

}