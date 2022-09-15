package javacodingassignment;

import javacodingassignment.repository.EntryRepository;
import javacodingassignment.repository.EventRepository;
import javacodingassignment.service.LogReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.TestPropertySource;

@SpringBatchTest
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@TestPropertySource("classpath:test.properties")
public class IntegrationTest {

  @Value("${app.logfile.base-path}")
  private String testLogfilesBasePath;
  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;
  @Autowired
  private LogReader logReader;
  @Autowired
  private EventRepository eventRepository;
  @Autowired
  private EntryRepository entryRepository;

  @AfterEach
  public void cleanUpDb() {
    eventRepository.truncateSchemaPublicAndCommit();
  }

  @Test
  public void events_with_different_alert_value() throws Exception {
    logReader.setReader(testLogfilesBasePath + "logfile-0.txt");
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();

    Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    Assertions.assertEquals(2, eventRepository.findAll().spliterator().getExactSizeIfKnown());
    Assertions.assertEquals(0, entryRepository.findAll().spliterator().getExactSizeIfKnown());
  }

  @Test
  public void events_with_duplicate_log_entry() throws Exception {
    logReader.setReader(testLogfilesBasePath + "logfile-1.txt");
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();

    Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    Assertions.assertEquals(2, eventRepository.findAll().spliterator().getExactSizeIfKnown());
    Assertions.assertEquals(0, entryRepository.findAll().spliterator().getExactSizeIfKnown());
  }

  @Test
  public void started_event_without_finished_event() throws Exception {
    logReader.setReader(testLogfilesBasePath + "logfile-2.txt");
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();

    Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    Assertions.assertEquals(2, eventRepository.findAll().spliterator().getExactSizeIfKnown());
    Assertions.assertEquals(1, entryRepository.findAll().spliterator().getExactSizeIfKnown());
  }

}
