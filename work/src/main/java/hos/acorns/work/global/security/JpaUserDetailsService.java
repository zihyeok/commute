package hos.acorns.work.global.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hos.acorns.work.domain.user.entity.UserEntity;
import hos.acorns.work.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

//Spring Security의 UserDetailsService는 UserDetails 정보를 토대로 유저 정보를 불러올 때 사용
//Jpa를 이용하여 DB에서 유저 정보를 조회할 것이므로 이에 맞춰서 구현
//사용X -> 추후 security 사용시 사용 예정

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String lgnId) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByLgnId(lgnId).orElseThrow(
                () -> new UsernameNotFoundException("Invalid authentication!")
        );
        return new CustomUserDetails(user);
    }

}
