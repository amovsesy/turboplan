/**
 * 
 */
package edu.calpoly.csc.luna.turboplan.core.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.calpoly.csc.luna.turboplan.core.dao.TaskDao;
import edu.calpoly.csc.luna.turboplan.core.dao.UserDao;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.Address;

/**
 * @author Aleksandr
 *
 */
public class DistanceSchedule extends SampleSchedule implements SchedulingStrategy {
	protected class Distance {
		protected User usr;
		protected double distance;
		protected Date startTime;
		protected Date endTime;
	}
	
	ArrayList<Distance> capable = new ArrayList<Distance>();
	List<User> allUsers;
	List<Task> allTasks;
	boolean checkedUser = false;

	@Override
	public void generateSchedule(Long companyId, Long userId) {
		double distance = -1;
		double time = -1;
		
		allUsers = UserDao.getInstance()
			.getAllUsersInSameCompanyAsThisUserById(userId);
		allTasks = TaskDao.getInstance()
			.getAllNewTaskBelongToCompanyById(companyId);
		
		for(Task t : allTasks) {
//			System.err.println(t.getTitle());
			for(User u : allUsers) {
//				System.err.println(u.getProfile().getFirstName());
				String fromAddr = getPrevAddr(u,t);
				String toAddr = parseAddress(t.getLocation(), u);
				
				
//				if(fromAddr.equals(toAddr)) {
//					break;
//				}
				
				distance = getDistance(fromAddr, toAddr);
				time = getTime(fromAddr, toAddr);
//				System.err.println(fromAddr);
//				System.err.println(toAddr);
//				System.err.println(time);
//				System.err.println(distance);
				
				if(distance != -1) {
					checkCapable(distance, time, t, u);
				}
			}
			
			if(!capable.isEmpty()) {
				assignTask(t, null);
			} 
			
			capable.clear();
		}
	}

	private void checkCapable(double distance, double time, Task task, User user) {
		int time_block = 0;
		Date cur_date = new Date();
		Date end_date = new Date();
			
		/** rounds the estimated time to the nearest half hour */
		double task_len = 
			roundEstimatedTimeToMinutes(task.getEstimatedTime() + time);

		/** round up the suggested start time to the nearest half hour */
		cur_date = roundUpTime(task.getSuggestedStartTime());
		/** round down the suggested end time to the nearest half hour */
		end_date = roundDownTime(task.getSuggestedEndTime());
						
		/** iterate, checking for availability in half hour time blocks */
		while(cur_date.before(end_date)) {
				
			/** if the user is available increase available time block by half hour
			 *   otherwise, reset available time block to 0 */
			if (isUserAvailable(user, cur_date)) {
				time_block += halfhour_min;
			} else {
				time_block = 0;
			}
				
			/** adjust current time by half hour to test next time block */
			cur_date.setTime(cur_date.getTime() + halfhour_ms);
			
			/** if available time block == estimated task length, assign task */
			if (time_block == task_len) {
				
				/** adjust current time = (current time - task length) */
				cur_date = resetCurrentTimeByTaskLengh(cur_date, task_len);
				
				Distance d = new Distance();
				
				d = placeInScheduleTimes(d, task_len, cur_date, distance, user);
				
//				System.err.println("here");
//				System.err.println(task.getTitle());
//				System.err.println(d.usr.getProfile().getFirstName());
//				System.err.println(d.distance);
				
				capable.add(d);
				
				break;
			}
		}
	}

	private Distance placeInScheduleTimes(Distance d, double task_len, Date start, 
			double distance, User user) {
			Date end = new Date();

			d.startTime = roundUpTime(start);
			
			/** Add task length to start time to find end time */
			end.setTime(roundUpTime(start).getTime() + 
					(halfhour_ms * (long)(task_len / halfhour_min))); 
			
			d.endTime = end;
			d.distance = distance;
			d.usr = user;
			
			return d;
	}

	private double getTime(String fromAddr, String toAddr) {
		String xml = getData(fromAddr, toAddr);
		int first = 0;
		int end = 0;
		double t;
		
		first = xml.indexOf("printheader:");
		
		String time = xml.substring(first+12);
		
		first = time.indexOf("\\x26#8211; about \\x3cb\\x3e");
		
		first+= 26;
		
		time = time.substring(first);

		end = time.indexOf(" hour");
		
		if(end == -1) {
			int mend = time.indexOf(" mins");
			t = Double.parseDouble(time.substring(0, mend));
			t = t / 60.0;
		} else {
			int mend = 0;
			int temp = time.indexOf("hours");
			if(temp == -1 || temp > 5) {
				t = Double.parseDouble(time.substring(0, end));
				mend = time.indexOf(" mins", end+5);
//				System.err.println(end + " " + mend + " " + time);
				t += Double.parseDouble(time.substring(end+5, mend)) / 60.0;
			} else {
				t = Double.parseDouble(time.substring(0, temp));
				mend = time.indexOf(" mins", temp+6);
//				System.err.println(temp + " " + mend + " " + time);
				t += Double.parseDouble(time.substring(temp+6, mend)) / 60.0;
//				System.err.println(Double.parseDouble(time.substring(temp+6, mend)));
			}
		}
		
		return t;
	}

