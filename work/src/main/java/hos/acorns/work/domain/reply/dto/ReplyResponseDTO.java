package hos.acorns.work.domain.reply.dto;

import hos.acorns.work.domain.reply.entity.ReplyEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReplyResponseDTO {

    private Long idx; //인덱스
    private Long refIdx; //참고 게시글 번호
    private String replyPw; // 비밀번호
    private String content; // 내용
    private String regDt; //작성일
    private int state;  //상태값 0:정상, 1:삭제

    public ReplyResponseDTO (ReplyEntity replyEntity) {
        this.idx = replyEntity.getIdx();
        this.refIdx = replyEntity.getRefIdx();
        this.replyPw = replyEntity.getReplyPw();
        this.content = replyEntity.getContent();
        this.regDt = String.valueOf(replyEntity.getRegDt());
        this.state = replyEntity.getState();
    }
}
