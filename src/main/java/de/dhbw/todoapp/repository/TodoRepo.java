package de.dhbw.todoapp.repository;
import de.dhbw.todoapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Ermöglicht den Zugriff auf die Todo-Tabelle in der Datenbank.
 */
public interface TodoRepo extends JpaRepository<Todo, Long>{
}
