package com.example.sweta.onetomanymapping.company;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class CompanyServiceImpl implements CompanyService {
	private final CompanyRepository companyRepository;
	public CompanyServiceImpl(CompanyRepository companyRepository) {
		
		this.companyRepository = companyRepository;
	}

	
	

	@Override
	public List<Company> getAllCompanies() {
		// TODO Auto-generated method stub
		return companyRepository.findAll();
	}

	@Override
	public boolean updateCompany(Company updatecompanyData, Long id) {
		// TODO Auto-generated method stub
		if(companyRepository.existsById(id)) {
		Company c=	companyRepository.findById(id).get();
		c.setDescription(updatecompanyData.getDescription());
		c.setName(updatecompanyData.getName());
		c.setJobs(updatecompanyData.getJobs());
		companyRepository.save(c);
			return true;
			}
			return false;
	}

	@Override
	public void createCompany(Company company) {
		// TODO Auto-generated method stub
		companyRepository.save(company);
	}

	@Override
	public boolean deleteCompanyById(Long id) {
		// TODO Auto-generated method stub
		if(companyRepository.existsById(id)) {
		companyRepository.deleteById(id);
		return true;
		}
		return false;
	}

	@Override
	public Company getCompanyById(Long id) {
		// TODO Auto-generated method stub
		return companyRepository.findById(id).orElse(null);
	}

}
