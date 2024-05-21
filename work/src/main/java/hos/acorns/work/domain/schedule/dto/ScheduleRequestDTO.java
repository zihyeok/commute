package hos.acorns.work.domain.schedule.dto;


import hos.acorns.work.domain.schedule.entity.ScheduleEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ScheduleRequestDTO {

    private Long id;
    private String start;
    private String end;
    private String title;
    private String regDt;
    private String udtDt;
    private int udtActive;
    //private int active;
/*     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
     .start(LocalDate.parse(start,formatter))
            .end(LocalDate.parse(end,formatter))*/
    public ScheduleEntity toScheduleEntity() {

        return ScheduleEntity.builder()
                .udtActive(1)
                .title(title)
                .start(LocalDate.parse(start))
                .end(LocalDate.parse(end))
                .build();
    }



}
