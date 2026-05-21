package de.dhbw.todoapp.repository;
 import de.dhbw.todoapp.model.Category;
 import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepo extends JpaRepository<Category, Long> {
}
