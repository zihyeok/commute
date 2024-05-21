package hos.acorns.work.domain.dayOff.dto;

import java.time.LocalDate;

import hos.acorns.work.domain.dayOff.entity.DayOffEntity;
import hos.acorns.work.domain.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DayOffCreateDTO {
	
	private Long idx;
    private Long userIdx;
    private int category;
    private String content;
    private String startDt;
    private float dayoffCount;
    private int active;
    
    public DayOffEntity toDayOffEntity() {
		return DayOffEntity.builder()
				.idx(idx)
				.userIdx(userIdx)
				.category(category)
				.content(content)
				.startDt(startDt)
				.active(1)
				.dayoffCount(dayoffCount)
				.build();
	}

}
