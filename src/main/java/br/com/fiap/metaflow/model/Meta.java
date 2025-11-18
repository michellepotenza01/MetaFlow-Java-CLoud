package br.com.fiap.metaflow.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mf_metas")
public class Meta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  @Column(nullable = false)
  private String titulo;

  @Enumerated(EnumType.STRING)
  private Categoria categoria;

  @Column(nullable = true)
  private String descricao;

  @Column(nullable = false)
  private Integer valorAlvo;

  @Column(nullable = false)
  private Integer valorAtual;

  @Column(nullable = false)
  private LocalDate prazo;

  @Enumerated(EnumType.STRING)
  private Status status;

  public enum Categoria {
    HABILIDADES, CARREIRA, SAUDE
  }

  public enum Status {
    ATIVA, INATIVA, CONCLUIDA
  }

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

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Integer getValorAlvo() {
    return valorAlvo;
  }

  public void setValorAlvo(Integer valorAlvo) {
    this.valorAlvo = valorAlvo;
  }

  public Integer getValorAtual() {
    return valorAtual;
  }

  public void setValorAtual(Integer valorAtual) {
    this.valorAtual = valorAtual;
  }

  public LocalDate getPrazo() {
    return prazo;
  }

  public void setPrazo(LocalDate prazo) {
    this.prazo = prazo;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}