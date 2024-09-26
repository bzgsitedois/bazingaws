package com.bazinga.entity;

import com.bazinga.entity.enums.ClasseTF;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "classe_tf")
public class ClasseTFEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ClasseTF classe;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClasseTF getClasse() {
        return classe;
    }

    public void setClasse(ClasseTF classe) {
        this.classe = classe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClasseTFEntity that = (ClasseTFEntity) o;
        return Objects.equals(id, that.id) && classe == that.classe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classe);
    }
}
