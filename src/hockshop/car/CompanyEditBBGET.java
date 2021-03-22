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

import jsf.hockshop.dao.CompanyDAO;
import jsf.hockshop.entities.Company;

@Named
@ViewScoped
public class CompanyEditBBGET implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_COMPANY_LIST = "/public/companyList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Company company = new Company();
	private Company loaded = null;

	@EJB
	CompanyDAO companyDAO;

	@Inject
	FacesContext context;


	public Company getCompany() {
		return company;
	}

	public void onLoad() throws IOException {
		if (!context.isPostback()) {
			if (!context.isValidationFailed() && company.getIdCompany() != null) {
				loaded = companyDAO.find(company.getIdCompany());
			}
			if (loaded != null) {
				company = loaded;
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "BÅ‚Ä™dne uÅ¼ycie systemu", null));

			}
		}

	}

	public String saveData() {
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (company.getIdCompany() == null) {
				companyDAO.create(company);
			} else {
				companyDAO.merge(company);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wyst¹pi³ b³¹d zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_COMPANY_LIST;
	}
}
