package hos.acorns.work.domain.attendance.dto;

import hos.acorns.work.domain.dayOff.entity.DayOffEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@ToString
@Data
@AllArgsConstructor
public class AttendanceListDTO {
	
	private Long idx;
	private Long userIdx;
	private String regDt;
	private String workIn;
	private String workOut;
	
	private int position;
	private String userId;
	private String name;
	
//	 public DayOffEntity toDayOffEntity() {
//			return DayOffEntity.builder()
//					.idx(idx)
//					.userIdx(userIdx)
//					.regDt(regDt)
//					.workIn(workIn)
//					.workOut(workOut)
//					.build();
//		}
}
