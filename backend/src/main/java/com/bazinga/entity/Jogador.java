package com.bazinga.entity;

import jakarta.persistence.*;

@Entity
@Table(schema = "seguranca" , name = "jogador")
public class Jogador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String fotoPath;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    @Enumerated(EnumType.STRING)
    private ClasseTF classetf;

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "time_id")
    private Time time;

    private Boolean liderTime;

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

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public ClasseTF getClassetf() {
        return classetf;
    }

    public void setClassetf(ClasseTF classetf) {
        this.classetf = classetf;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Boolean getLiderTime() {
        return liderTime;
    }

    public void setLiderTime(Boolean liderTime) {
        this.liderTime = liderTime;
    }

    public Long getId() {
        return id;
    }
}
