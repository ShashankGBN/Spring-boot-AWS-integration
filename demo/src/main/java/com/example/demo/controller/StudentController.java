package com.example.demo.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Student;

@RestController
@RequestMapping("/student")
public class StudentController {

	List<Student> students = new ArrayList<>();
	
	@PostMapping
	public Student save(@RequestBody Student student) {
		students.add(student);
		return student;
	}
	
	@GetMapping
	public List<Student> getStudents() {
		return students;
	}
	
}
