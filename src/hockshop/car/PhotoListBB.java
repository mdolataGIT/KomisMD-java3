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
import jsf.hockshop.dao.PhotoDAO;
import jsf.hockshop.entities.Car;
import jsf.hockshop.entities.Photo;

@Named
@RequestScoped
public class PhotoListBB {
	private static final String PAGE_PHOTO_EDIT = "/app/photoEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null; 

	private String url;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	PhotoDAO photoDAO;
	
	@EJB
	CarDAO carDAO;
	
		
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Photo> getFullList(){
		return photoDAO.getFullList();
	}

	
	Map<String,Object> searchParams = new HashMap<String, Object>();
	public List<Photo> getList(){
		List<Photo> list = null;
		
		//Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (url!= null && url.length() > 0){
			searchParams.put("url", url);
		}
		
		searchParams.put("carId", car.getIdCar());
		
		list = photoDAO.getList(searchParams);
		
		return list;
	}

	public String newPhoto(){
		Integer value = (Integer) searchParams.get("carId");
		
		Photo photo = new Photo();
		
		Car ca = carDAO.find(value.intValue());
		photo.setCar(ca);
			
		flash.put("photo", photo);
		
		return PAGE_PHOTO_EDIT;
	}

	public String editPhoto(Photo photo){

		flash.put("photo", photo);
		
		return PAGE_PHOTO_EDIT;
	}

	public String deletePhoto(Photo photo){
		photoDAO.remove(photo);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	private static final String PAGE_CAR_LIST = "/public/carList?faces-redirect=true";
	private Car car = new Car();
	private Car loaded =null;
	private Photo photo = new Photo();
	
	public Photo getPhoto() {
		return photo;
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
