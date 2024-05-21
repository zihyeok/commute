package hos.acorns.work.domain.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@ToString
@Data
@AllArgsConstructor
public class ReferenceDTO {
    private Long idx;
    private int dayOffCount;
    private String allowIp;
}
