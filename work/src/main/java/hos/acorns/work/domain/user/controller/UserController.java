package hos.acorns.work.domain.user.controller;

import java.util.Map;

import ch.qos.logback.core.CoreConstants;
import hos.acorns.work.domain.board.dto.BoardResponseDTO;
import hos.acorns.work.domain.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import hos.acorns.work.domain.user.dto.UserLoginDTO;
import hos.acorns.work.domain.user.dto.UserRequestDTO;
import hos.acorns.work.domain.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/user")
@Tag(name = "User", description = "직원 APIs")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

	// 생성자주입
	private final UserService userService;

	//회원가입
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK",
					content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
			@ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
	})
	@Operation(summary = "직원 등록 API", description = "직원 등록")
	@PostMapping(value = "/register")
	public Map<String, Object> userRegister(@RequestBody UserRequestDTO user) {
		return userService.userRegister(user);
	}

	// 로그인
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK",
					content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
			@ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
	})
	@Operation(summary = "로그인 API", description = "로그인")
	@PostMapping(value = "/login")
	public Map<String, Object> login(@RequestBody Map<String, String> loginForm, HttpServletRequest request, HttpServletResponse response) {
		return userService.userLogin(loginForm.get("lgnId"), loginForm.get("lgnPw"), request, response);
	}

	/*@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK",
					content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
			@ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
	})
	@Operation(summary = "직원 상세 API", description = "필요한 값 : idx")
	@GetMapping(value = "/article")
	public Map<String, Object> UserAticle(@RequestParam Long idx) {

		return userService.Article(idx);
	}*/

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK",
					content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
			@ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
	})
	@Operation(summary = "직원 리스트 API", description = "필요한 값 :")
	@GetMapping(value = "/list")
	public Map<String, Object> UserList(@Parameter Pageable pageable, HttpServletRequest request) {

		return userService.List(pageable, request);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK",
					content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
			@ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
	})
	@Operation(summary = "직원 리스트 API", description = "필요한 값 :")
	@GetMapping(value = "/activelist")
	public Map<String, Object> UserActiveList(@Parameter Pageable pageable, HttpServletRequest request) {

		return userService.Activelist(pageable, request);
	}

	/*@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK",
					content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
			@ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
	})
	@Operation(summary = "직원 삭제 API", description = "필요한 값 : idx, active, udt_dt(공백)")
	@PostMapping(value = "/delete")
	public Map<String, Object> UserDelete(@RequestBody UserRequestDTO dto, HttpSession session) {

		return userService.Delete(dto,session);
	}*/

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK",
					content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
			@ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
	})
	@Operation(summary = "직원 승인, 삭제 API", description = "필요한 값 : idx, active")
	@PostMapping(value = "/wait")
	public Map<String, Object> UserWait(@RequestBody UserRequestDTO dto, HttpServletRequest request) {

		return userService.Wait(dto, request);
	}

	// 직원정보수정
	@Operation(summary = "회원 정보 수정 API", description = "회원 정보 수정")
	@PostMapping(value = "/update")
	public Map<String, Object> userUpdate(@RequestBody UserRequestDTO user, HttpServletRequest request) {

		return userService.userUpdate(user, request);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK",
					content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
			@ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
	})
	@Operation(summary = "직원 직급 변경 API", description = "필요한 값 : idx, position")
	@PostMapping(value = "/position")
	public Map<String, Object> Userposition(@RequestBody UserRequestDTO dto, HttpServletRequest request) {

		return userService.Role(dto, request);
	}

	// 유저정보
	@Operation(summary = "유저정보 API", description = "유저정보")
	@PostMapping(value = "/info")
	public Map<String, Object> login(HttpServletRequest request) {
		return userService.userInfo(request);
	}
}