	private double getDistance(String fromAddr, String toAddr) {
		String xml = getData(fromAddr, toAddr);
		
		int first = xml.indexOf("class=ph_dist\\x3e\\x3cb\\x3e");
		first += 26;
		
		String miles = xml.substring(first);
		int end = miles.indexOf("\\x26#160;mi\\x3c/b\\x3e");
		
		return Double.parseDouble(miles.substring(0, end));
	}
	
	private String getData(String fromAddr, String toAddr) {
		final class WebFile {
		    // Saved response.
		    private java.util.Map<String,java.util.List<String>> responseHeader = null;
		    private java.net.URL responseURL = null;
		    private int responseCode = -1;
		    private String MIMEtype  = null;
		    private String charset   = null;
		    private Object content   = null;
		 
		    /** Open a web file. */
		    public WebFile( String urlString )
		        throws java.net.MalformedURLException, java.io.IOException {
		        // Open a URL connection.
		        final java.net.URL url = new java.net.URL( urlString );
		        final java.net.URLConnection uconn = url.openConnection( );
		        if ( !(uconn instanceof java.net.HttpURLConnection) )
		            throw new java.lang.IllegalArgumentException(
		                "URL protocol must be HTTP." );
		        final java.net.HttpURLConnection conn =
		            (java.net.HttpURLConnection)uconn;
		 
		        // Set up a request.
		        conn.setConnectTimeout( 10000 );    // 10 sec
		        conn.setReadTimeout( 10000 );       // 10 sec
		        conn.setInstanceFollowRedirects( true );
		        conn.setRequestProperty( "User-agent", "spider" );
		 
		        // Send the request.
		        conn.connect( );
		 
		        // Get the response.
		        responseHeader    = conn.getHeaderFields( );
		        responseCode      = conn.getResponseCode( );
		        responseURL       = conn.getURL( );
		        final int length  = conn.getContentLength( );
		        final String type = conn.getContentType( );
		        if ( type != null ) {
		            final String[] parts = type.split( ";" );
		            MIMEtype = parts[0].trim( );
		            for ( int i = 1; i < parts.length && charset == null; i++ ) {
		                final String t  = parts[i].trim( );
		                final int index = t.toLowerCase( ).indexOf( "charset=" );
		                if ( index != -1 )
		                    charset = t.substring( index+8 );
		            }
		        }
		 
		        // Get the content.
		        final java.io.InputStream stream = conn.getErrorStream( );
		        if ( stream != null )
		            content = readStream( length, stream );
		        else if ( (content = conn.getContent( )) != null &&
		            content instanceof java.io.InputStream )
		            content = readStream( length, (java.io.InputStream)content );
		        conn.disconnect( );
		    }
		 
		    /** Read stream bytes and transcode. */
		    private Object readStream( int length, java.io.InputStream stream )
		        throws java.io.IOException {
		        final int buflen = Math.max( 1024, Math.max( length, stream.available() ) );
		        byte[] buf   = new byte[buflen];;
		        byte[] bytes = null;
		 
		        for ( int nRead = stream.read(buf); nRead != -1; nRead = stream.read(buf) ) {
		            if ( bytes == null ) {
		                bytes = buf;
		                buf   = new byte[buflen];
		                continue;
		            }
		            final byte[] newBytes = new byte[ bytes.length + nRead ];
		            System.arraycopy( bytes, 0, newBytes, 0, bytes.length );
		            System.arraycopy( buf, 0, newBytes, bytes.length, nRead );
		            bytes = newBytes;
		        }
		 
		        if ( charset == null )
		            return bytes;
		        try {
		            return new String( bytes, charset );
		        }
		        catch ( java.io.UnsupportedEncodingException e ) { }
		        return bytes;
		    }
		 
		    /** Get the content. */
		    public Object getContent( ) {
		        return content;
		    }
		 
		    /** Get the response code. */
		    public int getResponseCode( ) {
		        return responseCode;
		    }
		 
		    /** Get the response header. */
		    public java.util.Map<String,java.util.List<String>> getHeaderFields( ) {
		        return responseHeader;
		    }
		 
		    /** Get the URL of the received page. */
		    public java.net.URL getURL( ) {
		        return responseURL;
		    }
		 
		    /** Get the MIME type. */
		    public String getMIMEType( ) {
		        return MIMEtype;
		    }
		}
		
		String url = "http://maps.google.com/maps?f=d&source=s_d&saddr="
			+ fromAddr + "&daddr=" + toAddr
			+ "&hl=en&geocode=&mra=ls&sll=34.669359,-98.789062&sspn=37.366002,"
			+ "79.101563&ie=UTF8&ll=36.456636,-121.552734&"
			+ "spn=4.612023,9.887695&z=7&layer=c&pw=2";
		
		String xml = "";
		
		try {
			WebFile wf = new WebFile(url);
			Object o = wf.getContent();
			
			xml = o.toString();
			
		} catch (Exception e) {
			//e.printStackTrace();
			return "-1";
		}
		
		
		return xml;
	}

