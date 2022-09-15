package javacodingassignment.service;

import javacodingassignment.model.Event;
import javacodingassignment.repository.EntryRepository;
import javacodingassignment.repository.EventRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionListener extends JobExecutionListenerSupport {

  private static final Logger logger = LogManager.getLogger(JobCompletionListener.class.getName());

  @Autowired
  private EventRepository eventRepo;
  @Autowired
  private EntryRepository eRepo;

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
      logger.trace(String.format("Job %d is completed.", jobExecution.getJobId()));
      for (Event event : eventRepo.findAll()) {
        logger.debug(event);
      }
    }
  }

}
