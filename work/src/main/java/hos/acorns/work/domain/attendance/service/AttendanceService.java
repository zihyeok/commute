package hos.acorns.work.domain.attendance.service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hos.acorns.work.domain.attendance.dto.AttendanceInCheckProjection;
import hos.acorns.work.domain.attendance.dto.AttendanceInDTO;
import hos.acorns.work.domain.attendance.dto.AttendanceListDTO;
import hos.acorns.work.domain.attendance.dto.AttendanceListProjection;
import hos.acorns.work.domain.attendance.entity.AttendanceEntity;
import hos.acorns.work.domain.attendance.repository.AttendanceRepository;
import hos.acorns.work.domain.commons.entity.ReferenceEntity;
import hos.acorns.work.domain.commons.repository.ReferenceRepository;
import hos.acorns.work.global.security.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceService {
	
	private final AttendanceRepository attendanceRepository;
	private final ReferenceRepository referenceRepository;
	private final JwtProvider jwtProvider;
	
	// 출근확인
	@Transactional(readOnly = true)
	public Map<String, Object> attendanceInCheck(HttpServletRequest request) {
		
		// return
		Map<String, Object> obj = new HashMap<>();

		// Token 만료확인
		Long userIdx = null;
//		int position = 0;
		String token = jwtProvider.resolveToken(request);
		if(!jwtProvider.validateToken(token)) {
			obj.put("res", "FAIL");
			obj.put("msg", "로그인 정보가 없거나 만료되었습니다. 다시 로그인해주세요.");
            return obj;
		}else {
			userIdx = Long.parseLong(jwtProvider.getUserIdx(token));
//			position = Integer.parseInt(jwtProvider.getPosition(token));
		}
		
		AttendanceInCheckProjection attendanceInCheck = attendanceRepository.findByUserIdx(userIdx);
		
		if(attendanceInCheck.getCount() == 0) {
			obj.put("res", "OK");
			obj.put("msg", "출근조회완료. 결과없음");
			obj.put("data", attendanceInCheck.getCount());
			return obj;
		}else {
			obj.put("res", "OK");
			obj.put("msg", "출근조회 완료");
			obj.put("data", attendanceInCheck.getCount());
			return obj;
		}
	}

	// 출퇴근
	@Transactional
	public Map<String, Object> attendanceIn(HttpServletRequest request, AttendanceInDTO attendanceInDTO){

		// return
		Map<String, Object> obj = new HashMap<>();
		
//		// ip 검증
//		// ip 가져오기
		String ip = request.getHeader("X-Forwarded-For");
	    
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("HTTP_CLIENT_IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("X-Real-IP"); 
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("X-RealIP"); 
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("REMOTE_ADDR");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getRemoteAddr(); 
	    }
	    
		// ip 확인
		Optional<ReferenceEntity> referenceEntity = referenceRepository.findByAllowIp(ip);
		if(!referenceEntity.isPresent()) {
			// 기록 없으면 반환
			obj.put("res", "FAIL");
			obj.put("msg", "허용 IP가 아닙니다.");
			return obj;
		}
		
		// 출근 조회
//		Optional<AttendanceEntity> attendanceEntity = attendanceRepository.findByUserIdx(attendanceInDTO.getIdx());
		
//		if(!attendanceEntity.isPresent()) {
//			// 기록 없으면 출근
//			AttendanceEntity attendanceInEntity = attendanceRepository.save(attendanceInDTO.toAttendanceEntity());
//			
//			if(attendanceInEntity == null) {
//				obj.put("res", "FAIL");
//				obj.put("msg", "출근이 실패되었습니다. 다시 시도해주세요.");
//				return obj;
//			}else {				
//				obj.put("res", "OK");
//				obj.put("msg", "출근이 완료되었습니다.");
//				return obj;
//			}
//		}else {
//			// 기록 있으면 퇴근으로 업데이트
//			AttendanceEntity entity = attendanceEntity.get();
//				entity.setWorkOut(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//			AttendanceEntity attendanceOutEntity = attendanceRepository.save(entity);
//			
//			if(attendanceOutEntity == null) {
//				obj.put("res", "FAIL");
//				obj.put("msg", "퇴근이 실패되었습니다. 다시 시도해주세요.");
//				return obj;
//			}else {				
//				obj.put("res", "OK");
//				obj.put("msg", "퇴근이 완료되었습니다.");
//				return obj;
//			}
//		}
		return null;
		
	}

	// 출퇴근리스트
	@Transactional(readOnly = true)
	public Map<String, Object> attendanceList(AttendanceListDTO attendanceListDTO, Pageable pageable) {
		
		// return
		Map<String, Object> obj = new HashMap<>();
		Page<AttendanceListProjection> attendanceListEntity = null;
		
//		// 정렬 조건 추가
//	    Sort sort = Sort.by(
//	    		Sort.Order.desc("workOut"),
//	            Sort.Order.desc("workIn")
//	    );
//	    // 정렬 조건 추가
//	    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

		if(attendanceListDTO.getPosition() == 4) {
			// 관리자는 리스트 다 가져오기
			attendanceListEntity = attendanceRepository.findAllList(pageable);
		}else {
			// 직원일경우 본인의 출퇴근 리스트만 가져오기
			attendanceListEntity = attendanceRepository.findByUserIdx(attendanceListDTO.getUserIdx(), pageable);
		}
		
		//내역 불러오기 실패시
		if(attendanceListEntity == null) {
			obj.put("res","FAIL");
			obj.put("msg", "출퇴근 리스트를 불러오는데 실패하였습니다.");
			return obj;
		}
		
		obj.put("res", "OK");
		obj.put("msg", "출퇴근리스트를 불러왔습니다.");
		obj.put("list", attendanceListEntity);
		return obj;
	}
	
	// 출퇴근리스트엑셀출력
	public void attendanceListExcel(HttpServletResponse response) throws Exception {

		// 1. workbook 을 생성한다.
	    // 2. workbook 내에 sheet를 생성한다.
	    // 3. sheet 내에 row를 생성한다.
	    // 4. 하나의 row에 여러개의 cell을 생성한다. (= 하나의 행에 여러 열을 생성한다)
		
		// 출퇴근리스트조회
		List<AttendanceListProjection> list = attendanceRepository.findAllList();

		// 1. 워크북(엑셀파일) 생성
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		// 2. sheet 생성
		XSSFSheet sheet = workbook.createSheet("Attendance List");
		
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 8000);
		sheet.setColumnWidth(5, 8000);
		
		// 3. row 생성
		Row row = null;
		Cell cell = null;
		int rowNo = 0;
		
		// 스타일 객체 생성 및 적용
		XSSFCellStyle headStyle = workbook.createCellStyle();
		// 가는 경계선
		headStyle.setBorderTop(BorderStyle.THIN);
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setBorderRight(BorderStyle.THIN);
		// 배경색은 회색
		headStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		// 데이터 가운데 정렬
		headStyle.setAlignment(HorizontalAlignment.CENTER);
		// 데이터 경계 스타일 테두리만 지정
		CellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		bodyStyle.setBorderRight(BorderStyle.THIN);
		
		// 4. 하나의 row에 여러개의 cell을 생성
		row = sheet.createRow(rowNo++);
		cell = row.createCell(0);
		cell.setCellStyle(headStyle);
		cell.setCellValue("번호");
		cell = row.createCell(1);
		cell.setCellStyle(headStyle);
		cell.setCellValue("아이디");
		cell = row.createCell(2);
		cell.setCellStyle(headStyle);
		cell.setCellValue("이름");
		cell = row.createCell(3);
		cell.setCellStyle(headStyle);
		cell.setCellValue("날짜");
		cell = row.createCell(4);
		cell.setCellStyle(headStyle);
		cell.setCellValue("출근시간");
		cell = row.createCell(5);
		cell.setCellStyle(headStyle);
		cell.setCellValue("퇴근시간");
		
		// 데이터 부분 생성
		for(AttendanceListProjection item : list) {
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);
			cell.setCellStyle(headStyle);
			cell.setCellValue(item.getIdx());
			cell = row.createCell(1);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(item.getLgnId());
			cell = row.createCell(2);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(item.getLgnName());
			cell = row.createCell(3);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(item.getRegDt());
			cell = row.createCell(4);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(item.getWorkIn());
			cell = row.createCell(5);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(item.getWorkOut());
		}
		
		// excel 파일 저장
		String desktopPath = System.getProperty("user.home") + "\\Downloads\\";
		LocalDateTime currentDateTime = LocalDateTime.now();
		String date = currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
		
		File xlsFile = new File(desktopPath + "workList" + date + ".xlsx");
        
		try (FileOutputStream fileOut = new FileOutputStream(xlsFile)) {
             workbook.write(fileOut);
        }

	    // 타입 설정
	    response.setHeader("Content-Disposition", "attachment; filename=workList" + date + ".xlsx");
		response.setContentType("application/vnd.ms-excel");
		
	    // 파일 전송
	    workbook.write(response.getOutputStream());
	    workbook.close();
	}
}