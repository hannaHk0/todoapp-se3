package de.dhbw.todoapp.service;
import de.dhbw.todoapp.model.Todo;
import de.dhbw.todoapp.repository.TodoRepo;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);
    private final TodoRepo todoRepo;

    public TodoService (TodoRepo todoRepo){this.todoRepo = todoRepo;}

    public List<Todo> alleToDosFinden () {return todoRepo.findAll();}
    public Optional<Todo> todoFinden (Long id){return todoRepo.findById(id);}
    public void todoSpeichern (Todo todo){
        try {
            todoRepo.save(todo);
        } catch (Exception e){
            logger.warn("Fehler beim Speichern des Todos: {}", e.getMessage());
        }
    }
    public void todoLoeschen (Long id){
        try {
            todoRepo.deleteById(id);
        } catch (Exception e){
            logger.warn("Fehler beim Löschen des Todos mit ID {}: {}",id,e.getMessage());
        }
    }
}
