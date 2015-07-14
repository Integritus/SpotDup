package com.santitosvk.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.santitosvk.jpo.Employee;

@Service
public class EmployeeService {
	
	@PersistenceContext
	private EntityManager em;
		
	
	@Transactional
	public List<Employee> searchEmployee(String searchStr) {
		Query result = em.createQuery("select e from Employee e  where upper(e.name) like upper(:keyword)");
		result.setParameter("keyword", "%" + searchStr + "%");
		return result.getResultList();
	}
			
	@Transactional
	public void createEmployee(Employee employee) {
		em.persist(employee);
	}
	
	@Transactional
	public void delete(Integer id) {
		Employee result = findById(id);
		em.remove(result);
	}

	@Transactional
	public Employee findById(Integer id) {
		Employee result = em.find(Employee.class, id);
		return result;
	}

	@Transactional
	public void update(Employee employee) {
		em.merge(employee);		
	}
	
	

}
