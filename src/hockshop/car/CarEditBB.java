package hockshop.car;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import jsf.hockshop.dao.CarDAO;
import jsf.hockshop.entities.Car;

@Named
@ViewScoped
public class CarEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_CAR_LIST = "/public/carList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	

	private Car car = new Car();
	private Car loaded = null;

	@EJB
	CarDAO carDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Car getCar() {
		return car;
	}

	public void onLoad() throws IOException {

		loaded = (Car) flash.get("car");


		if (loaded != null) {
			car = loaded;

		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "B³êdne u¿ycie systemu", null));
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
		return PAGE_STAY_AT_THE_SAME;
	}
}
