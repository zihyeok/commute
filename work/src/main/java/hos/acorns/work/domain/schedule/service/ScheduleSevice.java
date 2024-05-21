package hos.acorns.work.domain.schedule.service;

import hos.acorns.work.domain.board.dto.BoardRequestDTO;
import hos.acorns.work.domain.board.entity.BoardEntity;
import hos.acorns.work.domain.schedule.dto.ScheduleRequestDTO;
import hos.acorns.work.domain.schedule.entity.ScheduleEntity;
import hos.acorns.work.domain.schedule.repository.ScheduleRepository;
import hos.acorns.work.global.security.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleSevice {

    private final ScheduleRepository scheduleRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public Map<String, Object> Upload(ScheduleRequestDTO dto, HttpServletRequest request) {

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

        if (dto == null) {
            obj.put("res", "FAIL");
            obj.put("msg", "목록이 비어 있습니다.");
            return obj;
        }

        ScheduleEntity scheduleEntity = scheduleRepository.save(dto.toScheduleEntity());
        obj.put("list", scheduleEntity);
        obj.put("res", "OK");
        obj.put("msg", "성공적으로 저장되었습니다.");

        return obj;
    }

    /*@Transactional
    public Map<String, Object> Article(long idx, HttpServletRequest request) {

        Map<String, Object> obj = new HashMap<>();

        // Token 만료확인
        String token = jwtProvider.resolveToken(request);
        if (!jwtProvider.validateToken(token)) {
            obj.put("res", "FAIL");
            obj.put("msg", "로그인 정보가 없거나 만료되었습니다. 다시 로그인해주세요.");
            return obj;
        }

        // idx 값이 유효한지 검사
        if (idx <= 0) {
            // 실패: 잘못된 idx 값
            obj.put("res", "FAIL");
            obj.put("msg", "잘못된 idx 값입니다.");
            return obj;
        }

        Optional<ScheduleEntity> scheduleEntity = scheduleRepository.findById(idx);

        //목록이 비어 있지 않다면
        if (scheduleEntity.isPresent()) {
            // 성공
            obj.put("res", "OK");
            //obj.put("msg", "목록를 성공적으로 가져왔습니다.");

            ScheduleEntity entity = scheduleEntity.get(); // Optional에서 값 추출
            obj.put("idx", entity.getId());
            obj.put("content", entity.getTitle());
            obj.put("startdt", entity.getStart());
            obj.put("enddt", entity.getEnd());
            obj.put("regdt", entity.getRegDt());
            obj.put("udtdt", entity.getUdtDt());
            obj.put("udtactive", entity.getUdtActive());

        } else {
            // 실패
            obj.put("res", "FAIL");
            obj.put("msg", "게시글이 없습니다.");
        }
        return obj;
    }*/

    @Transactional
    public Map<String, Object> List(HttpServletRequest request) {

        Map<String, Object> obj = new HashMap<>();
        // Token 만료확인
        String token = jwtProvider.resolveToken(request);
        if (!jwtProvider.validateToken(token)) {
            obj.put("res", "FAIL");
            obj.put("msg", "로그인 정보가 없거나 만료되었습니다. 다시 로그인해주세요.");
            return obj;
        }


        List<ScheduleEntity> scheduleEntity = scheduleRepository.findAlls();

        //isEmpty는 없으면 true 있으면 false
        if (scheduleEntity.isEmpty()) {
            obj.put("res", "FAIL");
            obj.put("msg", "목록이 비어 있습니다.");
        }

        // 성공
        obj.put("res", "OK");
        obj.put("msg", "목록를 성공적으로 가져왔습니다.");
        obj.put("list", scheduleEntity);
        return obj;
    }

    @Transactional
    public Map<String, Object> Update(ScheduleRequestDTO dto, HttpServletRequest request) {

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

        if (dto == null) {
            obj.put("res", "FAIL");
            obj.put("msg", "목록이 비어 있습니다.");
        }

        Optional<ScheduleEntity> scheduleEntity = scheduleRepository.findById(dto.getId());
        ScheduleEntity schedule = scheduleEntity.get();

        schedule.setTitle(dto.getTitle());
        schedule.setStart(LocalDate.parse(dto.getStart()));
        schedule.setEnd(LocalDate.parse(dto.getEnd()));
        schedule.setUdtDt(LocalDate.now());
        schedule.setUdtActive(dto.getUdtActive());
        ScheduleEntity updatedEntity = scheduleRepository.save(schedule);

        obj.put("list", updatedEntity);
        obj.put("res", "OK");
        obj.put("msg", "성공적으로 수정되었습니다.");


        return obj;
    }

    @Transactional
    public Map<String, Object> Delete(ScheduleRequestDTO dto, HttpServletRequest request) {

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

        if (dto == null) {
            obj.put("res", "FAIL");
            obj.put("msg", "목록이 비어 있습니다.");
        }

        Optional<ScheduleEntity> scheduleEntity = scheduleRepository.findById(dto.getId());
        ScheduleEntity schedule = scheduleEntity.get();


        schedule.setUdtDt(LocalDate.now());
        schedule.setUdtActive(dto.getUdtActive());
        ScheduleEntity updatedEntity = scheduleRepository.save(schedule);

        obj.put("list", updatedEntity);
        obj.put("res", "OK");
        obj.put("msg", "성공적으로 삭제되었습니다.");

        return obj;
    }

    /*@Transactional
    public Map<String, Object> Renewal(String day) {

        Map<String, Object> obj = new HashMap<>();
        List<ScheduleEntity> scheduleEntity = scheduleRepository.findAll();

        //목록이 비어 있지 않다면
        if (!scheduleEntity.isEmpty()) {

            try {

            *//*active 값을 하루에 한번씩 보내는 함수를 써서 보내 값을 바꿔준다

            시작일이 오늘보다 적으면 다 없데이트
            이런식으로 하나 만들어줘야 할듯 #새로고침*//*


            //오늘날짜 yyyy-MM-dd로 생성
            String todayfm = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));

            //yyyy-MM-dd 포맷 설정
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            //today를데이터 포맷으로 변경
            Date today = new Date(dateFormat.parse(todayfm).getTime());
            Date date = new Date(dateFormat.parse(String.valueOf(day)).getTime());
            //System.out.println(date+"+++++++++"+today);

            //compareTo메서드를 통한 날짜비교
            int compare = date.compareTo(today);

            //조건문
            if(compare > 0) {
                System.out.println("date가 today보다 큽니다.(date > today)");
            }else if(compare < 0) {
                System.out.println("today가 date보다 큽니다.(date < today)");
                //scheduleEntity.get()
            }else {
                System.out.println("today와 date가 같습니다.(date = today)");
            }


            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            // 성공
            obj.put("res", "OK");
            //obj.put("msg", "목록를 성공적으로 가져왔습니다.");
            obj.put("list", scheduleEntity);

        } else {
            // 실패
            obj.put("res", "FAIL");
            obj.put("msg", "목록이 비어 있습니다.");
        }
        return obj;
    }*/


}
