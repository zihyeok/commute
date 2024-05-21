package hos.acorns.work.domain.user.dto;

import hos.acorns.work.domain.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserRequestDTO {

	private long idx; 		//사번
	private String lgnId;	//아이디
	private String lgnPw;	//비번
	private String name;	//이름
	private String gender;	//성별
	private int position;	//1:신입, 2:사원, 3:팀장, 4:관리자
	private String phone;	//폰
	private String email;	//이메일
	private int active;	//0:퇴직,1:재직,2:승인대기
	private String regDt;	//등록일
	private String workInDt;	//입사일
	private String udtDt;	//수정일

	public UserEntity toUserEntity() {
		return UserEntity.builder()
				.lgnId(lgnId)
				.lgnPw(lgnPw)
				.name(name)
				.phone(phone)
				.email(email)
				.active(2)
				.build();
	}
	public UserEntity tosUserEntity() {
		return UserEntity.builder()
				.lgnId(lgnId)
				.lgnPw(lgnPw)
				.name(name)
				.phone(phone)
				.email(email)
				.active(active)
				.position(position)
				.regDt(regDt)
				.workInDt(LocalDate.parse(workInDt))
				.udtDt(LocalDate.parse(udtDt))
				.build();
	}


}
