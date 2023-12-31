package org.jsp.library.repository;

import org.jsp.library.dto.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianRepository extends JpaRepository<Librarian, Integer>{

	Librarian findByMobile(long mobile);

	Librarian findByEmail(String email);

}
