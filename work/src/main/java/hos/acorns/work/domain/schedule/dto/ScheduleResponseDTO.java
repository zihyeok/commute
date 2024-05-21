package hos.acorns.work.domain.schedule.dto;

import hos.acorns.work.domain.schedule.entity.ScheduleEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ScheduleResponseDTO {

    private Long id;
    private String start;
    private String end;
    private String title;
    private String regDt;
    private String udtDt;
    private int udtActive;
    //private int active;

    public ScheduleResponseDTO (ScheduleEntity scheduleEntity) {
        this.id = scheduleEntity.getId();
        this.start = String.valueOf(scheduleEntity.getStart());
        this.title = scheduleEntity.getTitle();
        this.end = String.valueOf(scheduleEntity.getEnd());
        this.regDt = String.valueOf(scheduleEntity.getRegDt());
        this.udtDt = String.valueOf(scheduleEntity.getUdtDt());
        this.udtActive = scheduleEntity.getUdtActive();
        //this.active = scheduleEntity.getActive();
    }

}
