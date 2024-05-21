package hos.acorns.work.domain.user.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hos.acorns.work.domain.user.dto.UserDTO;
import hos.acorns.work.domain.user.dto.UserRequestDTO;
import hos.acorns.work.domain.user.entity.UserEntity;
import hos.acorns.work.domain.user.repository.UserRepository;
import hos.acorns.work.global.security.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	// 회원가입
	@Transactional
	public Map<String, Object> userRegister(UserRequestDTO user) {

		// return
		Map<String, Object> obj = new HashMap<>();

		// 비밀번호 암호화
		String encPw = passwordEncoder.encode(user.getLgnPw());
		user.setLgnPw(encPw);

		// 아이디 중복 체크
		Optional<UserEntity> lgnIdDupCheck = userRepository.findByLgnId(user.getLgnId());

		if(lgnIdDupCheck.isPresent()) {
			// 중복 있을시
			obj.put("res", "FAIL");
			obj.put("msg", "중복된 아이디가 있습니다.");
			return obj;
		}

		// 아이디 중복 없을시 회원가입 진행
		UserEntity userEntity = userRepository.save(user.toUserEntity());

		if(userEntity != null) {
			// 회원가입 성공S
			obj.put("res", "OK");
			obj.put("msg", "회원가입에 성공하셨습니다.");
		}else {
			// 회원가입 실패
			obj.put("res", "FAIL");
			obj.put("msg", "회원가입에 실패하셨습니다. 다시 시도해주세요.");
			return obj;
		}
		return obj;
	}

	//로그인
	@Transactional(readOnly = true)
	public Map<String, Object> userLogin(String lgnId, String lgnPw, HttpServletRequest request, HttpServletResponse response){

		// return
		Map<String, Object> obj = new HashMap<>();

		// 아이디 조회
		Optional<UserEntity> UserLogin = userRepository.findByLgnId(lgnId);

		if(!UserLogin.isPresent()) {
			// 아이디 정보 없음
			obj.put("res", "FAIL");
			obj.put("msg", "일치하는 회원이 없습니다.");
			return obj;
		}else {
			// 아이디 정보 있음
			UserEntity userEntity = UserLogin.get();
			UserDTO dto = UserDTO.toUserInfoDTO(userEntity);

			// 비밀번호 확인
			if(!passwordEncoder.matches(lgnPw, dto.getLgnPw())) {
			      obj.put("res", "FAIL");
			      obj.put("msg", "일치하는 회원이 없습니다.");
			      return obj;
			}

			if(dto.getActive() == 0) {
				obj.put("res", "FAIL");
			    obj.put("msg", "일치하는 회원이 없습니다.");
			    return obj;
			}else if(dto.getActive() == 2) {
				obj.put("res", "FAIL");
			    obj.put("msg", "승인대기중입니다.");
			    return obj;
			}

			// 기기 확인후 직원 mobile 막기
			if(dto.getPosition() != 4) { // 직원이면
				// 기기 확인
				String device = "";
				String userAgent = request.getHeader("User-Agent").toUpperCase();
				if(userAgent.indexOf("IS_MOBILE") > -1) {
					// 모바일이면
					device = "IS_MOBILE";
					obj.put("res", "FAIL");
					obj.put("msg", "PC로만 접속할 수 있습니다.");
					System.out.println("디바이스1 : "+device);
					return obj;
				}else {
					device = "IS_PC";
					System.out.println("디바이스2 : "+device);
				}
				System.out.println("디바이스 : "+device);
			}

			obj.put("res", "OK");
			obj.put("msg", "로그인에 성공하셨습니다.");
//			obj.put("data", dto);
			int position = dto.getPosition();
			response.setHeader("authorization", jwtProvider.createToken(dto.getIdx(), dto.getPosition()));
			response.setHeader("position", Integer.toString(position));

			return obj;
		}
	}

	/*@Transactional
	public Map<String, Object> Article(long idx) {
		Map<String, Object> obj = new HashMap<>();

		// idx 값이 유효한지 검사
		if (idx <= 0) {
			// 실패: 잘못된 idx 값
			obj.put("res", "FAIL");
			obj.put("msg", "잘못된 idx 값입니다.");
			return obj;
		}
		Optional<UserEntity> userEntity = userRepository.findById(idx);

		//목록이 비어 있지 않다면
		if (userEntity.isPresent()) {
			// 성공
			obj.put("res", "OK");
			//obj.put("msg", "목록를 성공적으로 가져왔습니다.");

			UserEntity entity = userEntity.get(); // Optional에서 값 추출
			obj.put("idx", entity.getIdx());
			obj.put("name", entity.getName());
			obj.put("lgnid", entity.getLgnId());
			obj.put("lgnpw", entity.getLgnPw());
			obj.put("active", entity.getActive());
			obj.put("phone", entity.getPhone());
			obj.put("position", entity.getPosition());
			obj.put("regdt", entity.getRegDt());
			obj.put("udtdt", entity.getUdtDt());
			obj.put("wokindt", entity.getWorkInDt());

			*//*	묶어서 불러옴
				for (Field field : entity.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				try {
					obj.put(field.getName(), field.get(entity));

				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}*//*

		} else {
			// 실패
			obj.put("res", "FAIL");
			obj.put("msg", "목록이 비어 있습니다.");
		}
		return obj;
	}*/

	@Transactional
	public Map<String, Object> List(Pageable pageable, HttpServletRequest request) {

		Map<String, Object> obj = new HashMap<>();
		// Token 만료확인
		String token = jwtProvider.resolveToken(request);
		if (!jwtProvider.validateToken(token)) {
			obj.put("res", "FAIL");
			obj.put("msg", "로그인 정보가 없거나 만료되었습니다. 다시 로그인해주세요.");
			return obj;
		}

		int position = Integer.parseInt(jwtProvider.getPosition(token));
		if (position != 4) {
			obj.put("res", "FAIL");
			obj.put("msg", "권한이 부족합니다.");
			return obj;
		}

		Page<UserEntity> userEntity = userRepository.findAllByUserList(pageable);

		//isEmpty는 없으면 true 있으면 false
		if (userEntity.isEmpty()) {
			obj.put("res", "FAIL");
			obj.put("msg", "목록이 비어 있습니다.");
			return obj;
		}

		obj.put("res", "OK");
		obj.put("msg", "목록을 성공적으로 가져왔습니다.");
		obj.put("list", userEntity);

		return obj;
	}

	@Transactional
	public Map<String, Object> Activelist(Pageable pageable, HttpServletRequest request) {
		Map<String, Object> obj = new HashMap<>();
		String token = jwtProvider.resolveToken(request);

		if (!jwtProvider.validateToken(token)) {
			obj.put("res", "FAIL");
			obj.put("msg", "로그인 정보가 없거나 만료되었습니다. 다시 로그인해주세요.");
			return obj;
		}

		int position = Integer.parseInt(jwtProvider.getPosition(token));
		if (position != 4) {
			obj.put("res", "FAIL");
			obj.put("msg", "권한이 부족합니다.");
			return obj;
		}

		Page<UserEntity> userEntity = userRepository.findByActiveList(pageable);

		//isEmpty는 없으면 true 있으면 false
		if (userEntity.isEmpty()) {

			obj.put("res", "FAIL");
			obj.put("msg", "목록이 비어 있습니다.");
			return obj;
		}

		obj.put("res", "OK");
		obj.put("msg", "목록을 성공적으로 가져왔습니다.");
		obj.put("list", userEntity);

		return obj;
	}



	/*@Transactional
	public Map<String, Object> Delete(UserRequestDTO dto, HttpSession session) {
		Optional<UserEntity> userEntity = userRepository.findById(dto.getIdx());

		Map<String, Object> obj = new HashMap<>();

		//목록이 비어 있지 않다면
		if (userEntity.isPresent()) {

			UserEntity user = userEntity.get();
			user.setActive(dto.getActive());
			user.setUdtDt(LocalDate.from(LocalDate.now()));
			userRepository.save(user);

			// 업데이트 결과에 따른 메시지 설정
			obj.put("res", "OK");
			obj.put("msg", "성공적으로 삭제되었습니다.");
		} else {
			// 찾지 못한 경우
			obj.put("res", "FAIL");
			obj.put("msg", "해당 목록을 찾을 수 없습니다.");
		}
		return obj;
	}*/

	@Transactional
	public Map<String, Object> Wait(UserRequestDTO dto, HttpServletRequest request) {
		// return
		Map<String, Object> obj = new HashMap<>();

		// Token 만료확인
		String token = jwtProvider.resolveToken(request);
		/*Long userIdx = null;*/
		if (!jwtProvider.validateToken(token)) {
			obj.put("res", "FAIL");
			obj.put("msg", "로그인 정보가 없거나 만료되었습니다. 다시 로그인해주세요.");
			return obj;
		}
		/*userIdx = Long.parseLong(jwtProvider.getUserIdx(token));
		position = Integer.parseInt(jwtProvider.getPosition(token));*/

		int position = Integer.parseInt(jwtProvider.getPosition(token));
		if (position != 4) {
			obj.put("res", "FAIL");
			obj.put("msg", "권한이 부족합니다.");
			return obj;
		}

		if (dto == null) {
			obj.put("res", "FAIL");
			obj.put("msg", "목록이 비어있습니다.");
			return obj;
		}

		Optional<UserEntity> userEntity = userRepository.findById(dto.getIdx());
		//isEmpty는 없으면 true 있으면 false
		if (userEntity.isEmpty()) {
			obj.put("res", "FAIL");
			obj.put("msg", "해당 목록을 찾을 수 없습니다.");
			return obj;
		}

		UserEntity user = userEntity.get();
		user.setActive(dto.getActive());
		user.setWorkInDt(LocalDate.now());
		user.setUdtDt(LocalDate.now());
		userRepository.save(user);

		obj.put("res", "OK");
		obj.put("msg", "성공적으로 변경되었습니다.");
		return obj;
	}

	@Transactional
	public Map<String, Object> Role(UserRequestDTO dto, HttpServletRequest request) {


		Map<String, Object> obj = new HashMap<>();
		// Token 만료확인
		String token = jwtProvider.resolveToken(request);
		if(!jwtProvider.validateToken(token)) {
			obj.put("res", "FAIL");
			obj.put("msg", "로그인 정보가 없거나 만료되었습니다. 다시 로그인해주세요.");
			return obj;
		}

		Optional<UserEntity> userEntity = userRepository.findById(dto.getIdx());
		//isEmpty는 없으면 true 있으면 false
		if (userEntity.isEmpty()) {
			obj.put("res", "FAIL");
			obj.put("msg", "해당 목록을 찾을 수 없습니다.");
			return obj;
		}

		UserEntity user = userEntity.get();
		user.setPosition(dto.getPosition());
		user.setUdtDt(LocalDate.from(LocalDate.now()));
		userRepository.save(user);

		// 업데이트 결과에 따른 메시지 설정
		obj.put("res", "OK");
		obj.put("msg", "성공적으로 변경되었습니다.");

		return obj;
	}

	// 직원정보수정
	@Transactional
	public Map<String, Object> userUpdate(UserRequestDTO user, HttpServletRequest request) {

		// return
		Map<String, Object> obj = new HashMap<>();

		// Token 만료확인
		String token = jwtProvider.resolveToken(request);
		if(!jwtProvider.validateToken(token)) {
			obj.put("res", "FAIL");
			obj.put("msg", "로그인 정보가 없거나 만료되었습니다. 다시 로그인해주세요.");
			return obj;
		}

		// 비밀번호 암호화
		String encPw = passwordEncoder.encode(user.getLgnPw());
		user.setLgnPw(encPw);

		// 정보조회
		Optional<UserEntity> userInfo = userRepository.findById(user.getIdx());

		if(userInfo.isPresent()) {
			// 정보조회시 있으면 수정
			UserEntity userEntity = userInfo.get();
				userEntity.setLgnPw(user.getLgnPw());
				userEntity.setName(user.getName());
				userEntity.setPhone(user.getPhone());
				userEntity.setEmail(user.getEmail());
				userEntity.setUdtDt(LocalDate.now());
			UserEntity updateEntity = userRepository.save(userEntity);

			if(updateEntity == null) {
				obj.put("res", "FAIL");
				obj.put("msg", "수정이 실패되었습니다. 다시 시도해주세요.");
				return obj;
			}
			obj.put("res", "OK");
			obj.put("msg", "정보수정 완료.");
			obj.put("list", updateEntity);
			return obj;
		}
		return obj;
	}

	// 유저정보조회
	@Transactional(readOnly = true)
	public Map<String, Object> userInfo(HttpServletRequest request) {

		// return
		Map<String, Object> obj = new HashMap<>();

		// Token 만료확인
		Long userIdx = null;
		String token = jwtProvider.resolveToken(request);
		if(!jwtProvider.validateToken(token)) {
			obj.put("res", "FAIL");
			obj.put("msg", "로그인 정보가 없거나 만료되었습니다. 다시 로그인해주세요.");
            return obj;
		}else {
			userIdx = Long.parseLong(jwtProvider.getUserIdx(token));
		}

		Optional<UserEntity> userInfo = userRepository.findById(userIdx);

		if(!userInfo.isPresent()) {
			obj.put("res", "FAIL");
			obj.put("msg", "유저정보가 없습니다.");
			return obj;
		}else {
			// entity -> dto 변환
			UserEntity entity = userInfo.get();
			UserDTO dto = UserDTO.toUserInfoDTO(entity);

			obj.put("res", "OK");
			obj.put("msg", "유저정보 조회가 완료되었습니다.");
			obj.put("data", dto);
			return obj;
		}
	}
}




