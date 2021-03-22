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

import jsf.hockshop.dao.CarDAO;
import jsf.hockshop.dao.CoustomerDAO;
import jsf.hockshop.entities.Car;
import jsf.hockshop.entities.Company;
import jsf.hockshop.entities.Coustomer;

@Named
@RequestScoped
public class CoustomerListBB {
	private static final String PAGE_COUSTOMER_EDIT = "/app/coustomerEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String coustomerName;
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	CoustomerDAO coustomerDAO;
	
	@EJB
	CarDAO carDAO;
	
	public String getCoustomerName() {
		return coustomerName;
	}
	
	public void setCoustomerName(String coustomerName) {
		this.coustomerName=coustomerName;
	}
	
	public List<Coustomer>getFullList(){
		return coustomerDAO.getFullList();
	}
	
	
	Map<String,Object> searchParams = new HashMap<String,Object>();
	public List<Coustomer> getList(){
		List<Coustomer> list= null;
		
		
		if (coustomerName!=null && coustomerName.length()>0) {
			searchParams.put("coustomerName",coustomerName);
		}
		
		searchParams.put("carId", car.getIdCar());
		
		list = coustomerDAO.getList(searchParams);
		return list;
	}
	
	public String newCoustomer() {
		
		Integer value = (Integer) searchParams.get("carId");
		
		Coustomer coustomer = new Coustomer();
		Car ca = carDAO.find(value.intValue());
		coustomer.setCar(ca);
		
		
		flash.put("coustomer",coustomer);
		return PAGE_COUSTOMER_EDIT;
		
	}
	
	public String editCoustomer(Coustomer coustomer) {
		flash.put("coustomer", coustomer);
		return PAGE_COUSTOMER_EDIT;
	}
	
	public String deleteCoustomer(Coustomer coustomer) {
		coustomerDAO.remove(coustomer);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	
	private static final String PAGE_CAR_LIST = "/public/carList?faces-redirect=true";
	private Car car = new Car();
	private Car loaded = null;
	private Coustomer coustomer = new Coustomer();
	
	public Coustomer getCoustomer() {
		return coustomer;
	}


	@Inject
	FacesContext context;
	


	public Car getCar() {
		return car;
	}

	public void onLoad() throws IOException {
		if (!context.isPostback()) {
			if (!context.isValidationFailed() && car.getIdCar() != null) {
				loaded = carDAO.find(car.getIdCar());
			}
			if (loaded != null) {
				car = loaded;
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "B³¹d", null));
			}
		}

	}
	

	public String saveData() {
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (car.getIdCar() == null) {
				carDAO.create(car);
			} else {
				carDAO.merge(car);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wyst¹pi³ b³¹d zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_CAR_LIST;
	}
	
	
	
}
