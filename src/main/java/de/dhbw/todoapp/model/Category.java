package de.dhbw.todoapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
/**
 * Repräsentiert eine Kategorie für To-Dos (z.B. "Arbeit", "Privat").
 */
@Entity
public class Category {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Todo> todos;
    // Getter und Setter
    public Long getId() {return id;}
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public List<Todo> getTodos() {
        return todos;
    }
    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }
}
