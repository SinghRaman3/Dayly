package com.crud.CrudApp.Journal;

import com.crud.CrudApp.User.User;
import com.crud.CrudApp.User.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journals")
public class JournalController {
    private static final Logger log = LoggerFactory.getLogger(JournalController.class);
    @Autowired
    private JournalService journalService;
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Journal> createJournal(@RequestBody Journal journal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        journalService.save(journal, userName);
        return new ResponseEntity<>(journal, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Journal>> getAllJournals() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.getByUsername(userName);
        List<Journal> journals= user.getJournalEntries();
        if(journals.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(journals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable ObjectId id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.getByUsername(userName);
        List<Journal> collection = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).toList();
        if(!collection.isEmpty()) {
            Optional<Journal> journal = journalService.getById(id);
            if(journal.isPresent()){
                return new ResponseEntity<>(journal.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        boolean removed = journalService.deleteById(id, userName);
        if (!removed){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Journal> updateJournal(@PathVariable ObjectId id, @RequestBody Journal newJournal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.getByUsername(userName);
        List<Journal> collection = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).toList();
        if(!collection.isEmpty()) {
            Optional<Journal> journalEntry = journalService.getById(id);
            if(journalEntry.isPresent()){
                Journal oldJournal = journalEntry.get();
                oldJournal.setTitle(newJournal.getTitle() != null && newJournal.getTitle().isEmpty() ? newJournal.getTitle() : oldJournal.getTitle());
                oldJournal.setContent(oldJournal.getContent() != null && newJournal.getContent() != null ? newJournal.getContent() : oldJournal.getContent());
                journalService.update(oldJournal);
                return new ResponseEntity<>(newJournal, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
