package org.jsp.library.dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String author;
	private int quantity;
	private double Price;
	private boolean status;
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] picture;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<BookRecord> records;

}
