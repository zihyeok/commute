package hos.acorns.work.domain.dayOff.controller;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hos.acorns.work.domain.attendance.dto.AttendanceListDTO;
import hos.acorns.work.domain.dayOff.dto.DayOffCreateDTO;
import hos.acorns.work.domain.dayOff.service.DayOffService;
import hos.acorns.work.domain.user.controller.UserController;
import hos.acorns.work.domain.user.dto.UserRequestDTO;
import hos.acorns.work.domain.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/dayOff")
@Tag(name = "DayOff", description = "DayOff Sever APIs")
@Slf4j
@RestController
@RequiredArgsConstructor
public class DayOffController {

	private final DayOffService dayOffService;

	// 연차신청
	@ApiOperation(value=" 연차신청 "
            , notes="<h2>✓ required</h2>  <h1>  </h1>")
	@PostMapping(value = "/create")
	public Map<String, Object> dayOffCreate(@RequestBody DayOffCreateDTO dayOffCreateInfo) {
		return dayOffService.dayOffCreate(dayOffCreateInfo);
	}

	// 연차리스트
	@ApiOperation(value=" 연차리스트 "
            , notes="<h2>✓ required</h2>  <h1> userIdx </h1>")
	@GetMapping(value = "/list")
	public Map<String, Object> dayOffList(@RequestParam Long idx, @RequestParam int active,
							@RequestParam int position, @RequestParam int size, @RequestParam int page) {
		// 페이지 설정 (들어온페이지, 총 보여줄개수);
		Pageable customPageable = PageRequest.of(page-1, size);
		return dayOffService.dayOffList(idx, active, position, customPageable);
	}



	// 연차상세조회
	@ApiOperation(value=" 연차상세조회 "
            , notes="<h2>✓ required</h2>  <h1> userIdx </h1>")
	@GetMapping(value = "/detail")
	public Map<String, Object> dayOffDetail(@RequestParam Long idx) {
		return dayOffService.dayOffDetail(idx);
	}

	// 연차알람리스트
	@ApiOperation(value=" 연차알람리스트"
            , notes="<h2>✓ required</h2>  <h1> userIdx, position </h1>")
	@GetMapping(value = "/alarm/list")
	public Map<String, Object> dayOffAlarmList(@RequestParam Long idx, @RequestParam int position) {
		return dayOffService.dayOffAlarmList(idx, position);
	}

	// 연차알람확인
	@ApiOperation(value=" 연차알람확인"
            , notes="<h2>✓ required</h2>  <h1> dayOffIdx </h1>")
	@GetMapping(value = "/alarm/check")
	public Map<String, Object> dayOffAlarmCheck(@RequestParam Long dayOffIdx, @RequestParam int position) {
		return dayOffService.dayOffAlarmCheck(dayOffIdx, position);
	}

	// 연차승인거절리스트
	@ApiOperation(value=" 연차승인거절리스트"
            , notes="<h2>✓ required</h2>  <h1>  </h1>")
	@GetMapping(value = "/list/check")
	public Map<String, Object> dayOffCheckList(@RequestParam int size, @RequestParam int page) {
		// 페이지 설정 (들어온페이지, 총 보여줄개수);
		Pageable customPageable = PageRequest.of(page-1, size);
		return dayOffService.dayOffCheckList(customPageable);
	}

	// 연차승인
	@ApiOperation(value=" 연차승인"
            , notes="<h2>✓ required</h2>  <h1> idx </h1>")
	@GetMapping(value = "/approval")
	public Map<String, Object> dayOffApproval(@RequestParam Long idx) {
		return dayOffService.dayOffApproval(idx);
	}

	// 연차거절
	@ApiOperation(value=" 연차거절"
            , notes="<h2>✓ required</h2>  <h1> idx </h1>")
	@GetMapping(value = "/refuse")
	public Map<String, Object> dayOffRefuse(@RequestParam Long idx) {
		return dayOffService.dayOffRefuse(idx);
	}
	
	// 연차취소
	@ApiOperation(value=" 연차취소 "
            , notes="<h2>✓ required</h2>  <h1> idx </h1>")
	@GetMapping(value = "/cancel")
	public Map<String, Object> dayOffCancel(@RequestParam Long idx) {
		return dayOffService.dayOffCancel(idx);
	}
}
