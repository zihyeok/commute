package hos.acorns.work.domain.board.dto;

import hos.acorns.work.domain.board.entity.BoardEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class BoardResponseDTO {

    private Long idx;
    private int category;
    private String title;
    private String content;
    private String boardPw;
    private String regDt;
    private String udtDt;
    private int view;
    private int state;

    public BoardResponseDTO (BoardEntity boardEntity) {
        this.idx = boardEntity.getIdx();
        this.category = boardEntity.getCategory();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.boardPw = boardEntity.getBoardPw();
        this.regDt = String.valueOf(boardEntity.getRegDt());
        this.udtDt = String.valueOf(boardEntity.getUdtDt());
        this.view = boardEntity.getView();
        this.state = boardEntity.getState();
    }

}
