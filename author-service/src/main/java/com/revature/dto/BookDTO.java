package com.revature.dto;

import java.time.LocalDate;

/*
 * BASE - Basic Availability, Soft State, Eventual Consistency
 * 
 * DTO - Data Transfer Object - Some POJO that defines the structure of
 * transferred data, which is not directly an entity itself.
 */
public class BookDTO {
	private int id;
	private String isbn;
	private Integer totalPages;
	private String title;
	private LocalDate releaseDate;
	private Integer authorId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorId == null) ? 0 : authorId.hashCode());
		result = prime * result + id;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((totalPages == null) ? 0 : totalPages.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookDTO other = (BookDTO) obj;
		if (authorId == null) {
			if (other.authorId != null)
				return false;
		} else if (!authorId.equals(other.authorId))
			return false;
		if (id != other.id)
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (totalPages == null) {
			if (other.totalPages != null)
				return false;
		} else if (!totalPages.equals(other.totalPages))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BookDTO [id=" + id + ", isbn=" + isbn + ", totalPages=" + totalPages + ", title=" + title
				+ ", releaseDate=" + releaseDate + ", authorId=" + authorId + "]";
	}

	public BookDTO(int id, String isbn, Integer totalPages, String title, LocalDate releaseDate, Integer authorId) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.totalPages = totalPages;
		this.title = title;
		this.releaseDate = releaseDate;
		this.authorId = authorId;
	}

	public BookDTO() {
		super();
	}
}
