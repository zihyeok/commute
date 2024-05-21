package hos.acorns.work.domain.dayOff.entity;


import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Formula;

import hos.acorns.work.domain.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@DynamicInsert
@Entity
@Data
@NoArgsConstructor (access = AccessLevel.PROTECTED) 
@AllArgsConstructor
@ToString
@Builder
@Table(name = "dayoff_log")
public class DayOffEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("인덱스")
	private Long idx;

	@Comment("유저인덱스")
	@Column(name="user_idx", nullable = false)
	private Long userIdx;

	@ColumnDefault("1")
	@Comment("카테고리 1:연차, 2:반차, 3:기타")
	private int category;

	@Column(nullable = false)
	@Comment("사유 ex)병가 등...")
	private String content;

	@Column(name="reg_dt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Comment("신청일")
	private LocalDate regDt;

	@Column(name="udt_dt", columnDefinition = "TIMESTAMP")
	@Comment("수정일")
	private String udtDt;

	@Column(name="start_dt", length = 100, nullable = false)
	@Comment("시작일")
	private String startDt;

	@ColumnDefault("1")
	@Column(name="dayoff_count")
	@Comment("사용개수")
	private float dayoffCount;

	@ColumnDefault("0")
	@Column(name="off_check")
	@Comment("관리자확인 0:확인X, 1:확인O")
	private int offCheck;
	
	@ColumnDefault("0")
	@Column(name="user_off_check")
	@Comment("직원확인 0:확인X, 1:확인O")
	private int userOffCheck;

	@ColumnDefault("1")
	@Comment("상태값 0:취소, 1:신청, 2:승인, 3:거절")
	private int active;

}
