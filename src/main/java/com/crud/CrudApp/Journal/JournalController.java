package com.crud.CrudApp.Journal;

import com.crud.CrudApp.User.User;
import com.crud.CrudApp.User.UserService;
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
    @Autowired
    private UserService userService;

    @PostMapping("/{userName}")
    public ResponseEntity<Journal> createJournal(@RequestBody Journal journal, @PathVariable String userName) {
        try{
            journalService.save(journal, userName);
            return new ResponseEntity<>(journal, HttpStatus.CREATED);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<List<Journal>> getAllJournals(@PathVariable String userName) {
        try{
            User user = userService.getByUsername(userName);
            List<Journal> journals= user.getJournalEntries();
//            List<Journal> journals = journalService.getAll(user);
            if(journals.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(journals, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{username}/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable String username,@PathVariable ObjectId id) {
        try{
            Optional<Journal> journal = journalService.getById(id);
            return journal.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userName}/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable String userName,@PathVariable ObjectId id) {
        try {
            journalService.deleteById(id, userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userName}/{id}")
    public ResponseEntity<Journal> updateJournal(@PathVariable String userName, @PathVariable ObjectId id, @RequestBody Journal newJournal) {
        Journal oldJournal =  journalService.getById(id).orElse(null);
        if(oldJournal != null){
            oldJournal.setTitle(newJournal.getTitle() != null && newJournal.getTitle().isEmpty() ? newJournal.getTitle() : oldJournal.getTitle());
            oldJournal.setContent(oldJournal.getContent() != null && newJournal.getContent() != null ? newJournal.getContent() : oldJournal.getContent());
            journalService.update(oldJournal);
            return new ResponseEntity<>(newJournal, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
