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

import jsf.hockshop.dao.PhotoDAO;
import jsf.hockshop.entities.Photo;

@Named
@ViewScoped
public class PhotoEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PHOTO_LIST = "/public/photoList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Photo photo = new Photo();
	private Photo loaded = null;

	@EJB
	PhotoDAO photoDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Photo getPhoto() {
		return photo;
	}

	public void onLoad() throws IOException {

		loaded = (Photo) flash.get("photo");

		if (loaded != null) {
			photo = loaded;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "B³êdne u¿ycie systemu", null));

		}

	}

	public String saveData() {
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (photo.getIdPhoto() == null) {
				photoDAO.create(photo);
			} else {
				photoDAO.merge(photo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wyst¹pi³ b³¹d zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_PHOTO_LIST;
	}
}
