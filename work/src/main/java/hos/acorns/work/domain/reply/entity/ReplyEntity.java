package hos.acorns.work.domain.reply.entity;

import java.time.LocalDate;

import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import hos.acorns.work.domain.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@DynamicInsert
@Entity
@Getter
@Setter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
@Table(name = "reply")
public class ReplyEntity {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Comment("인덱스")
		private Long idx;

		@Column(name="ref_idx", nullable = false)
		@Comment("참고게시글번호")
		private Long refIdx;

		@Column(name="reply_pw", length=20, nullable = false)
		@Comment("비밀번호")
		private String replyPw;

		@Column(columnDefinition = "TEXT", nullable = false)
		@Comment("내용")
		private String content;

		@CreationTimestamp
		@Column(name="reg_dt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
		@Comment("작성일")
		private LocalDate regDt;

		@Column(nullable = false)
		@Comment("상태값 0:정상, 1:삭제")
		private int state;

}
