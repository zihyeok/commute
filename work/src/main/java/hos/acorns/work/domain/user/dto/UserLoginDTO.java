package hos.acorns.work.domain.user.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserLoginDTO {

	private String lgnId;
	private String lgnPw;
	private int active;

}
