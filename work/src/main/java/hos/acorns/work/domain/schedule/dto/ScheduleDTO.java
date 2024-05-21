package hos.acorns.work.domain.schedule.dto;

import lombok.*;


@NoArgsConstructor
@Builder
@ToString
@Data
@AllArgsConstructor
public class ScheduleDTO {

    private Long id;
    private String start;
    private String end;
    private String title;
    private String regDt;
    private String udtDt;
    private int udtActive;
    //private int active;

}
