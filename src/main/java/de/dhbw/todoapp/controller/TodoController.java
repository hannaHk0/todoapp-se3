package de.dhbw.todoapp.controller;

import de.dhbw.todoapp.model.Todo;
import de.dhbw.todoapp.model.TodoStatus;
import de.dhbw.todoapp.service.CategoryService;
import de.dhbw.todoapp.service.TodoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private final TodoService todoService;
    private final CategoryService categoryService;

    public TodoController(TodoService todoService, CategoryService categoryService) {
        this.todoService = todoService;
        this.categoryService = categoryService;
    }
    /** Startseite: zeigt alle Todos an. */
    @GetMapping("/")
    public String alleAnzeigen(Model model) {
        model.addAttribute("todo", todoService.alleToDosFinden());
        return "index";
    }
    /** Zeigt das Formular für ein neues Todo. */
    @GetMapping("/todo/neu")
    public String neuesFormular(Model model) {
        model.addAttribute("todo", new Todo());
        model.addAttribute("kategorien", categoryService.alleKategorienFinden());
        model.addAttribute("statusOptionen", TodoStatus.values());
        return "todo-formular";
    }
    /** Zeigt das Formular zum Bearbeiten eines bestehenden Todos. */
    @GetMapping("/todo/bearbeiten/{id}")
    public String bearbeitenFormular(@PathVariable Long id, Model model) {
        Optional<Todo> todo = todoService.todoFinden(id);
        if (todo.isPresent()) {
            model.addAttribute("todo", todo.get());
            model.addAttribute("kategorien", categoryService.alleKategorienFinden());
            model.addAttribute("statusOptionen", TodoStatus.values());
            return "todo-formular";
        }
        logger.warn("Todo mit ID {} nicht gefunden", id);
        return "redirect:/";
    }
    /** Speichert ein neues oder bearbeitetes Todo. */
    @PostMapping("/todo/speichern")
    public String speichern(@ModelAttribute Todo todo, Model model, RedirectAttributes attrs) {
        if (todo.getTitel() == null || todo.getTitel().isBlank()) {
            model.addAttribute("fehler", "Titel darf nicht leer sein.");
            model.addAttribute("kategorien", categoryService.alleKategorienFinden());
            model.addAttribute("statusOptionen",TodoStatus.values());
            return "todo-formular";
        }
        todoService.todoSpeichern(todo);
        attrs.addFlashAttribute("erfolg", "To-Do erfolgreich gespeichert.");
        return "redirect:/";
    }
    /** Löscht ein Todo – Sicherheitsabfrage kommt vom HTML. */
    @PostMapping("/todo/loeschen/{id}")
    public String loeschen(@PathVariable Long id, RedirectAttributes attrs) {
        todoService.todoLoeschen(id);
        attrs.addFlashAttribute("erfolg", "To-Do erfolgreich gelöscht.");
        return "redirect:/";
    }
}