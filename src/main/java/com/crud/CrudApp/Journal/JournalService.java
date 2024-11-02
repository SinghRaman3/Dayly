package com.crud.CrudApp.Journal;

import com.crud.CrudApp.User.User;
import com.crud.CrudApp.User.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {
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

    public void deleteById(ObjectId id, String userName) {
        try {
            User user = userService.getByUsername(userName);
            user.getJournalEntries().removeIf(journal -> journal.getId().equals(id));
            userService.saveUser(user);
            journalRepository.deleteById(String.valueOf(id));
        }
        catch (Exception e){
            throw new RuntimeException("Error while deleting journal./n", e);
        }
    }

    public void update(Journal journal) {
        journalRepository.save(journal);
    }
}
