package edu.calpoly.csc.luna.turboplan.web.server.servlet;

import edu.calpoly.csc.luna.turboplan.core.dao.CompanyDao;
import edu.calpoly.csc.luna.turboplan.core.dao.UserDao;
import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.util.GwtUtil;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtCompany;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.service.CompanyService;

public class CompanyServlet extends BaseTurboPlanServlet implements CompanyService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 485796991220712647L;

	@Override
	public void addCompany(GwtCompany company) {
		Company cmp = GwtUtil.gwt2hib(company);
		String uname = CompanyDao.getInstance().addCompany(cmp);
		
		for(GwtUser gwtUsr : company.getUsers())
		{
			try {
				new EmailServlet().sendEmail(UserDao.DEFAULT_PASSWORD, uname, gwtUsr.getProfile().getEmail(), false, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}

}
