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
import jsf.hockshop.dao.SpecelemDAO;
import jsf.hockshop.entities.Car;
import jsf.hockshop.entities.Company;
import jsf.hockshop.entities.Coustomer;
import jsf.hockshop.entities.Specelem;

@Named
@RequestScoped
public class SpecelemListBB {
	private static final String PAGE_SPECELEM_EDIT = "/app/specelemEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String specName;
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	SpecelemDAO specelemDAO;
	
	@EJB
	CarDAO carDAO;
	
	public String getSpecName() {
		return specName;
	}
	
	public void setSpecName(String specName) {
		this.specName=specName;
	}
	
	public List<Specelem>getFullList(){
		return specelemDAO.getFullList();
	}
	
	
	Map<String,Object> searchParams = new HashMap<String,Object>();
	public List<Specelem> getList(){
		List<Specelem> list= null;
		
		
		if (specName!=null && specName.length()>0) {
			searchParams.put("specName",specName);
		}
		
		searchParams.put("carId", car.getIdCar());
		
		list = specelemDAO.getList(searchParams);
		return list;
	}
	
	public String newSpecelem() {
		
		Integer value = (Integer) searchParams.get("carId");
		
		Specelem specelem = new Specelem();
		Car ca = carDAO.find(value.intValue());
		specelem.setCar(ca);
		
		
		flash.put("specelem",specelem);
		return PAGE_SPECELEM_EDIT;
		
	}
	
	public String editSpecelem(Specelem specelem) {
		flash.put("specelem", specelem);
		return PAGE_SPECELEM_EDIT;
	}
	
	public String deleteSpecelem(Specelem specelem) {
		specelemDAO.remove(specelem);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	
	private static final String PAGE_CAR_LIST = "/public/carList?faces-redirect=true";
	private Car car = new Car();
	private Car loaded = null;
	private Specelem specelem = new Specelem();
	
	public Specelem getSpecelem() {
		return specelem;
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
