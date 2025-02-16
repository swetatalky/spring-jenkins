package com.example.sweta.onetomanymapping.job;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.sweta.onetomanymapping.company.Company;
import com.example.sweta.onetomanymapping.company.CompanyService;
import com.example.sweta.onetomanymapping.exception.InvalidInputException;
import com.example.sweta.onetomanymapping.exception.JobNotFoundException;

@ExtendWith(MockitoExtension.class)
public class JobServiceImplTest {

    @Mock  // Mocking dependencies
    private JobRepository jobRepository;

    @Mock
    private CompanyService companyService;

    @InjectMocks  // Creates instance of JobServiceImpl and injects mocks into it
    private JobServiceImpl jobService;
    private Job job;
    @BeforeEach
    void setUp() {
        job = new Job(1L, "Software Engineer", "Develop software", "50000", "80000", "NYC");
    }
    
    @Test
    public void testFindAllJobs() {
        List<Job> jobs = Arrays.asList(job);
        when(jobRepository.findAll()).thenReturn(jobs);

        List<Job> result = jobService.findAll();

        assertEquals(1, result.size());
        assertEquals("Software Engineer", result.get(0).getTitle());
        verify(jobRepository, times(1)).findAll();
    }
    
    @Test
    public void testCreateJob_Success() {
    	 when(jobRepository.save(Mockito.any(Job.class))).thenReturn(job); //  Fix: Use when(...).thenReturn(...)

    	    assertDoesNotThrow(() -> jobService.createJob(job));

    	    verify(jobRepository, times(1)).save(job);
    }
    
    @Test
    public void testGetJobById_Success() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        Job result = jobService.getJobById(1L);

        assertNotNull(result);
        assertEquals("Software Engineer", result.getTitle());
        verify(jobRepository, times(1)).findById(1L);
    }

    
    @Test
    public void testGetJobById_NotFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(JobNotFoundException.class, () -> jobService.getJobById(1L));

        assertEquals("Job with ID 1 not found.", exception.getMessage());
        verify(jobRepository, times(1)).findById(1L);
    }
    
    @Test
    public void testDeleteJob_Success() {
        when(jobRepository.existsById(1L)).thenReturn(true);
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        doNothing().when(jobRepository).deleteById(1L);

        boolean result = jobService.deleteJobById(1L);

        assertTrue(result);
        verify(jobRepository, times(1)).deleteById(1L);
    }
    
    @Test
    public void testDeleteJob_NotFound() {
        when(jobRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(JobNotFoundException.class, () -> jobService.deleteJobById(1L));

        assertEquals("Job with ID 1 does not exist.", exception.getMessage());
        verify(jobRepository, never()).deleteById(anyLong());
    }
    
    //@Test
    public void testDeleteJob_CompanyExists() {
    	  Job jobWithCompany = new Job(1L, "Software Engineer", "Develop software", "50000", "80000", "NYC");
    	    Company company = new Company();
    	    company.setDescription("tech company");
    	    company.setId(1L);
    	    company.setName("Tech Ltd");
    	    jobWithCompany.setCompany(company);

    	    when(jobRepository.existsById(1L)).thenReturn(true);
    	    when(jobRepository.findById(1L)).thenReturn(Optional.of(jobWithCompany));
    	    when(companyService.updateCompany(any(Company.class), eq(1L))).thenReturn(true); // ✅ Fix: Use when(...).thenReturn(true)
    	    doNothing().when(jobRepository).deleteById(1L);

    	    boolean result = jobService.deleteJobById(1L);

    	    assertTrue(result);
    	    verify(companyService, times(1)).updateCompany(any(Company.class), eq(1L));
    	    verify(jobRepository, times(1)).deleteById(1L);
    }

    
    @Test
    public void testUpdateJob_NotFound() {
        when(jobRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(JobNotFoundException.class, () -> jobService.updateJob(1L, job));

        assertEquals("Job with ID 1 does not exist.", exception.getMessage());
        verify(jobRepository, never()).save(any(Job.class));
    }
    
    //@Test
    public void testUpdateJob_InvalidSalary() {
        Job invalidJob = new Job(1L, "Engineer", "Test", "-5000", "10000", "NYC");

        when(jobRepository.existsById(1L)).thenReturn(true);
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        Exception exception = assertThrows(InvalidInputException.class, () -> jobService.updateJob(1L, invalidJob));

        assertEquals("Salary cannot be less than 0", exception.getMessage());
        verify(jobRepository, never()).save(any(Job.class));
    }





    
}