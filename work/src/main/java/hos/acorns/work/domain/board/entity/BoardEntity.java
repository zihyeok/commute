package hos.acorns.work.domain.board.entity;

import java.time.LocalDate;
import java.util.Date;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@DynamicInsert
@Entity
@Setter
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
@Table(name="board")
public class BoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("인덱스")
	private Long idx;

	@Column(nullable = false)
	@Comment("카테고리 0:사내게시판, 1:공지사항")
	private int category;

	@Column(length=50, nullable = false)
	@Comment("제목")
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	@Comment("내용")
	private String content;

	@Column(name="board_pw", length=150)
	@Comment("비밀번호")
	private String boardPw;

	@CreationTimestamp
	@Column(name="reg_dt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Comment("작성일")
	private LocalDate regDt;

	@UpdateTimestamp
	@Column(name="udt_dt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Comment("수정일, 공지사항 수정시에만 사용")
	private LocalDate udtDt;

	@ColumnDefault("0")
	@Comment("조회수")
	private int view;

	@Column(columnDefinition = "integer default 0", nullable = false)
	@Comment("상태값 0:정상, 1:삭제")
	private int state;

}
