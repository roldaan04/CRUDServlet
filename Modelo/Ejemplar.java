package org.example.crudBiblio.Modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.example.crudBiblio.Modelo.Libro;
import org.example.crudBiblio.Modelo.Prestamo;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ejemplar")
public class Ejemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "isbn", nullable = false)
    @JsonBackReference
    private org.example.crudBiblio.Modelo.Libro libro;

    @ColumnDefault("'Disponible'")
    @Lob
    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "ejemplar")
    @JsonManagedReference
    private List<org.example.crudBiblio.Modelo.Prestamo> prestamos = new ArrayList<>();

    public Ejemplar(){}

    public Ejemplar(org.example.crudBiblio.Modelo.Libro libro, String estado) {
        this.libro = libro;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public org.example.crudBiblio.Modelo.Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro isbn) {
        this.libro = isbn;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<org.example.crudBiblio.Modelo.Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    @Override
    public String toString() {
        return "Ejemplar{" +
                "id=" + id +
                ", libro=" + libro.getTitulo() +
                ", estado='" + estado + '\'' +
                "\n}";
    }
}