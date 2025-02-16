package com.example.sweta.onetomanymapping.job;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sweta.onetomanymapping.company.Company;
import com.example.sweta.onetomanymapping.company.CompanyService;
import com.example.sweta.onetomanymapping.exception.InvalidInputException;
import com.example.sweta.onetomanymapping.exception.JobNotFoundException;
@Service
public class JobServiceImpl implements JobService {
	private JobRepository jobRepository;
	private CompanyService companyService;
	
	public JobServiceImpl(JobRepository jobRepository)
	{
		this.jobRepository=jobRepository;
	}

	@Override
	public List<Job> findAll() {
		// TODO Auto-generated method stub
		return jobRepository.findAll();
	}

	@Override
	public void createJob(Job job) {
		// TODO Auto-generated method stub
		if(Integer.parseInt(job.getMinSalary())<0)
			throw new InvalidInputException("Salary cannot be less than 0");
		 jobRepository.save(job);
	}

/*	@Override
	public Job getJobById(Long id) {
		// TODO Auto-generated method stub
		return jobRepository.findById(id).orElse(null);
	}

	@Override
	public boolean deleteJobById(Long id) {
		// TODO Auto-generated method stub
		if(jobRepository.existsById(id))
		{
			Job j=jobRepository.findById(id).get();
			long companyId=j.getCompany().getId();
			Company c=j.getCompany();
			c.getJobs().remove(j);
			companyService.updateCompany(c, companyId);
			j.setCompany(null);
			jobRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateJob(Long id, Job updatedJob) {
		// TODO Auto-generated method stub
		if(jobRepository.existsById(id))
		{
			Job j=jobRepository.findById(id).get();
			j.setCompany(updatedJob.getCompany());
			j.setDescription(updatedJob.getDescription());
			j.setId(id);
			j.setLocation(updatedJob.getLocation());
			j.setMaxSalary(updatedJob.getMaxSalary());
			j.setMinSalary(updatedJob.getMinSalary());
			j.setTitle(updatedJob.getTitle());
			jobRepository.save(j);
			return true;
		}
		return false;
	}
	*/
	
	@Override
	public Job getJobById(Long id) {
	    return jobRepository.findById(id)
	            .orElseThrow(() -> new JobNotFoundException("Job with ID " + id + " not found."));
	}

	@Override
	public boolean deleteJobById(Long id) {//job ho skta hai bina company k but review ka existence company k hi sath hai
	    if (!jobRepository.existsById(id)) {
	        throw new JobNotFoundException("Job with ID " + id + " does not exist.");
	    }

	    Job job = jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job with ID " + id + " not found."));
	    Company company = job.getCompany();
	    if (company != null) {
	        company.getJobs().remove(job);
	        companyService.updateCompany(company, company.getId());
	    }
	    jobRepository.deleteById(id);
	    return true;
	}

	@Override
	public boolean updateJob(Long id, Job updatedJob) {
	    if (!jobRepository.existsById(id)) {
	        throw new JobNotFoundException("Job with ID " + id + " does not exist.");
	    }

	    Job job = jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job with ID " + id + " not found."));
	    job.setTitle(updatedJob.getTitle());
	    job.setDescription(updatedJob.getDescription());
	    job.setMinSalary(updatedJob.getMinSalary());
	    job.setMaxSalary(updatedJob.getMaxSalary());
	    job.setLocation(updatedJob.getLocation());
	    job.setCompany(updatedJob.getCompany());

	    jobRepository.save(job);
	    return true;
	}


}
