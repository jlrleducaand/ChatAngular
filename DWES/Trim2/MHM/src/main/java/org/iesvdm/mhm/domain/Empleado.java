package org.iesvdm.mhm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "empleado")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  //solo los que tienen include
public class Empleado {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    @EqualsAndHashCode.Include
    private long id;

    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;

    private String ccc_empleado;

    @ManyToMany
    @JoinTable(name = "empl_clientes",
        joinColumns = @JoinColumn (name = "id_cliente"),
        inverseJoinColumns = @JoinColumn(name = "id_empleado")
    )
    //@JsonIgnore         //Rompe el lazo de Serializacion
    //@ToStringExclude    //Rompe el lazo de Serializacion
    Set<Cliente> clientes = new HashSet<>();


    @OneToMany(mappedBy = "empleado", fetch = FetchType.EAGER)
    @JsonIgnore         //Rompe el lazo de Serializacion
    @ToStringExclude    //Rompe el lazo de Serializacion
    Set<Pedido> pedidos = new HashSet<>();

    @Column(name = "fecha_alta")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private Date fecha_alta;




    // ******* CONSTRUCTORES PARA TESTS *********


    public Empleado(int id, String nombre, HashSet<Pedido> pedidos) {
        this.id = id;
        this.nombre = nombre;
        this.pedidos = pedidos;
    }


    public Empleado(String nombre, String apellidos,  Date fecha_alta) {
        this.id = 0;
        this.apellidos = apellidos;
        this.nombre = nombre;
        this.fecha_alta = fecha_alta;
        this.pedidos = new HashSet<Pedido>();
    }

}
