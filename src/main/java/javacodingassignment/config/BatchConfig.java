package javacodingassignment.config;

import javacodingassignment.model.Entry;
import javacodingassignment.model.Event;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

  @Autowired
  public JobBuilderFactory jobBuilderFactory;
  @Autowired
  public StepBuilderFactory stepBuilderFactory;
  @Value("${app.batch.chunkSize}")
  private int chunkSize;

  @Bean
  public Job importJob(JobExecutionListenerSupport listener, Step step1) {
    return jobBuilderFactory.get("processLogJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(step1)
        .end()
        .build();
  }

  @Bean
  public Step step1(ItemReader<Entry> reader, ItemProcessor<Entry, Event> processor,
      ItemWriter<Event> writer) {
    return stepBuilderFactory.get("step1")
        .<Entry, Event>chunk(chunkSize)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
  }

}