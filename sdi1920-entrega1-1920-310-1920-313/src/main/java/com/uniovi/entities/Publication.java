package com.uniovi.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Publication {

	@Id
	@GeneratedValue
	private long id;
	private String title;
	private String text;
	private Date date;	

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User poster;
	
	public Publication(long id, String title, String text, Date date, User poster) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.date = date;
		this.poster = poster;
	}
	
	public Publication() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getPoster() {
		return poster;
	}

	public void setPoster(User poster) {
		this.poster = poster;
	}

	
	
}
