package hos.acorns.work.domain.user.entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hos.acorns.work.domain.attendance.entity.AttendanceEntity;
import hos.acorns.work.domain.dayOff.entity.DayOffEntity;
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
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("직원번호")
	private Long idx;

	@Column(name = "lgn_id", nullable = false, unique = true, length = 50)
	@Comment("아이디")
	private String lgnId;

	@Column(name = "lgn_pw", nullable = false, length = 80)
	@Comment("비밀번호")
	private String lgnPw;

	@Column(name="lgn_name", nullable = false, length = 30)
	@Comment("이름")
	private String name;

	@ColumnDefault("1")
	@Comment("직급 1:신입, 2:사원, 3:팀장, 4:관리자")
	private int position;

	@Column(length = 20)
	@Comment("핸드폰")
	private String phone;

	@Column(length = 50)
	@Comment("이메일")
	private String email;

	@Comment("활성화 0:퇴직, 1:재직, 2:승인대기")
	@ColumnDefault("1")
	private int active;

	@CreationTimestamp // insert시 현재 시간 값 적용
	@Column(name = "reg_dt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Comment("등록일")
	private String regDt;

	@Column(name = "workIn_dt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Comment("입사일")
	private LocalDate workInDt;

	@UpdateTimestamp // update시 현재 시간 적용
	@Column(name = "udt_dt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Comment("수정일")
	private LocalDate udtDt;





}
