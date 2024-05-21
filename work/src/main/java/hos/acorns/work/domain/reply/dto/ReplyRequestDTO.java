package hos.acorns.work.domain.reply.dto;


import hos.acorns.work.domain.reply.entity.ReplyEntity;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ReplyRequestDTO {

    private Long idx; //인덱스
    private Long refIdx; //참고 게시글 번호
    private String replyPw; // 비밀번호
    private String content; // 내용
    private String regDt; //작성일
    private int state;  //상태값 0:정상, 1:삭제

    public ReplyEntity toReplyEntity() {
        return ReplyEntity.builder()
                .refIdx(refIdx)
                .replyPw(replyPw)
                .content(content)
                .state(state)
                .build();
    }

}
