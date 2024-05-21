package hos.acorns.work.domain.dayOff.dto;

public interface DayOffAlarmListProjection {
	
	Long getIdx();
	Long getUserIdx();
	String getActive();
	String getCategory();
	String getStartDt();
	String getLgnName();
	String getLgnId();
	int getDayoffCount();
	String getContent();
	int getOffCheck();
	String getUdtDt();

}
