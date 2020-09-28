package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Student {
	
	@Id
	private int roll;
	private String name;
	private int marks;
	
	@OneToMany(mappedBy="student")  // no need to create student_laptop table
	private List<Laptop> laptop = new ArrayList<Laptop>();
	
	public Student() {
		
	}
	public Student(int roll, String name, int marks, List<Laptop> laptop) {
		super();
		this.roll = roll;
		this.name = name;
		this.marks = marks;
		this.laptop = laptop;
	}

	public int getRoll() {
		return roll;
	}

	public void setRoll(int roll) {
		this.roll = roll;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	public List<Laptop> getLaptop() {
		return laptop;
	}

	public void setLaptop(List<Laptop> laptop) {
		this.laptop = laptop;
	}	

}