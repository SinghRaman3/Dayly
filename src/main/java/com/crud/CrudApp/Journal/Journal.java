package com.crud.CrudApp.Journal;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
public class Journal {
    @Id
    private ObjectId id;
    private String title;
    private LocalDateTime date;
    private String content;
}
