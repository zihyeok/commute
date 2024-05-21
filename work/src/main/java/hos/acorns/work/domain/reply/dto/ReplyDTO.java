package hos.acorns.work.domain.reply.dto;

import lombok.*;

@NoArgsConstructor
@Builder
@ToString
@Data
@AllArgsConstructor
public class ReplyDTO {

    private Long idx; //인덱스
    private Long refIdx; //참고 게시글 번호
    private String replyPw; // 비밀번호
    private String content; // 내용
    private String regDt; //작성일
    private int state;  //상태값 0:정상, 1:삭제

}
