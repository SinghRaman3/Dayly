package com.crud.CrudApp.Journal;

import com.crud.CrudApp.User.User;
import com.crud.CrudApp.User.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {
    private static final Logger log = LoggerFactory.getLogger(JournalService.class);
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void save(Journal journal, String userName) {
        try{
            User user = userService.getByUsername(userName);
            journal.setDate(LocalDateTime.now());
            Journal saved = journalRepository.save(journal);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Error while saving journal./n", e);
        }
    }

    public Optional<Journal> getById(ObjectId id) {
        return journalRepository.findById(String.valueOf(id));
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userService.getByUsername(userName);
            removed = user.getJournalEntries().removeIf(journal -> journal.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalRepository.deleteById(String.valueOf(id));
            }
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error while deleting journal./n", e);
        }
        return removed;
    }

    public void update(Journal journal) {
        journalRepository.save(journal);
    }
}
