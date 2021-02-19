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
import jsf.hockshop.entities.Coustomer;

@Named
@ViewScoped
public class CoustomerEditBB implements Serializable{
	private static final String PAGE_COUSTOMER_LIST ="coustomerList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME =null;
	
	private Coustomer coustomer = new Coustomer ();
	private Coustomer loaded = null;

	@EJB
	CoustomerDAO coustomerDAO;
	
	@Inject
	FacesContext context;
	
	@Inject
	Flash flash;
	
	public Coustomer getCoustomer() {
		return coustomer;
	}
	
	public void onLoad() throws IOException{
		loaded = (Coustomer) flash.get("coustomer");
		
		if(loaded!=null) {
			coustomer = loaded;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "B³êdne u¿ycie systemu", null));
		}
	}
	
	public String saveData() {
		if(loaded ==null) {
			return PAGE_STAY_AT_THE_SAME;
		}
		
		try {
			if(coustomer.getIdCoustomer()==null) {
				coustomerDAO.create(coustomer);
			}else {
				coustomerDAO.merge(coustomer);
			}
		}catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wyst¹pi³ b³¹d zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_COUSTOMER_LIST;
	}
	
	
}
