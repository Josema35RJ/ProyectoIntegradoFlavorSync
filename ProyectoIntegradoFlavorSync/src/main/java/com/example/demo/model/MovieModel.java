package com.example.demo.model;

import java.time.LocalDate;

public class MovieModel {

	private Integer id;

	private String title;

	private String description;

	private boolean isShort;

	private float duration;

	private String video;

	private int likesCount;

	private LocalDate createDate;

	private LocalDate updateDate;

	public MovieModel() {
		super();
	}

	public MovieModel(String title, String description, float duration, String video, int likesCount) {
		super();
		this.title = title;
		this.description = description;
		this.isShort = MovieIsShort(duration);
		this.video = video;
		this.createDate = LocalDate.now();
	}

	public static boolean MovieIsShort(float duration) {
		if (duration <= 1) //Si dura menos de 1 min, es short, sino video normal
			return true;
		return false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public boolean isShort() {
		return isShort;
	}

	public void setShort(boolean isShort) {
		this.isShort = isShort;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "movieModel [id=" + id + ", title=" + title + ", description=" + description + ", isShort=" + isShort
				+ ", duration=" + duration + ", video=" + video + ", likesCount=" + likesCount + ", createDate="
				+ createDate + ", updateDate=" + updateDate + "]";
	}
}
