package org.jsp.library.dao;

import org.jsp.library.dto.Librarian;
import org.jsp.library.repository.LibrarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LibrarianDao {
	
	@Autowired
	private LibrarianRepository librarianRepository;

	public Librarian fetchByPhone(long mobile) {
		return librarianRepository.findByMobile(mobile);
	}

	public Librarian fetchByEmail(String email) {
		return librarianRepository.findByEmail(email);
	}

	public Librarian librarianDataSave(Librarian librarian) {
		return librarianRepository.save(librarian);
		
	}

	public Librarian fetchById(int id) {
		return librarianRepository.findById(id).orElse(null);
	}

}
