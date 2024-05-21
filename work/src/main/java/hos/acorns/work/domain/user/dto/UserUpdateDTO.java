package hos.acorns.work.domain.user.dto;

import hos.acorns.work.domain.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateDTO {

	private Long idx;
	private String lgnId;
	private String lgnPw;
	private String Name;
	private String gender;
	private String phone;
	private String email;
	private String udtDt;
	
	public UserUpdateDTO(UserEntity userEntity) {
		this.idx = userEntity.getIdx();
		this.lgnId = userEntity.getLgnId();
		this.lgnPw = userEntity.getLgnPw();
		this.Name = userEntity.getName();
		this.phone = userEntity.getPhone();
		this.email = userEntity.getEmail();
		this.udtDt = String.valueOf(userEntity.getUdtDt());
	}
		
}
