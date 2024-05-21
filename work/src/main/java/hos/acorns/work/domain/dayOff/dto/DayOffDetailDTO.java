package hos.acorns.work.domain.dayOff.dto;

import hos.acorns.work.domain.dayOff.entity.DayOffEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@ToString
@Data
@AllArgsConstructor
public class DayOffDetailDTO {
	
	private Long idx;
	private Long userIdx;
    private String category;
    private String content;
    private String regDt;
    private String udtDt;
    private String startDt;
    private float dayoffCount;
    private String offCheck;
    private int active;
    
    public DayOffDetailDTO(DayOffEntity dayOffEntity) {
		this.idx = dayOffEntity.getIdx();
		this.userIdx = dayOffEntity.getUserIdx();
		this.category = String.valueOf(dayOffEntity.getCategory());
		this.content = dayOffEntity.getContent();
		this.regDt = String.valueOf(dayOffEntity.getRegDt());
		this.udtDt = String.valueOf(dayOffEntity.getUdtDt());
		this.startDt = dayOffEntity.getStartDt();
		this.dayoffCount = dayOffEntity.getDayoffCount();
		this.offCheck = String.valueOf(dayOffEntity.getOffCheck());
		this.active = dayOffEntity.getActive();
	}

}
