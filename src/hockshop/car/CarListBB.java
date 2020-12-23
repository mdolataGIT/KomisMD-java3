package hockshop.car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpSession;


import jsf.hockshop.dao.CarDAO;
import jsf.hockshop.entities.Car;

@Named
@RequestScoped
public class CarListBB {
	private static final String PAGE_CAR_EDIT = "carEdit?faces-redirect=true";
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
}
