package javacodingassignment.repository;

import javacodingassignment.model.Entry;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EntryRepository extends CrudRepository<Entry, String> {

  @Modifying
  @Transactional
  @Query(value = "TRUNCATE SCHEMA public AND COMMIT", nativeQuery = true)
  void truncateSchemaPublicAndCommit();

}
