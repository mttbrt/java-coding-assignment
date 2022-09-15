package javacodingassignment.repository;

import javacodingassignment.model.Event;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

  @Modifying
  @Transactional
  @Query(value = "TRUNCATE SCHEMA public AND COMMIT", nativeQuery = true)
  void truncateSchemaPublicAndCommit();

}
