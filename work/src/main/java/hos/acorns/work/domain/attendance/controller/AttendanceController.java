package hos.acorns.work.domain.attendance.controller;

import java.net.UnknownHostException;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hos.acorns.work.domain.attendance.dto.AttendanceInDTO;
import hos.acorns.work.domain.attendance.dto.AttendanceListDTO;
import hos.acorns.work.domain.attendance.service.AttendanceService;
import hos.acorns.work.domain.dayOff.controller.DayOffController;
import hos.acorns.work.domain.dayOff.dto.DayOffCreateDTO;
import hos.acorns.work.domain.dayOff.service.DayOffService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/attendance")
@Tag(name = "attendance", description = "attendance Sever APIs")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AttendanceController {
	
	private final AttendanceService attendanceService;
	
	// 출근확인
	@ApiOperation(value=" 출근 "
            , notes="<h2>✓ required</h2>  <h1> userIdx </h1>")
	@GetMapping(value = "/in/check")
	public Map<String, Object> attendanceInCheck(HttpServletRequest request) {
		return attendanceService.attendanceInCheck(request);
	}
	
	// 출근퇴근
	@ApiOperation(value=" 출근퇴근 "
            , notes="<h2>✓ required</h2>  <h1> userIdx </h1>")
	@PostMapping(value = "/in")
	public Map<String, Object> attendanceInOut(@RequestBody AttendanceInDTO attendanceInDTO, HttpServletRequest request){
		return attendanceService.attendanceIn(request, attendanceInDTO);
	}

	// 출퇴근리스트
	@ApiOperation(value=" 출근퇴근리스트 "
            , notes="<h2>✓ required</h2>  <h1> userIdx, position </h1>")
	@GetMapping(value = "/list")
	public Map<String, Object> attendanceList(@ModelAttribute AttendanceListDTO attendanceListDTO,
												@RequestParam int size, @RequestParam int page) {
		// 페이지 설정 (들어온페이지, 총 보여줄개수);
		Pageable customPageable = PageRequest.of(page-1, size);
		return attendanceService.attendanceList(attendanceListDTO, customPageable);
	}
	
	// 출퇴근리스트엑셀출력
	@ApiOperation(value=" 출근퇴근리스트엑셀출력 "
            , notes="<h2>✓ required</h2>  <h1>  </h1>")
	@GetMapping(value = "/list/excel")
	public void attendanceListExcel(HttpServletResponse response) throws Exception {
		attendanceService.attendanceListExcel(response);
	}
}
