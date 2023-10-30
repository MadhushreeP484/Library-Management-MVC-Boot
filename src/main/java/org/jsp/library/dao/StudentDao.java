package org.jsp.library.dao;

import org.jsp.library.dto.Student;
import org.jsp.library.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao {

	@Autowired
    private StudentRepository studentRepository;

    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public Student findByMobile(long mobile) {
        return studentRepository.findByMobile(mobile);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

	public Student fetchById(int id) {
		return studentRepository.findById(id).orElse(null);
	}
}
