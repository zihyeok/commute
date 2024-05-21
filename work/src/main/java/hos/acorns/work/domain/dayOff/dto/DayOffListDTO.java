package hos.acorns.work.domain.dayOff.dto;

import java.time.LocalDate;

import hos.acorns.work.domain.dayOff.entity.DayOffEntity;
import hos.acorns.work.domain.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@ToString
@Data
@AllArgsConstructor
public class DayOffListDTO {
	
	    private Long idx;
//	    private Long userIdx;
	    private String category;
	    private String content;
//	    private String regDt;
	    private String udtDt;
	    private String startDt;
	    private float dayoffCount;
	    private String offCheck;
	    private String active;
	    
	    public DayOffListDTO(DayOffEntity dayOffEntity) {
			this.idx = dayOffEntity.getIdx();
			this.category = String.valueOf(dayOffEntity.getCategory());
			this.content = dayOffEntity.getContent();
			this.udtDt = String.valueOf(dayOffEntity.getUdtDt());
			this.startDt = dayOffEntity.getStartDt();
			this.dayoffCount = dayOffEntity.getDayoffCount();
			this.offCheck = String.valueOf(dayOffEntity.getOffCheck());
			this.active = String.valueOf(dayOffEntity.getActive());
		}

}
