package hockshop.car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

import jsf.hockshop.dao.PhotoDAO;
import jsf.hockshop.entities.Photo;

@Named
@RequestScoped
public class PhotoListBB {
	private static final String PAGE_PHOTO_EDIT = "photoEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String url;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	PhotoDAO photoDAO;
		
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Photo> getFullList(){
		return photoDAO.getFullList();
	}

	public List<Photo> getList(){
		List<Photo> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (url!= null && url.length() > 0){
			searchParams.put("url", url);
		}
		
		list = photoDAO.getList(searchParams);
		
		return list;
	}

	public String newPhoto(){
		Photo photo = new Photo();
			
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
}
