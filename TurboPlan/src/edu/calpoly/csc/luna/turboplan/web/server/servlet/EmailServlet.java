package edu.calpoly.csc.luna.turboplan.web.server.servlet;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;

import java.security.Security;
import java.util.*;

import edu.calpoly.csc.luna.turboplan.core.dao.UserDao;
import edu.calpoly.csc.luna.turboplan.core.dao.generator.SimpleUsername;
import edu.calpoly.csc.luna.turboplan.core.dao.generator.UsernameGenerator;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.security.encryption.Encryption;
import edu.calpoly.csc.luna.turboplan.core.util.GwtUtil;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.service.EmailService;

/**
 * This class is the servlet used to send emails when new users are created,
 *   passwords are reset, and usernames are reset.
 * 		
 */
public class EmailServlet extends BaseTurboPlanServlet implements EmailService {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2295771432763291662L;
	private static final Logger log = Logger.getLogger(EmailServlet.class);
	
	private void setUpAndTransmitEmail(String subject, String message, String email) {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	     Properties props = new Properties();
	     
	     
	     props.setProperty("mail.transport.protocol", "smtp");   
	     props.setProperty("mail.host", "smtp.gmail.com");  
	     props.put("mail.smtp.auth", "true");  
	     props.put("mail.smtp.port", "465");
	     props.put("mail.smtp.socketFactory.port", "465");  
	     props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
	     props.put("mail.smtp.socketFactory.fallback", "false");  
	     Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator()   
	     {   
	    	 protected PasswordAuthentication getPasswordAuthentication()   
	    	 {  
	    		 return new PasswordAuthentication("lunasetturboplan","capstone");  
	    	 }  
	     });
	     
	    // create a message
	    try{ 
	    Transport transport = session.getTransport();  
	    InternetAddress addressFrom = new InternetAddress("lunasetturboplan@gmail.com");   
	    MimeMessage msg = new MimeMessage(session);  
	    msg.setSender(addressFrom);
	    
	    InternetAddress addressTo = new InternetAddress(email);
	    msg.addRecipient(RecipientType.TO, addressTo);
	    msg.setSubject(subject);
		msg.setText(message);
		

	    transport.connect(); 
	    transport.send(msg);  
	    transport.close();
	    }
	    catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		}
	}

	/**
	 * This is the method that sends the email to the correct email
	 * 
	 * @param password the password the user was set/reset to
	 * @param username the username the user was set/reset to
	 * @param justPass true if only the password was reset
	 * @param newUser true if a new user has been added
	 * @return the text of the email
	 * @throws MessagingException 
	 */
	public String sendEmail(String password, String username, String email, Boolean justPass, Boolean newUser){
		String message = "";
		String subject = "";
		log.debug(
				new StringBuilder("Sending email ")
					.append(username).append("/")
					.append(password).append("/")
					.append(email).append("/")
					.append(justPass ? "justPass=true" : "justPass=false")
					.append(newUser ? "newUser=true" : "newUser=false")
		);
		
//		//Set the host smtp address
//		 Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//	     Properties props = new Properties();
//	     
//	     
//	     props.setProperty("mail.transport.protocol", "smtp");   
//	     props.setProperty("mail.host", "smtp.gmail.com");  
//	     props.put("mail.smtp.auth", "true");  
//	     props.put("mail.smtp.port", "465");
//	     props.put("mail.smtp.socketFactory.port", "465");  
//	     props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
//	     props.put("mail.smtp.socketFactory.fallback", "false");  
//	     Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator()   
//	     {   
//	    	 protected PasswordAuthentication getPasswordAuthentication()   
//	    	 {  
//	    		 return new PasswordAuthentication("lunasetturboplan","capstone");  
//	    	 }  
//	     });
//	     
//	    // create a message
//	    try{ 
//	    Transport transport = session.getTransport();  
//	    InternetAddress addressFrom = new InternetAddress("lunasetturboplan@gmail.com");   
//	    MimeMessage msg = new MimeMessage(session);  
//	    msg.setSender(addressFrom);
//	    
//	    InternetAddress addressTo = new InternetAddress(email);
//	    msg.addRecipient(RecipientType.TO, addressTo);
//	    
	    if (justPass && !"changing".equals(username)) {
//	    	msg.setSubject("Password Reset");
//			msg.setText("Your password has been reset to " + password +
//					"\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html");
			
	    	subject = "Password Reset";
	    	message = "Your password has been reset to " + password +
				"\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html";
			
		}
		else if (justPass && "changing".equals(username)){
//			msg.setSubject("Password Changed");
//			msg.setText("Your password has been changed to " + password +
//					"\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html");
			
			subject = "Password Changed";
			message = "Your password has been changed to " + password +
				"\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html";
		}
		else if (newUser) {
//			msg.setSubject("Username and Password Created");
//			msg.setText("Your username has been set to " + username
//					+ " and your password has been set to " + password +
//					"\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html");
			
			subject = "Username and Password Created";
			message = "Your username has been set to " + username
				+ " and your password has been set to " + password +
				"\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html";
		}
		else {
//			msg.setSubject("Username and Password Reset");
//			msg.setText("Your username has been reset to " + username
//				+ " and your password has been reset to " + password +
//				"\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html");
			
			subject = "Username and Password Reset";
			message = "Your username has been reset to " + username
				+ " and your password has been reset to " + password +
				"\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html";
		}
//
//	    transport.connect(); 
//	    transport.send(msg);  
//	    transport.close();
//	    }
//	    catch (Exception e) {
//			// TODO: handle exception
//	    	e.printStackTrace();
//		}
		
	    setUpAndTransmitEmail(subject, message, email);
	    
	    return message;
	}

	@Override
	public String sendEmailChangePass(String password, String email) {
		String subject;
		String message;
		
		subject = "Password Changed";
		message = "Your password has been changed to " + password +
			"\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html";
		
		setUpAndTransmitEmail(subject, message, email);
		
		return message;
	}

	@Override
	public String sendEmailChangeUsername(GwtUser gwtUsr) {
		String subject;
		String message;
		String uname;
		
		User usr = GwtUtil.gwt2hib(gwtUsr);
		
		uname = new UsernameGenerator(new SimpleUsername(), usr, null).run();
		
		usr.setUserName(Encryption.getInstance().encrypt(uname));
		
		UserDao.getInstance().updateUser(usr, false, true);
		
		subject = "Username Changed";
		message = "Your username has been changed to " + uname +
			"\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html";
		
		setUpAndTransmitEmail(subject, message, usr.getProfile().getEmail());
		
		return message;
	}

	@Override
	public String sendEmailNewUser(String password, String username,
			String email) {
		String subject;
		String message;
		
		subject = "Username and Password Created";
		message = "Your username has been set to " + username
			+ " and your password has been set to " + password +
			"\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html";
		
		setUpAndTransmitEmail(subject, message, email);
		
		return message;
	}

	@Override
	public String sendEmailResetPass(String password, String email) {
		String subject;
		String message;
		
		subject = "Password Reset";
    	message = "Your password has been reset to " + password +
			"\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html";
		
		setUpAndTransmitEmail(subject, message, email);
		
		return message;
	}

	@Override
	public String sendEmailResetUnamePass(String username, String password,
			String email) {
		String subject;
		String message;
		
		subject = "Username and Password Reset";
		message = "Your username has been reset to " + username
			+ " and your password has been reset to " + password +
			"\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html";
		
		setUpAndTransmitEmail(subject, message, email);
		
		return message;
	}
}
