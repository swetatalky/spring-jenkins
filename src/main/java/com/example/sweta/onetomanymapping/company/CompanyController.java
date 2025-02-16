package com.example.sweta.onetomanymapping.company;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
public class CompanyController {
	private final CompanyService companyService;
	 public CompanyController(CompanyService companyService)
	 {
		 this.companyService=companyService;
	 }
	
	@GetMapping("/{companyId}")
	public ResponseEntity<Company> getCompanyById(@PathVariable Long companyId)
	{
		Company c=companyService.getCompanyById(companyId);
		if(c==null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(c,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Company>> getAllCompany()
	{
		List<Company> c=companyService.getAllCompanies();
		if(c==null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(c,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> addCompany(@RequestBody Company c)
	{
		companyService.createCompany(c);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{companyId}")
	public ResponseEntity<String> deleteCompanyById(@PathVariable Long companyId)
	{
		if(companyService.deleteCompanyById(companyId))
	return new ResponseEntity<>("Successfully delted company",HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>("Could not delete company",HttpStatus.OK);
	}
	
	@PutMapping("/{companyId}")
	public ResponseEntity<String> updateCompanyById(@RequestBody Company uCom,@PathVariable Long companyId)
	{if(companyService.updateCompany(uCom,companyId))
		return new ResponseEntity<>("Successfully updated company",HttpStatus.NOT_FOUND);
	else
		return new ResponseEntity<>("Could not update company",HttpStatus.OK);
	}

}
