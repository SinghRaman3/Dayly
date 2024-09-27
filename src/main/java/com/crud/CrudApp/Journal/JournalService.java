package com.crud.CrudApp.Journal;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {
    @Autowired
    private JournalRepository journalRepository;

    public void save(Journal journal) {
        journal.setDate(LocalDateTime.now());
        journalRepository.save(journal);
    }

    public List<Journal> getAll() {
        return journalRepository.findAll();
    }

    public Optional<Journal> getById(ObjectId id) {
        return journalRepository.findById(String.valueOf(id));
    }

    public void deleteById(ObjectId id) {
        journalRepository.deleteById(String.valueOf(id));
    }

    public void update(Journal journal) {
        journalRepository.save(journal);
    }
}
