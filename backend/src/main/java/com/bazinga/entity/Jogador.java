package com.bazinga.entity;

import com.bazinga.entity.enums.ClasseTF;
import com.bazinga.entity.enums.Perfil;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(cascade = {} , fetch = FetchType.LAZY)
    @JoinTable(
            schema = "controle",
            name = "classe_jogador",
            joinColumns = @JoinColumn(name = "jogador_id"),
            inverseJoinColumns = @JoinColumn(name = "classe_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<ClasseTFEntity> classes = new HashSet<>();

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "time_id")
    private Time time;

    private Boolean liderTime;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jogador")
    private List<Venda> vendas;

    public Set<ClasseTFEntity> getClasses() {
        return classes;
    }

    public void setClasses(Set<ClasseTFEntity> classes) {
        this.classes = classes;
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
