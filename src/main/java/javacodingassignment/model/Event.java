package javacodingassignment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String eventId;
  private long duration;
  private boolean alert;
  private String type;
  private String host;

  public Event(String eventId, long duration) {
    this.eventId = eventId;
    this.duration = duration;
    this.alert = duration > 4;
  }

  public Event() {
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setHost(String host) {
    this.host = host;
  }

  @Override
  public String toString() {
    return "Event{" +
        "id=" + id +
        ", eventId='" + eventId + '\'' +
        ", duration=" + duration +
        ", alert=" + alert +
        ", type='" + type + '\'' +
        ", host='" + host + '\'' +
        '}';
  }
}
