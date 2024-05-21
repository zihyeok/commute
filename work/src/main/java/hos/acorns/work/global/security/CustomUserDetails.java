package hos.acorns.work.global.security;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hos.acorns.work.domain.user.entity.UserEntity;

// Spring Security를 사용한 유저 인증과정에서는 UserDetails를 참조하여 인증 진행하기 때문에 상속받아 진행
// UserEntity에 바로 상속받아도 되지만 그러면 엔티티가 오염되기때문에 따로 분리
// 사용X -> 추후 security 사용시 사용 예정

public class CustomUserDetails implements UserDetails{
	
	private final UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public final UserEntity getUserEntity() {
        return userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	// position을 역할(role)로 변환
        String role = String.valueOf(userEntity.getPosition());

        // 역할(role)을 사용한 SimpleGrantedAuthority 컬렉션 반환
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
    
    @Override
    public String getPassword() {
        return userEntity.getLgnPw();
    }

    @Override
    public String getUsername() {
        return userEntity.getLgnId();
    }

    // JWT를 이용할 것이기 때문에 아래 isAccountNonExpired() 아래로 4개속성은 true로 설정한다.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
