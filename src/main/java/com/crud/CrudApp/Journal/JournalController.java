package com.crud.CrudApp.Journal;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journals")
public class JournalController {
    private static final Logger log = LoggerFactory.getLogger(JournalController.class);
    @Autowired
    private JournalService journalService;

    @PostMapping
    public ResponseEntity<Journal> createJournal(@RequestBody Journal journal) {
        try{
            journalService.save(journal);
            return new ResponseEntity<>(journal, HttpStatus.CREATED);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Journal>> getAllJournals() {
        try{
            List<Journal> journals = journalService.getAll();
            if(journals.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(journals, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable ObjectId id) {
        try{
            Optional<Journal> journal = journalService.getById(id);
            if(journal.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(journal.get(), HttpStatus.OK);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id) {
        try {
            journalService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Journal> updateJournal(@PathVariable ObjectId id, @RequestBody Journal newJournal) {
        Journal oldJournal =  journalService.getById(id).orElse(null);
        if(oldJournal != null){
            oldJournal.setTitle(oldJournal.getTitle() != null && newJournal.getTitle() != null ? newJournal.getTitle() : oldJournal.getTitle());
            oldJournal.setContent(oldJournal.getContent() != null && newJournal.getContent() != null ? newJournal.getContent() : oldJournal.getContent());
            journalService.update(oldJournal);
            return new ResponseEntity<>(newJournal, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
