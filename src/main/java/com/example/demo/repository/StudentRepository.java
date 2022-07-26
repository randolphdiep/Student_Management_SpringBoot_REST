package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.ListStudent;
import com.example.demo.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {
		
//	@Query("SELECT new com.example.demo.model.ListStudent("
//			+ "s1.studentId, "
//			+ "s1.studentCode, "
//			+ "s1.studentName, "
//			+ "s2.dateOfBirth, "
//			+ "s2.address, "
//			+ "s2.averageScore) "	
//			+ "FROM Student s1, StudentInfo s2 "
//			+ "WHERE s1.studentId = s2.studentIdentity.studentId AND s1.studentCode LIKE %?1% AND s1.studentName LIKE %?2% AND s2.dateOfBirth = ?3"
//			)
//	public List<ListStudent> searchListStudent(String studentCode, String studentName, LocalDate dateOfBirth);
	
	@Query("SELECT new com.example.demo.model.ListStudent("
			+ "s1.studentId, "
			+ "s1.studentCode, "
			+ "s1.studentName, "
			+ "s2.dateOfBirth, "
			+ "s2.address, "
			+ "s2.averageScore) "	
			+ "FROM Student s1, StudentInfo s2 "
			+ "WHERE s1.studentId = s2.studentIdentity.studentId AND s1.studentCode LIKE %:code% AND s1.studentName LIKE %:name% AND (:date is null or s2.dateOfBirth = :date)"
			)
	public List<ListStudent> searchListStudent(@Param("code") String studentCode, @Param("name") String studentName, @Param("date")LocalDate dateOfBirth);
	
//	@Query(value = "SELECT "
//	+ "s1.student_id AS studentId, "
//	+ "s1.student_code AS studentCode, "
//	+ "s1.student_name AS studentName, "
//	+ "s2.date_of_birth AS dateOfBirth, "
//	+ "s2.address AS address, "
//	+ "s2.average_score AS averageScore "	
//	+ "FROM student s1, student_info s2 "
//	+ "WHERE s1.student_id = s2.student_id AND s1.student_id = s2.info_id AND s1.student_code LIKE %:code% AND s1.student_name LIKE %:name% AND (:date is null or s2.date_of_birth = :date)"
//	,nativeQuery = true)
//	public List<ListStudent> searchListStudent(@Param("code") String studentCode, @Param("name") String studentName, @Param("date")LocalDate dateOfBirth);
	
	@Query("SELECT new com.example.demo.model.ListStudent("
			+ "s1.studentId, "
			+ "s1.studentCode, "
			+ "s1.studentName, "
			+ "s2.dateOfBirth, "
			+ "s2.address, "
			+ "s2.averageScore) "	
			+ "FROM Student s1, StudentInfo s2 "
			+ "WHERE s2.studentIdentity.studentId = s1.studentId"
			)
	public List<ListStudent> findAllListStudent();
	
	@Query("SELECT new com.example.demo.model.ListStudent("
			+ "s1.studentId, "
			+ "s1.studentCode, "
			+ "s1.studentName, "
			+ "s2.dateOfBirth, "
			+ "s2.address, "
			+ "s2.averageScore) "	
			+ "FROM Student s1, StudentInfo s2 "
			+ "WHERE s2.studentIdentity.studentId = s1.studentId AND s1.studentId = ?1"
			)
	public ListStudent findListStudentById(int studentId);
	
	@Modifying
	@Transactional
	@Query(
		value = "insert into student (student_code, student_name) values (?1, ?2)"
		,nativeQuery = true)
	public void addStudent(String code, String name);
	
	@Modifying
	@Transactional
	@Query(
	  value = "insert into student_info values ((select student_id from student order by student_id desc limit 1), (select student_id from student order by student_id desc limit 1), ?1, ?2, ?3)",
	  nativeQuery = true)
		public void addStudentInfo(String address, double averageScore, LocalDate dateOfBirth);
}
