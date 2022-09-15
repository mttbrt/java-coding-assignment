package javacodingassignment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javacodingassignment.model.Entry;
import javax.annotation.PreDestroy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LogReader implements ItemReader<Entry> {

  private static final Logger logger = LogManager.getLogger(LogReader.class.getName());
  private final ObjectMapper mapper;
  private BufferedReader reader;

  public LogReader(@Value("${app.logfile}") String logfile) throws FileNotFoundException {
    setReader(logfile);
    mapper = new ObjectMapper();
  }

  public void setReader(String logfile) throws FileNotFoundException {
    reader = new BufferedReader(new FileReader(logfile));
  }

  @Override
  public Entry read() {
    try {
      String logEntry = reader.readLine();
      return logEntry != null ? mapper.readValue(logEntry, Entry.class) : null;
    } catch (IOException e) {
      logger.debug("Exception while reading log entry.");
      return null;
    }
  }

  @PreDestroy
  public void close() {
    try {
      reader.close();
    } catch (IOException e) {
      logger.error("Could not close logfile stream reader.", e);
    }
  }
}
