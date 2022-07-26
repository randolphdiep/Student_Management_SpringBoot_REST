package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.ListStudent;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepo;

	public void save(Student student) {
		studentRepo.save(student);
	}

	public Student findById(int id) {
		Student student = studentRepo.findById(id).get();
		return student;
	}

	public List<Student> findAll() {
		return (List<Student>) studentRepo.findAll();
	}

	public void deleteById(int studentId) {
		studentRepo.deleteById(studentId);
	}

//	public Collection<ListStudent> search(String code, String name, LocalDate birthday) {
//		List<ListStudent> list = this.studentRepo.searchListStudent(code, name, birthday);
//		list.forEach(item -> list.add(new ListStudent(
//				item.getStudentId(), 
//				item.getStudentCode(), 
//				item.getStudentName(),
//				item.getDateOfBirth(), 
//				item.getAddress(), 
//				item.getAverageScore())));
//		return list;
//	}
}
