package com.example.demo.entities;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String description;
	private String image;
	private String tag;
	private Date datePub;
	private Long userId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Date getDatePub() {
		return datePub;
	}
	public void setDatePub(Date datePub) {
		this.datePub = datePub;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Post(int id, String title, String description, String image, String tag, Date datePub, Long userId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.image = image;
		this.tag = tag;
		this.datePub = datePub;
		this.userId = userId;
	}

	public Post() {
		super();
	}

	
}
