package com.example.notes;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteRepository notes;

    public NoteController(NoteRepository notes) {
        this.notes = notes;
    }

    @GetMapping
    public List<Note> list() {
        return notes.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note create(@Valid @RequestBody Note note) {
        note.setId(null);
        return notes.save(note);
    }

    @PutMapping("/{id}")
    public Note update(@PathVariable Long id, @Valid @RequestBody Note note) {
        if (!notes.existsById(id)) {
            throw notFound(id);
        }
        note.setId(id);
        return notes.save(note);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!notes.existsById(id)) {
            throw notFound(id);
        }
        notes.deleteById(id);
    }

    private static ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Note " + id + " not found");
    }
}
