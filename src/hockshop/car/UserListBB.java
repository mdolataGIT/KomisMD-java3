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


import jsf.hockshop.dao.UserDAO;
import jsf.hockshop.entities.User;

@Named
@RequestScoped
public class UserListBB {
	private static final String PAGE_USER_EDIT = "userEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String login;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	UserDAO userDAO;
		
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<User> getFullList(){
		return userDAO.getFullList();
	}

	public List<User> getList(){
		List<User> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (login != null && login.length() > 0){
			searchParams.put("login", login);
		}
		
		list = userDAO.getList(searchParams);
		
		return list;
	}

	public String newUser(){
		User user = new User();
			
		flash.put("user", user);
		
		return PAGE_USER_EDIT;
	}

	public String editUser(User user){

		flash.put("user", user);
		
		return PAGE_USER_EDIT;
	}

	public String deleteUser(User user){
		userDAO.remove(user);
		return PAGE_STAY_AT_THE_SAME;
	}
}
