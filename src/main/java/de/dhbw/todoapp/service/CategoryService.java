package de.dhbw.todoapp.service;
import de.dhbw.todoapp.model.Category;
import de.dhbw.todoapp.repository.CategoryRepo;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepo categoryRepo;
    public CategoryService ( CategoryRepo categoryRepo){
        this.categoryRepo = categoryRepo;
    }
    /** Gibt alle Kategorien zurück. */
    public List<Category> alleKategorienFinden(){
        return categoryRepo.findAll();
    }
    /** Gibt eine einzelne Kategorie anhand der ID zurück. */
    public Optional<Category> kategorieFinden (Long id){
        return categoryRepo.findById(id);
    }
    /** Speichert eine neue oder aktualisiert eine bestehende Kategorie. */
    public void kategorieSpeichern (Category category){
        try {
            categoryRepo.save(category);
        } catch (Exception e){
            logger.warn("Fehler beim Speichern der Kategorie: {}", e.getMessage());
        }
    }
    /** Löscht eine Kategorie anhand der ID. */
    public boolean kategorieLoeschen (Long id){
        try {
            Optional<Category> kategorie = categoryRepo.findById(id);
            if (kategorie.isPresent() && !kategorie.get().getTodos().isEmpty()){
                logger.warn("Kategorie {} hat noch Todos, wird nicht gelöscht",id);
                return false;
            }
            categoryRepo.deleteById(id);
            return true;
        } catch (Exception e){
            logger.warn("Fehler beom Löschen der Kategorie mit ID {}: {}", id, e.getMessage());
            return false;
        }
    }
}
