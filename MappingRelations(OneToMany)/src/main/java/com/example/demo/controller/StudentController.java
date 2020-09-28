package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Laptop;
import com.example.demo.model.Student;
import com.example.demo.repository.LaptopRepo;
import com.example.demo.repository.StudentRepo;

@RestController
public class StudentController {
	
	@Autowired
	StudentRepo srepo;
	@Autowired
	LaptopRepo lrepo;
	
	@GetMapping("/")
	public String getIndex() {
		return "Project is running.......";
	}
	
	// Add student (roll,name,marks)
	@PostMapping("/student/")
	public String addStudent( @RequestBody Student student )
	{
		srepo.save(student);
		return "Student added";
	}
	
	// Assigned laptop to a student
	@PostMapping("/student/{roll}/laptop/{lid}")
	public String addLaptops( @PathVariable("roll")  int roll,  @PathVariable("lid")  int lid ) {
		
		boolean stu_exits = srepo.existsById(roll);
		boolean lap_exits = lrepo.existsById(lid);
		if ( !stu_exits || !lap_exits ) {  // check the roll and lid is valid or not 
			throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Not Found" );
		}
	
		Optional<Student> student = srepo.findById(roll);  // entity record
		Optional<Laptop> laptop = lrepo.findById(lid);  // entity record
		
		Student stu = student.get(); // model instance 
		Laptop lap = laptop.get();   // model instance 
		
		if( lap.getStudent() != null ) {
			throw new ResponseStatusException( HttpStatus.CONFLICT, "Conflict" );
		}
		
		lap.setStudent(stu);
		lrepo.save(lap);  // Hibernate save this instace into entity laptop
		
		return "laptop added";
	}
	
	// Retrieve all the laptop of a student 
	@GetMapping("/student/{roll}/laptops")
	public List<Laptop> getLaptops( @PathVariable("roll")  int roll) {
		
		boolean exits = srepo.existsById(roll);
		if ( !exits ) {
			throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Not Found" );
		}
		Optional<Student> student = srepo.findById(roll);
		
		return student.get().getLaptop();
	}
	
	// Show All the students 
	@GetMapping("/students")
	public List<Student> getStudents(){
		return srepo.findAll();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}