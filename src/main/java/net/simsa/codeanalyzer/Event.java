package net.simsa.codeanalyzer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * JPA-Hibernate entity tutorial
 * @author jenny
 *
 */
@Entity
@Table(name = "Event")
public class Event {

    Long id;
    String title;
    Date date;

    public Event() {
	super();
    }

    public Event(String title, Date date) {
	this.title = title;
	this.date = date;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getId() {
	return id;
    }

    public String getTitle() {
	return title;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    public Date getDate() {
	return date;
    }
}