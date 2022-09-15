package javacodingassignment.service;

import static java.lang.Math.abs;

import java.util.Optional;
import javacodingassignment.model.Entry;
import javacodingassignment.model.Event;
import javacodingassignment.repository.EntryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogProcessor implements ItemProcessor<Entry, Event> {

  private static final Logger logger = LogManager.getLogger(LogProcessor.class.getName());

  @Autowired
  private EntryRepository entryRepo;

  @Override
  public Event process(Entry entry) {
    Optional<Entry> entryInDb = entryRepo.findById(entry.getId());
    if (entryInDb.isPresent()) {
      if (entryInDb.get().getState().equals(entry.getState())) {
        logger.debug("Duplicated log entry found.");
        return null;
      }

      Event event = new Event(entry.getId(),
          abs(entryInDb.get().getTimestamp() - entry.getTimestamp()));
      if (entry.getType() != null && entry.getType().equals(entryInDb.get().getType())) {
        event.setType(entry.getType());
      }
      if (entry.getHost() != null && entry.getHost().equals(entryInDb.get().getHost())) {
        event.setHost(entry.getHost());
      }

      entryRepo.delete(entryInDb.get());
      return event;
    } else {
      entryRepo.save(entry);
      return null;
    }
  }
}
