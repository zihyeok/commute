package hos.acorns.work.domain.board.dto;

import lombok.*;


@NoArgsConstructor
@Builder
@ToString
@Data
@AllArgsConstructor
public class BoardDTO {
    private Long idx; //인덱스
    private int category; //카테고리 0:사내게시판, 1:공지사항
    private String title;   //제목
    private String content; //내용
    private String boardPw; //비밀번호
    private String regDt;   //작성일
    private String udtDt;   //수정일
    private int view;   //조회수
    private int state; //상태값 0:정상, 1:삭제

}
