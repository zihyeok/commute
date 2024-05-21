package hos.acorns.work.domain.attendance.dto;

public interface AttendanceListProjection {
	
	Long getIdx();
	Long getUserIdx();
	String getLgnId();
	String getLgnName();
	String getRegDt();
	String getWorkIn();
	String getWorkOut();

}
