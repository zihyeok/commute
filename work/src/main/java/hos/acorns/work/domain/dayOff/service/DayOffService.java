package hos.acorns.work.domain.dayOff.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import hos.acorns.work.domain.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import hos.acorns.work.domain.attendance.entity.AttendanceEntity;
import hos.acorns.work.domain.dayOff.dto.DayOffAlarmListProjection;
import hos.acorns.work.domain.dayOff.dto.DayOffCreateDTO;
import hos.acorns.work.domain.dayOff.dto.DayOffDetailDTO;
import hos.acorns.work.domain.dayOff.dto.DayOffListDTO;
import hos.acorns.work.domain.dayOff.entity.DayOffEntity;
import hos.acorns.work.domain.dayOff.repository.DayOffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DayOffService {

	private final DayOffRepository dayOffRepository;

	// 연차신청 및 수정
	@Transactional
	public Map<String, Object> dayOffCreate(DayOffCreateDTO dayOffCreateInfo) {

		// return
		Map<String, Object> obj = new HashMap<>();

		if(dayOffCreateInfo.getStartDt() == null) {
			// 신청날짜 미입력 return
			obj.put("res", "FAIL");
			obj.put("msg", "신청 날짜를 클릭해주세요.");
			return obj;
		}

		// 연차사용 카테고리마다 연차사용개수 데이터 가공
		if(dayOffCreateInfo.getCategory() == 1) {
			dayOffCreateInfo.setDayoffCount(1);
		}else if(dayOffCreateInfo.getCategory() == 2) {
			dayOffCreateInfo.setDayoffCount((float)0.5);
		}else if(dayOffCreateInfo.getCategory() == 3){
			dayOffCreateInfo.setDayoffCount(0);
		}

		if(dayOffCreateInfo.getIdx() == 0) {
			// 해당 연차인덱스 없으면 insert
			// 연차신청개수에 따라 데이터개별 가공
			String[] date = dayOffCreateInfo.getStartDt().split(",");
			for(String item : date) {
				dayOffCreateInfo.setStartDt(item);
				DayOffEntity dayOffEntity = dayOffRepository.save(dayOffCreateInfo.toDayOffEntity());
				if(dayOffEntity == null) {
					// 연차신청 실패
					obj.put("res", "FAIL");
					obj.put("msg", "연차신청에 실패하셨습니다. 다시 시도해주세요.");
					return obj;
				}
			}
		}else {
			// 해당 연차인덱스 있으면 update
			// 연차 조회
			Optional<DayOffEntity> optionalDayOffEntity = dayOffRepository.findById(dayOffCreateInfo.getIdx());

			if(optionalDayOffEntity.isPresent()) {
				// 연차 날짜 2일이상 고르면 return
				String[] date = dayOffCreateInfo.getStartDt().split(",");

				if(date.length > 1) {
					obj.put("res", "FAIL");
					obj.put("msg", "수정은 1일만 고를 수 있습니다.");
					return obj;
				}

				DayOffEntity entity = optionalDayOffEntity.get();
					entity.setCategory(dayOffCreateInfo.getCategory());
					entity.setStartDt(dayOffCreateInfo.getStartDt());
					entity.setContent(dayOffCreateInfo.getContent());
					entity.setDayoffCount(dayOffCreateInfo.getDayoffCount());
					entity.setUdtDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				DayOffEntity dayOffUpdateEntity = dayOffRepository.save(entity);

				if(dayOffUpdateEntity == null) {
					// 연차 수정중 실패
					obj.put("res", "FAIL");
					obj.put("msg", "연차수정중 실패하셨습니다. 다시 시도해주세요.");
					return obj;
				}else {
					// 연차 수정 완료
					obj.put("res", "OK");
					obj.put("msg", "연차수정을 완료하셨습니다.");
					return obj;
				}
			}
		}
		// 연차신청 완료
		obj.put("res", "OK");
		obj.put("msg", "연차신청이 완료되었습니다.");
		return obj;
	}

	// 연차리스트
	@Transactional
	public Map<String, Object> dayOffList(Long userIdx, int active, int position, Pageable pageable) {

		// return
		Map<String, Object> obj = new HashMap<>();
		Page<DayOffAlarmListProjection> dayOffListEntity = null;

		if(position == 4) {
			// 관리자일시
			if(active == 4) {
				// 모든 연차 리스트
				dayOffListEntity = dayOffRepository.findAllById(pageable);
			}else {
				// 검색리스트
				dayOffListEntity = dayOffRepository.findAllByIdAndActive(active, pageable);
			}
		}else {
			// 직원연차리스트
			if(active == 4) {
				// 전체리스트
				dayOffListEntity = dayOffRepository.findAllBySearchUserIdx(userIdx, pageable);

				// 연차확인(user_off_check)후 update
				int active1 = 2;
				int active2 = 3;
				int userOffCheck = 0;
				List<DayOffEntity> dayOffEntity = dayOffRepository.findByUserIdxActiveAndUserOffCheck(userIdx, active1, active2, userOffCheck);
				if (!dayOffEntity.isEmpty()) {
				    // 리스트가 있으면 유저확인으로 업데이트
				    for(DayOffEntity item : dayOffEntity) {
				    	item.setUserOffCheck(1);
				    	dayOffRepository.save(item);
				    }
				}
			}else {
				// 검색리스트
				dayOffListEntity = dayOffRepository.findAllBySearchUserIdxAndActive(userIdx, active, pageable);
			}
		}

		if(dayOffListEntity == null) {
			obj.put("res", "FAIL");
			obj.put("msg", "연차내역을 불러오는데 실패하였습니다.");
			return obj;
		}else {
			obj.put("res", "OK");
			obj.put("msg", "연차내역을 불러왔습니다.");
			obj.put("list", dayOffListEntity);
			return obj;
		}
	}

	// 연차상세조회
	public Map<String, Object> dayOffDetail(Long idx) {

		// return
		Map<String, Object> obj = new HashMap<>();

		Optional<DayOffEntity> optionalDayOffDetailEntity = dayOffRepository.findById(idx);
		if(optionalDayOffDetailEntity.isPresent()) {
			// 정보가 있으면
			// entity -> dto 변환
			DayOffEntity dayOffEntity = optionalDayOffDetailEntity.get();
			DayOffDetailDTO dto = new DayOffDetailDTO(dayOffEntity);

			obj.put("res", "OK");
			obj.put("list", dto);
			return obj;
		}else {
			// 정보가 없으면 (불러오기 실패)
			obj.put("res", "FAIL");
			obj.put("msg", "상세내역을 불러오는데 실패하였습니다. 다시 시도해주세요.");
			return obj;
		}
	}

	// 연차알람리스트
	@Transactional(readOnly = true)
	public Map<String, Object> dayOffAlarmList(Long idx, int position) {

		// return
		Map<String, Object> obj = new HashMap<>();
		List<DayOffAlarmListProjection> dayOffEntity = null;

		if(position == 4) {
			// 관리자일떄: active 1일이면서 off_check 0인 리스트
			int active = 1;
			int offCheck = 0;
			dayOffEntity = dayOffRepository.findByActiveAndOffCheck(active, offCheck);
		}else {
			// 직원일때: active가 2,3(승인 거절)이면서 user_check 0인 리스트
			int active1 = 2;
			int active2 = 3;
			int userOffCheck = 0;
			dayOffEntity = dayOffRepository.findByActiveAndUserOffCheck(idx, active1, active2, userOffCheck);
		}

		if(dayOffEntity == null) {
		    obj.put("res", "FAIL");
		    obj.put("msg", "연차알람을 불러오는데 실패하였습니다.");
		    return obj;
		}

		obj.put("res", "OK");
		obj.put("msg", "연차알람 내역을 불러왔습니다.");
		obj.put("list", dayOffEntity);
		return obj;
	}

	// 연차알람확인
	@Transactional
	public Map<String, Object> dayOffAlarmCheck(Long dayOffIdx, int position) {

		// return
		Map<String, Object> obj = new HashMap<>();


		if(position == 4) {
			//관리자의 경우 모든 알람 체크
			int offCheck = 0;
			List<DayOffEntity> DayOffAlarmCheckEntity = dayOffRepository.findByOffCheck(offCheck);

			for(DayOffEntity  item : DayOffAlarmCheckEntity) {
					item.setOffCheck(1);
				DayOffEntity dayOffAlarmCheckEntity = dayOffRepository.save(item);

				if(dayOffAlarmCheckEntity == null) {
					obj.put("res", "FAIL");
					obj.put("msg", "알람 확인을 실패하였습니다. 다시 시도해주세요.");
					return obj;
				}
			}
		}else {
			// 직원일경우 해당 알람만 체크
			// 해당 연차알람 조회
			Optional<DayOffEntity> optionalDayOffAlarmCheckEntity = dayOffRepository.findById(dayOffIdx);

			if(optionalDayOffAlarmCheckEntity.isPresent()) {
				// 해당 연차알람 user_off_check update
				DayOffEntity entity = optionalDayOffAlarmCheckEntity.get();
					entity.setUserOffCheck(1);
				DayOffEntity dayOffAlarmCheckEntity = dayOffRepository.save(entity);

				if(dayOffAlarmCheckEntity == null) {
					obj.put("res", "FAIL");
					obj.put("msg", "알람 확인을 실패하였습니다. 다시 시도해주세요.");
					return obj;
				}
			}
		}
		obj.put("res", "OK");
		obj.put("msg", "알람확인이 완료되었습니다.");
		return obj;
	}

	// 연차승인거절리스트
	public Map<String, Object> dayOffCheckList(Pageable customPageable) {

		// return
		Map<String, Object> obj = new HashMap<>();

		// 신청 연차만 가져오기
		int active = 1;
		Page<DayOffAlarmListProjection> dayOffListEntity = dayOffRepository.findAllByActiveOrderByIdxDesc(active, customPageable);

		if(dayOffListEntity == null) {
			obj.put("res", "FAIL");
			obj.put("msg", "연차체크내역을 불러오는데 실패하였습니다.");
			return obj;
		}else {
			obj.put("res", "OK");
			obj.put("msg", "연차내역을 불러왔습니다.");
			obj.put("list", dayOffListEntity);
			return obj;
		}
	}

	// 연차승인
	public Map<String, Object> dayOffApproval(Long idx) {

		// return
		Map<String, Object> obj = new HashMap<>();

		// 연차 조회
		Optional<DayOffEntity> optionalDayOffEntity = dayOffRepository.findById(idx);

		if(optionalDayOffEntity.isPresent()) {
			// 연차내역 있으면
			DayOffEntity entity = optionalDayOffEntity.get();
				entity.setActive(2);
				entity.setOffCheck(1);
			DayOffEntity dayOffUpdateEntity = dayOffRepository.save(entity);

			if(dayOffUpdateEntity == null) {
				// 연차 승인중 실패
				obj.put("res", "FAIL");
				obj.put("msg", "연차승인중 실패하셨습니다. 다시 시도해주세요.");
				return obj;
			}else {
				// 연차 승인 완료
				obj.put("res", "OK");
				obj.put("msg", "연차승인을 완료하셨습니다.");
				return obj;
			}
		}
		return obj;
	}

	// 연차거절
	public Map<String, Object> dayOffRefuse(Long idx) {

		// return
		Map<String, Object> obj = new HashMap<>();

		// 연차 조회
		Optional<DayOffEntity> optionalDayOffEntity = dayOffRepository.findById(idx);

		if(optionalDayOffEntity.isPresent()) {
			// 연차내역 있으면
			DayOffEntity entity = optionalDayOffEntity.get();
				entity.setActive(3);
				entity.setOffCheck(1);
			DayOffEntity dayOffUpdateEntity = dayOffRepository.save(entity);

			if(dayOffUpdateEntity == null) {
				// 연차 거절중 실패
				obj.put("res", "FAIL");
				obj.put("msg", "연차거절중 실패하셨습니다. 다시 시도해주세요.");
				return obj;
			}else {
				// 연차 거절 완료
				obj.put("res", "OK");
				obj.put("msg", "연차가 거절되었습니다.");
				return obj;
			}
		}
		return obj;
	}

	// 연차취소
	public Map<String, Object> dayOffCancel(Long idx) {
		
		// return
		Map<String, Object> obj = new HashMap<>();
		
		// 연차 조회
		Optional<DayOffEntity> optionalDayOffEntity = dayOffRepository.findById(idx);
		
		if(optionalDayOffEntity.isPresent()) {
			// 연차내역 있으면
			DayOffEntity entity = optionalDayOffEntity.get();
				entity.setActive(0);
			DayOffEntity dayOffUpdateEntity = dayOffRepository.save(entity);
			
			if(dayOffUpdateEntity == null) {
				// 연차 거절중 실패
				obj.put("res", "FAIL");
				obj.put("msg", "연차취소가 실패되었습니다. 다시 시도해주세요.");
				return obj;
			}else {
				// 연차 거절 완료
				obj.put("res", "OK");
				obj.put("msg", "연차가 취소되었습니다.");
				return obj;
			}
		}
		return obj;
	}

}
