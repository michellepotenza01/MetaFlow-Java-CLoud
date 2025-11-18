package br.com.fiap.metaflow.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;;

@Entity
@Table(name = "mf_usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String tituloProfissional;

  @Column(nullable = false)
  private String objetivoCarreira;

  @Column(nullable = false)
  private LocalDateTime dataCriacao;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
  private List<Meta> metas = new ArrayList<>();

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
  private List<Checkin> checkins = new ArrayList<>();

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
  private List<Habilidade> habilidades = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTituloProfissional() {
    return tituloProfissional;
  }

  public void setTituloProfissional(String tituloProfissional) {
    this.tituloProfissional = tituloProfissional;
  }

  public String getObjetivoCarreira() {
    return objetivoCarreira;
  }

  public void setObjetivoCarreira(String objetivoCarreira) {
    this.objetivoCarreira = objetivoCarreira;
  }

  public LocalDateTime getDataCriacao() {
    return dataCriacao;
  }

  public void setDataCriacao(LocalDateTime dataCriacao) {
    this.dataCriacao = dataCriacao;
  }

  public List<Meta> getMetas() {
    return metas;
  }

  public void setMetas(List<Meta> metas) {
    this.metas = metas;
  }

  public List<Checkin> getCheckins() {
    return checkins;
  }

  public void setCheckins(List<Checkin> checkins) {
    this.checkins = checkins;
  }

  public List<Habilidade> getHabilidades() {
    return habilidades;
  }

  public void setHabilidades(List<Habilidade> habilidades) {
    this.habilidades = habilidades;
  }

}