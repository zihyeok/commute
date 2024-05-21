package hos.acorns.work.domain.board.dto;

import hos.acorns.work.domain.board.entity.BoardEntity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class BoardRequestDTO {

    private Long idx;       //
    private int category;   //카테고리 0:사내게시판, 1:공지사항
    private String title;   //제목
    private String content; //내용
    private String boardPw; //비밀번호
    private String regDt;   //작성일
    private String udtDt;   //수정일
    private int view;   //조회수
    private int state;   //상태값 0:정상, 1:삭제

    public BoardEntity toBoardEntity() {
        return BoardEntity.builder()
                .category(category)
                .title(title)
                .content(content)
                .boardPw(boardPw)
                .view(view)
                .state(state)
                .build();
    }
    /*public BoardEntity toBoardIdEntity() {
        return BoardEntity.builder()
                .idx(idx)
                .build();
    }*/

}
