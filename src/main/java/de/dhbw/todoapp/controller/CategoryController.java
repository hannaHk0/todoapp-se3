package de.dhbw.todoapp.controller;
import de.dhbw.todoapp.model.Category;
import de.dhbw.todoapp.service.CategoryService;
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
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /** Startseite: alle Todos anzeigen */
    @GetMapping("/kategorien")
    public String alleAnzeigen(Model model) {
        model.addAttribute("kategorien", categoryService.alleKategorienFinden());
        model.addAttribute("kategorie", new Category());
        return "kategorien";
    }

    /** Formular für neues Todo anzeigen */
    @PostMapping("/kategorie/speichern")
    public String speichern(@ModelAttribute Category category,
                            Model model, RedirectAttributes attrs) {
        if (category.getName() == null || category.getName().isBlank()) {
            model.addAttribute("fehler", "Name darf nicht leer sein.");
            model.addAttribute("kategorien", categoryService.alleKategorienFinden());
            return "kategorien";
        }
        categoryService.kategorieSpeichern(category);
        attrs.addFlashAttribute("erfolg", "Kategorie erfolgreich gespeichert.");
        return "redirect:/kategorien";
    }

    /** Neues Todo speichern (Formular abgeschickt) */
    @GetMapping("/kategorie/bearbeiten/{id}")
    public String bearbeitenFormular(@PathVariable Long id, Model model) {
        Optional<Category> kategorie = categoryService.kategorieFinden(id);
        if (kategorie.isPresent()) {
            model.addAttribute("kategorie", kategorie.get());
            return "kategorien-formular";
        }
        logger.warn("Kategorie mit ID {} nicht gefunden", id);
        return "redirect:/kategorien";
    }

    /** Todo löschen – mit Sicherheitsabfrage im HTML */
    @PostMapping("/kategorie/loeschen/{id}")
    public String loeschen(@PathVariable Long id, RedirectAttributes attrs) {
        boolean erfolg = categoryService.kategorieLoeschen(id);
        if (!erfolg) {
            attrs.addFlashAttribute("fehler",
                    "Kategorie kann nicht gelöscht werden – es gibt noch zugeordnete To-Dos.");
        } else {
            attrs.addFlashAttribute("erfolg", "Kategorie erfolgreich gelöscht.");
        }
        return "redirect:/kategorien";
    }
}
