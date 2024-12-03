package com.bazinga.entity;

import com.bazinga.entity.enums.Perfil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Entity
@Table(schema = "seguranca" , name = "jogador")
public class Jogador implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Email
    private String email;

    private String senha;

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
    @JoinColumn(name = "time_id" , nullable = true)
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.perfil.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void setSenhaEncriptada(String senha) {
        this.senha = new BCryptPasswordEncoder().encode(senha);
    }
}
