package javacodingassignment.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "entry")
public class Entry {

  @Id
  private String id;
  private EntryType state;
  private long timestamp;
  private String type;
  private String host;

  public Entry() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public EntryType getState() {
    return state;
  }

  public void setState(EntryType state) {
    this.state = state;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  @Override
  public String toString() {
    return "Entry{" +
        "id='" + id + '\'' +
        ", state=" + state +
        ", timestamp=" + timestamp +
        ", type='" + type + '\'' +
        ", host='" + host + '\'' +
        '}';
  }
}
