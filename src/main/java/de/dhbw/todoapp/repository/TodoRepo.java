package de.dhbw.todoapp.repository;
import de.dhbw.todoapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TodoRepo extends JpaRepository<Todo, Long>{
}
