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

import jsf.hockshop.dao.CoustomerDAO;
import jsf.hockshop.dao.SpecelemDAO;
import jsf.hockshop.entities.Coustomer;
import jsf.hockshop.entities.Specelem;

@Named
@ViewScoped
public class SpecelemEditBB implements Serializable{
	private static final String PAGE_SPECELEM_LIST ="/public/specelemList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME =null;
	
	private Specelem specelem = new Specelem ();
	private Specelem loaded = null;

	@EJB
	SpecelemDAO specelemDAO;
	
	@Inject
	FacesContext context;
	
	@Inject
	Flash flash;
	
	public Specelem getSpecelem() {
		return specelem;
	}
	
	public void onLoad() throws IOException{
		loaded = (Specelem) flash.get("specelem");
		
		if(loaded!=null) {
			specelem = loaded;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "B³êdne u¿ycie systemu", null));
		}
	}
	
	public String saveData() {
		if(loaded ==null) {
			return PAGE_STAY_AT_THE_SAME;
		}
		
		try {
			if(specelem.getIdSpecelem()==null) {
				specelemDAO.create(specelem);
			}else {
				specelemDAO.merge(specelem);
			}
		}catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wyst¹pi³ b³¹d zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_SPECELEM_LIST;
	}
	
	
}
