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

import jsf.hockshop.dao.CoustomerDAO;
import jsf.hockshop.entities.Coustomer;

@Named
@RequestScoped
public class CoustomerListBB {
	private static final String PAGE_COUSTOMER_EDIT = "coustomerEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String coustomerName;
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	CoustomerDAO coustomerDAO;
	
	public String getCoustomerName() {
		return coustomerName;
	}
	
	public void setCoustomerName(String coustomerName) {
		this.coustomerName=coustomerName;
	}
	
	public List<Coustomer>getFullList(){
		return coustomerDAO.getFullList();
	}
	
	public List<Coustomer> getList(){
		List<Coustomer> list= null;
		
		Map<String,Object> searchParams = new HashMap<String,Object>();
		
		if (coustomerName!=null && coustomerName.length()>0) {
			searchParams.put("coustomerName",coustomerName);
		}
		
		list = coustomerDAO.getList(searchParams);
		return list;
	}
	
	public String newCoustomer() {
		Coustomer coustomer = new Coustomer();
		
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
}
