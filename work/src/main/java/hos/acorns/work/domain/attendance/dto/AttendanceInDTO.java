package hos.acorns.work.domain.attendance.dto;

import java.time.LocalDate;

import hos.acorns.work.domain.attendance.entity.AttendanceEntity;
import hos.acorns.work.domain.dayOff.dto.DayOffCreateDTO;
import hos.acorns.work.domain.dayOff.entity.DayOffEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceInDTO {
	
    private Long idx;
    private String regDt;
    private String workIn;

    public AttendanceEntity toAttendanceEntity() {
		return AttendanceEntity.builder()
				.userIdx(idx)
				.workIn(workIn)
				.regDt(regDt)
				.build();
	}
}
