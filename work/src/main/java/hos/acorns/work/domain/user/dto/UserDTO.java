package hos.acorns.work.domain.user.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

import hos.acorns.work.domain.user.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO{

	private long idx; 		//사번
	private String lgnId;	//아이디
	private String lgnPw;	//비번
	private String name;	//이름
	private int position;	//1:신입, 2:사원, 3:팀장, 4:관리자
	private String phone;	//폰
	private String email;	//이메일
	private int active;	//0:퇴직,1:재직,2:승인대기
	private String regDt;	//등록일
	private String workInDt;	//입사일
	private String udtDt;	//수정일

	 public static UserDTO toUserInfoDTO(UserEntity userEntity) {
		 UserDTO userDTO = new UserDTO();
		 userDTO.setIdx(userEntity.getIdx());
		 userDTO.setLgnId(userEntity.getLgnId());
		 userDTO.setLgnPw(userEntity.getLgnPw());
		 userDTO.setName(userEntity.getName());
		 userDTO.setPosition(userEntity.getPosition());
		 userDTO.setPhone(userEntity.getPhone());
		 userDTO.setEmail(userEntity.getEmail());
		 userDTO.setActive(userEntity.getActive());
		 userDTO.setRegDt(userEntity.getRegDt());
		 userDTO.setWorkInDt(String.valueOf(userEntity.getWorkInDt()));
		 userDTO.setUdtDt(String.valueOf(userEntity.getUdtDt()));
        return userDTO;
    }

}
