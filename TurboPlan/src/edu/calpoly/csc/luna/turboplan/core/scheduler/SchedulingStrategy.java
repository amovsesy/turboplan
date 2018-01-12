package edu.calpoly.csc.luna.turboplan.core.scheduler;

/**
 * This class defines an interface to be able to
 *   run different types of schedule generation
 * 		
 */
public interface SchedulingStrategy {
	public Boolean assign(Long taskId, Long userId);
	public void generateSchedule(Long companyId, Long userId);
	public void generateSchedule(Long companyId, Long userId, SchedulingOptions opt);
}
