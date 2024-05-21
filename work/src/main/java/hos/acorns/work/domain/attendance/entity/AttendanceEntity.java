package hos.acorns.work.domain.attendance.entity;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

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
@Builder
@ToString
@Table(name = "work_log")
public class AttendanceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("인덱스")
	private Long idx;

	@Column(name="user_idx", nullable = false)
	@Comment("유저인덱스")
	private Long userIdx;

	@CreationTimestamp
	@Column(name="reg_dt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Comment("작성날짜")
	private String regDt;

	@CreationTimestamp
	@Column(name="workIn", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Comment("출근시간")
	private String workIn;

	@Column(name="workOut", columnDefinition = "TIMESTAMP")
	@Comment("퇴근시간")
	private String workOut;
	
}
