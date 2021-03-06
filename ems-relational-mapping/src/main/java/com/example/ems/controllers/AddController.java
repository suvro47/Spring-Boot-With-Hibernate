package com.example.ems.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.ems.dao.AddressRepository;
import com.example.ems.dao.EmployeeRepository;
import com.example.ems.models.Address;
import com.example.ems.models.Employee;

@RestController
public class AddController {
	
	@Autowired
	AddressRepository arepo;
	
	@Autowired
	EmployeeRepository erepo;
	
	@GetMapping("/addresses")
	public List<Address> getAddress(){
		List<Address> add = arepo.findAll();
		if( add.size() == 0 ) throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Not Found" );
		return add;
	}
	
	@GetMapping("/address/sector/{sector_no}")
	public List<Address> getAddressBasedOnSectorNo( @PathVariable int sector_no){
		
		List<Address> add = arepo.findAddressSectorNo(sector_no);
		if( add.size() == 0 ) throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Not Found" );
		return add;
	}
	
	// Add Address info with specified Employee
	@PostMapping("/employee/{eid}/address/add")
	public  ResponseEntity<Object> addEmployeeAddress( @PathVariable long eid, @RequestBody Address address ){
		
		Optional<Employee> emp = erepo.findById(eid);
		
		if( ! emp.isPresent() ) {
			return ResponseEntity.unprocessableEntity().body("Failed to add address with the Employee specified.");
		}
		
		Employee employee = emp.get();
		employee.setAddress(address);
		address.setEmployee(employee);
		arepo.save(address);
        erepo.save(employee);	
		return ResponseEntity.ok().body("Employee Address added successfully.");
	}
	
	@PutMapping("/employee/{eid}/address/{aid}/update")
	public  ResponseEntity<Object> updateEmployeeAddres( @PathVariable long eid, @PathVariable long aid ){
		
		Optional<Employee> OpEmp = erepo.findById(eid); 
		Optional<Address> OpAdd = arepo.findById(aid);
		
		Address add = OpAdd.get();  // retrieve instance from record
		Employee emp = OpEmp.get(); 
		
		emp.setAddress(add);
		add.setEmployee(emp);
		erepo.save(emp);
		
		return ResponseEntity.ok().body("Employee address updated successfully.");
	}
	
	
	
	
	

}
