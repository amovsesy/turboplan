package edu.calpoly.csc.luna.turboplan.web.client.service;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Abstraction layer for all services. New services will be registered in here,
 * and when used, retrieved from here.
 * 
 * For every service:
 * <ol>
 * <li>Add 3 lines in the static block</li>
 * <li>Add a get method to retrieve the that async service</li>
 * </ol>
 * 
 * @author Ming Liu
 * 
 */
public class ServiceManager {
	private static final Map<Class<? extends BaseService>, BaseServiceAsync> serviceMap = new HashMap<Class<? extends BaseService>, BaseServiceAsync>();

	static {
		setEndPoint((ManageEmployeesServiceAsync) GWT
				.create(ManageEmployeesService.class),
				ManageEmployeesService.class);

		setEndPoint((ManageTasksServiceAsync) GWT
				.create(ManageTasksService.class), ManageTasksService.class);

		setEndPoint((UserServiceAsync) GWT.create(UserService.class),
				UserService.class);
		
		setEndPoint((EmailServiceAsync) GWT.create(EmailService.class),
				EmailService.class);
		
		setEndPoint((CompanyServiceAsync) GWT.create(CompanyService.class),
				CompanyService.class);
	}

	public static ManageEmployeesServiceAsync getManageEmployeesServiceAsync() {
		return (ManageEmployeesServiceAsync) serviceMap
				.get(ManageEmployeesService.class);
	}

	public static ManageTasksServiceAsync getManageTasksServiceAsync() {
		return (ManageTasksServiceAsync) serviceMap
				.get(ManageTasksService.class);
	}

	public static UserServiceAsync getUserServiceAsync() {
		return (UserServiceAsync) serviceMap
				.get(UserService.class);
	}
	
	public static EmailServiceAsync getEmailServiceAsync() {
		return (EmailServiceAsync) serviceMap
				.get(EmailService.class);
	}
	
	public static CompanyServiceAsync getComapnyServiceAsync() {
		return (CompanyServiceAsync) serviceMap
				.get(CompanyService.class);
	}

	private static <T extends BaseServiceAsync, K> void setEndPoint(T async,
			Class<? extends BaseService> clazz) {
		String name = "/"
				+ clazz.getName().substring(50, clazz.getName().length() - 7);
		System.out.println("name = " + name);
		String endpoint = GWT.getModuleBaseURL() + name;

		((ServiceDefTarget) async).setServiceEntryPoint(endpoint);
		serviceMap.put(clazz, async);
	}
}
