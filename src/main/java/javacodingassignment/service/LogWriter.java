package javacodingassignment.service;

import java.util.List;
import javacodingassignment.model.Event;
import javacodingassignment.repository.EventRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogWriter implements ItemWriter<Event> {

  @Autowired
  private EventRepository eventRepo;

  @Override
  public void write(List<? extends Event> events) {
    eventRepo.saveAll(events);
  }
}