	private String getPrevAddr(User u, Task t) {
		String ret = "";
		Task prev = null;
		Date start = new Date(t.getSuggestedStartTime().getYear(), 
				t.getSuggestedStartTime().getMonth(), 
				t.getSuggestedStartTime().getDate(), 
				0, 0, 0);
		
		for(Task task : u.getTasks()) {
			if(task.getScheduledEndTime().after(start) && 
					task.getScheduledEndTime().before(t.getSuggestedStartTime())) {
				if(prev != null) {
					if(prev.getScheduledEndTime().before(task.getScheduledEndTime())) {
						prev = task;
					}
				} else {
					prev = task;
				}
			}
		}
		
		if(prev != null) {
			Address addr = prev.getLocation();
			ret = parseAddress(addr, u);
		} else {
			checkedUser = true;
			ret = parseAddress(u.getProfile().getAddress(), u);
			checkedUser = false;
		}
		
		return ret;
	}

	private String parseAddress(Address addr, User u) {
		String ret = "";
		int start;
		int back;
		
		if(!addr.getAddress().equals("")) {
			start = 0;
			back = 0;
			
			for(back=addr.getAddress().indexOf(" "); back != -1; 
			back=addr.getAddress().indexOf(" ", start)) {
				ret += addr.getAddress().substring(start, back) + "+";
				start = back + 1;
			}
			
			ret += addr.getAddress().substring(start);
		}
		
		if(!addr.getCity().equals("")) {
			start = 0;
			back = 0;
		
			for(back=addr.getCity().indexOf(" "); back != -1; 
			back=addr.getCity().indexOf(" ", start)) {
				ret += "+" + addr.getCity().substring(start, back);
				start = back + 1;
			}
		
			ret += "+" + addr.getCity().substring(start);
		
			if(!addr.getState().equals("")) {
				start = 0;
				back = 0;
		
				for(back=addr.getState().indexOf(" "); back != -1; 
				back=addr.getState().indexOf(" ", start)) {
					ret += "+" + addr.getState().substring(start, back);
					start = back + 1;
				}
		
				ret += "+" + addr.getState().substring(start);
			}
		
			if(addr.getZip() > 0) {
				ret += "+" + addr.getZip();
			}
		} else if(!checkedUser) {
			checkedUser = true;
			ret = parseAddress(u.getProfile().getAddress(), u);
			checkedUser = false;
		} else {
			ret = parseAddress(u.getCompany().getAddress());
		}
		
		
		return ret;
	}

	private String parseAddress(String address) {
		String ret = "";
		
		int start = 0;
		int back = 0;
		
		for(back=address.indexOf(" "); back != -1; back=address.indexOf(" ", start)) {
			ret += address.substring(start, back) + "+";
			start = back+1;
		}
		
		ret += address.substring(start);
		
		return ret;
	}

	@Override
	public void generateSchedule(Long companyId, Long userId,
			SchedulingOptions opt) {

	}

	@Override
	public Boolean assignTask(Task task, User user) {
		Distance smallest = null;
		
		for(Distance d : capable) {
			if(smallest == null) {
				smallest = d;
			} else if (smallest.distance > d.distance) {
				smallest = d;
			}
		}
		
		task.setStatus(TaskStatus.Assigned);
		task.getUsers().add(smallest.usr);
		task.setScheduledStartTime(smallest.startTime);
		task.setScheduledEndTime(smallest.endTime);
		TaskDao.getInstance().updateTask(task);
		try {
			Thread.sleep(1000L);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
