package edu.calpoly.csc.luna.turboplan.core.scheduler;

import org.apache.log4j.Logger;

/**
 * This class is the Stategy design  to be able
 *   to implement different scheduling algorithms.
 * 		
 */
public class Scheduler {
	private final SchedulingStrategy strategy;
	private final Long companyId;
	private final Long userId;
	private final Long taskId;
	private final SchedulingOptions opt;

	private static final Logger log = Logger.getLogger(Scheduler.class);
	
	/**
	 * This is a contructor to create a new Scheduler
	 *   that takes a company ID and the strategy to
	 *   generate the schedule
	 * 
	 * @param companyId The company for which to generate
	 * 		the schedule for
	 * @param strategy The way that the schedule will be
	 *      generated
	 */
	private Scheduler(Long companyId, Long userId, SchedulingStrategy strategy) {
		super();
		
		this.companyId = companyId;
		this.strategy = strategy;
		this.userId = userId;
		this.taskId = -1L;
		this.opt = new SchedulingOptions(0,0,false,false,false,false,false,false,false,false);
	}
	
	/**
	 * This is a contructor to create a new Scheduler
	 *   that takes a company ID and the strategy to
	 *   generate the schedule
	 * 
	 * @param companyId The company for which to generate
	 * 		the schedule for
	 * @param strategy The way that the schedule will be
	 *      generated
	 */
	private Scheduler(Long companyId, Long userId, Long taskId, SchedulingStrategy strategy) {
		super();
		
		this.companyId = companyId;
		this.strategy = strategy;
		this.userId = userId;
		this.taskId = taskId;
		this.opt = new SchedulingOptions(0,0,false,false,false,false,false,false,false,false);
	}
	
	/**
	 * This is a contructor to create a new Scheduler
	 *   that takes a company ID and the strategy to
	 *   generate the schedule
	 * 
	 * @param companyId The company for which to generate
	 * 		the schedule for
	 * @param strategy The way that the schedule will be
	 *      generated
	 */
	private Scheduler(Long companyId, Long userId, SchedulingStrategy strategy, SchedulingOptions opt) {
		super();
		
		this.companyId = companyId;
		this.strategy = strategy;
		this.userId = userId;
		this.taskId = -1L;
		this.opt = opt;
	}
	
	/**
	 * Creates a new instance of the Scheduler
	 * 
	 * @param companyId The company for which to generate
	 * 		the schedule for
	 * @param strat The way that the schedule will be
	 *      generated
	 * @return a Scheduler object
	 */
	public static Scheduler newInstance(Long companyId, Long userId, SchedulingStrategy strat) {
		return new Scheduler(companyId, userId, strat);
	}
	
	/**
	 * Creates a new instance of the Scheduler
	 * 
	 * @param companyId The company for which to generate
	 * 		the schedule for
	 * @return a Scheduler object
	 */
	public static Scheduler newInstance(Long companyId, Long userId) {
		return new Scheduler(companyId, userId, new StartTimeFirstSchedule());
	}
	
	/**
	 * Creates a new instance of the Scheduler
	 * 
	 * @param companyId The company for which to generate
	 * 		the schedule for
	 * @return a Scheduler object
	 */
	public static Scheduler newInstance(Long companyId, Long userId, Long taskId) {
		return new Scheduler(companyId, userId, taskId, new StartTimeFirstSchedule());
	}
	
	/**
	 * Creates a new instance of the Scheduler
	 * 
	 * @param companyId The company for which to generate
	 * 		the schedule for
	 * @return a Scheduler object
	 */
	public static Scheduler newInstance(Long companyId, Long userId, SchedulingOptions opt) {
		if(opt.getReverse()) {
			log.trace("Assign Close to Due Dates Initiated");
			return new Scheduler(companyId, userId, new EndTimeFirstSchedule(), opt);
		} else {
			return new Scheduler(companyId, userId, new StartTimeFirstSchedule(), opt);
		}
	}

	/**
	 * Calls the generate schedule method of the given
	 *   strategy
	 * 
	 */
	public void run() {
		strategy.generateSchedule(companyId, userId);
	}
	
	/**
	 * Calls the generate schedule method of the given
	 *   strategy with schedule options
	 * 
	 */
	public void run(SchedulingOptions opt) {
		log.debug("Options: " + opt.toString());
		strategy.generateSchedule(companyId, userId, opt);
	}
	
	/**
	 * Calls the assign task method 
	 */
	public Boolean assign() {
		if(taskId != -1L) {
			return strategy.assign(taskId, userId);
		}
		
		return false;
	}
}
