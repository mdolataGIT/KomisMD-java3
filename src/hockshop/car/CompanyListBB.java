package hockshop.car;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;


import jsf.hockshop.dao.CompanyDAO;
import jsf.hockshop.entities.Company;

@Named
@RequestScoped
public class CompanyListBB {
	private static final String PAGE_COMPANY_EDIT = "/app/companyEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_COMPANY_NEXT = "/public/carList";

	private String name;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	CompanyDAO companyDAO;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Company> getFullList(){
		return companyDAO.getFullList();
	}

	public List<Company> getList(){
		List<Company> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (name != null && name.length() > 0){
			searchParams.put("name", name);
		}
		
		list = companyDAO.getList(searchParams);
		
		return list;
	}

	public String newCompany(){
		Company company = new Company();
			
		flash.put("company", company);
		
		return PAGE_COMPANY_EDIT;
	}

	public String editCompany(Company company){

		flash.put("company", company);
		
		return PAGE_COMPANY_EDIT;
	}

	public String deleteCompany(Company company){
		companyDAO.remove(company);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	public String nextCompany(Company company){
		
		return PAGE_COMPANY_NEXT;
	}
	


}
