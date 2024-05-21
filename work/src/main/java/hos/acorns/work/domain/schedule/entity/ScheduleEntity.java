package hos.acorns.work.domain.schedule.entity;

import java.sql.Timestamp;
import java.time.LocalDate;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

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
@Setter
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
@Table(name = "schedule")
public class ScheduleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("인덱스")
	private Long id;

	@Column(name="start_dt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Comment("시작일")
	private LocalDate start;

	@Column(name="end_dt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Comment("종료일")
	private LocalDate end;

	@Column(length=100, nullable = false)
	@Comment("내용")
	private String title;

	// @CreationTimestamp
	@Column(name="reg_dt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Comment("등록일")
	private LocalDate regDt;

	// @UpdateTimestamp
	@Column(name="udt_dt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Comment("수정일")
	private LocalDate udtDt;

	@ColumnDefault("1")
	@Column(name="udt_active")
	@Comment("수정 활성값 1:수정사항없음, 2:수정, 3:삭제")
	private int udtActive;

	/*@ColumnDefault("1")
	@Comment("활성값 0:종료, 1:진행중")
	private int active;*/
}
