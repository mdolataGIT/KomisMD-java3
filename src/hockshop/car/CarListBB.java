package hockshop.car;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import jsf.hockshop.dao.CarDAO;
import jsf.hockshop.dao.CompanyDAO;
import jsf.hockshop.entities.Car;
import jsf.hockshop.entities.Company;

@Named
@RequestScoped

public class CarListBB {
	private static final String PAGE_CAR_EDIT = "/app/carEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String brand;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	CarDAO carDAO;
	
		
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public List<Car> getFullList(){
		return carDAO.getFullList();
	}

	public List<Car> getList(){
		List<Car> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (brand != null && brand.length() > 0){
			searchParams.put("brand", brand);
		}
		
		searchParams.put("companyId",company.getIdCompany());
		
		
		list = carDAO.getList(searchParams);
		
		return list;
	}

	public String newCar(){
		Car car = new Car();
			
		flash.put("car", car);
		
		return PAGE_CAR_EDIT;
	}

	public String editCar(Car car){

		flash.put("car", car);
		
		return PAGE_CAR_EDIT;
	}

	public String deleteCar(Car car){
		carDAO.remove(car);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	
	
	
	
	private static final String PAGE_COMPANY_LIST = "/public/companyList?faces-redirect=true";
	private Company company = new Company();
	private Company loaded = null;
	private Car car = new Car();
	
	public Car getCar() {
		return car;
	}

	@EJB
	CompanyDAO companyDAO;

	@Inject
	FacesContext context;
	


	public Company getCompany() {
		return company;
	}

	public void onLoad() throws IOException {
		if (!context.isPostback()) {
			if (!context.isValidationFailed() && company.getIdCompany() != null) {
				loaded = companyDAO.find(company.getIdCompany());
			}
			if (loaded != null) {
				company = loaded;
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "B³¹d", null));
				// if (!context.isPostback()) { // possible redirect
				// context.getExternalContext().redirect("personList.xhtml");
				// context.responseComplete();
				// }
			}
		}

	}
	
	
	

	public String saveData() {
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (company.getIdCompany() == null) {
				companyDAO.create(company);
			} else {
				companyDAO.merge(company);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wyst¹pi³ b³¹d zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_COMPANY_LIST;
	}
	
	
	
	
	
}
