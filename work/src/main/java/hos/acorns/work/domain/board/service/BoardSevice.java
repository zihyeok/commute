package hos.acorns.work.domain.board.service;

import hos.acorns.work.domain.board.dto.BoardRequestDTO;
import hos.acorns.work.domain.board.entity.BoardEntity;
import hos.acorns.work.domain.board.repository.BoardRepository;
import hos.acorns.work.global.security.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardSevice {

    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    //게시판 생성
    @Transactional
    public Map<String, Object> Crate(BoardRequestDTO dto, HttpServletRequest request) {
        // return
        Map<String, Object> obj = new HashMap<>();

        // Token 만료확인
        String token = jwtProvider.resolveToken(request);
        if(!jwtProvider.validateToken(token)) {
            obj.put("res", "FAIL");
            obj.put("msg", "로그인 정보가 없거나 만료되었습니다. 다시 로그인해주세요.");
            return obj;
        }

        if (dto == null) {
            obj.put("res", "FAIL");
            obj.put("msg", "목록이 비어있습니다.");
            return obj;
        }

        // 비밀번호 암호화
        String encPw = passwordEncoder.encode(dto.getBoardPw());
        dto.setBoardPw(encPw);

        BoardEntity boardEntity = boardRepository.save(dto.toBoardEntity());
        obj.put("list", boardEntity);
        obj.put("res", "OK");
        obj.put("msg", "성공적으로 저장되었습니다.");


        return obj;
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

        Optional<BoardEntity> boardEntity = boardRepository.findById(idx);

        //목록이 비어 있지 않다면
        if (boardEntity.isPresent()) {
            BoardEntity entity = boardEntity.get();

            // 조회수 증가 로직 추가
            entity.setView(entity.getView() + 1); // 현재 조회수를 가져와 1을 더함
            boardRepository.save(entity); // 변경된 조회수를 데이터베이스에 저장

            // 성공
            obj.put("res", "OK");
            //obj.put("msg", "목록를 성공적으로 가져왔습니다.");
            obj.put("idx", entity.getIdx());
            obj.put("title", entity.getTitle());
            obj.put("content", entity.getContent());
            obj.put("view", entity.getView());
            obj.put("regdt", entity.getRegDt());
            obj.put("udtdt", entity.getUdtDt());

        } else {
            // 실패
            obj.put("res", "FAIL");
            obj.put("msg", "게시글이 없습니다.");
        }
        return obj;
    }*/

    /* list에 idx 추가
    @Transactional
    public Map<String, Object> List(Pageable pageable, Long boardIdx) {
        Page<BoardEntity> boardEntity = boardRepository.findAllByList(boardIdx, pageable);
        Map<String, Object> obj = new HashMap<>();

        //목록이 비어 있지 않다면
        if (!boardEntity.isEmpty()) {
            // 성공
            obj.put("list", boardEntity);
            obj.put("res", "OK");
            //obj.put("msg", "목록를 성공적으로 가져왔습니다.");

        } else {
            // 실패
            obj.put("res", "FAIL");
            obj.put("msg", "목록이 비어 있습니다.");
        }
        return obj;
    }*/

    /* 리스트 쿼리로 페이징
    @Transactional
    public Map<String, Object> List(Pageable pageable) {
        Page<BoardEntity> boardEntity = boardRepository.findAllByList(pageable);
        Map<String, Object> obj = new HashMap<>();

        //목록이 비어 있지 않다면
        if (!boardEntity.isEmpty()) {
            // 성공
            obj.put("list", boardEntity);
            obj.put("res", "OK");
            //obj.put("msg", "목록를 성공적으로 가져왔습니다.");

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

        //Page<BoardEntity> boardEntity = boardRepository.findAllByStateZero(pageable);
        Page<BoardEntity> boardEntity = boardRepository.findAll(pageable);

        //목록이 비어 있지 않다면
        if (!boardEntity.isEmpty()) {
            // 성공
            obj.put("res", "OK");
            obj.put("msg", "목록를 성공적으로 가져왔습니다.");
            obj.put("list", boardEntity);

        } else {
            // 실패
            obj.put("res", "FAIL");
            obj.put("msg", "목록이 비어 있습니다.");
        }
        return obj;
    }

    @Transactional
    public Map<String, Object> Update(BoardRequestDTO dto, HttpServletRequest request) {

        Map<String, Object> obj = new HashMap<>();
        // Token 만료확인
        String token = jwtProvider.resolveToken(request);
        if (!jwtProvider.validateToken(token)) {
            obj.put("res", "FAIL");
            obj.put("msg", "로그인 정보가 없거나 만료되었습니다. 다시 로그인해주세요.");
            return obj;
        }

        if (dto == null) {
            // 입력된 정보가 없을 때
            obj.put("res", "FAIL");
            obj.put("msg", "입력된 정보가 없습니다.");
            return obj;
        }

        Optional<BoardEntity> boardEntity = boardRepository.findById(dto.getIdx());

        //isEmpty는 없으면 true 있으면 false
        if (boardEntity.isEmpty()) {
            obj.put("res", "FAIL");
            obj.put("msg", "게시글을 찾을 수 없습니다.");
            return obj;
        }

        BoardEntity board = boardEntity.get();
        String storedPassword = board.getBoardPw();

        if (!passwordEncoder.matches(dto.getBoardPw(), storedPassword)) {
            // 비밀번호가 일치하지 않을 때
            obj.put("res", "FAIL");
            obj.put("msg", "비밀번호가 일치하지 않습니다.");

        }

        // 비밀번호가 일치할 때 게시글 수정 진행
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setUdtDt(LocalDate.now());

        // 새로운 비밀번호를 암호화하여 저장
        String newEncryptedPassword = passwordEncoder.encode(dto.getBoardPw());
        board.setBoardPw(newEncryptedPassword);
        BoardEntity updatedEntity = boardRepository.save(board);

        obj.put("list", updatedEntity);
        obj.put("res", "OK");
        obj.put("msg", "수정되었습니다.");

        return obj;
    }

   @Transactional
   public Map<String, Object> Delete(BoardRequestDTO dto, HttpServletRequest request) {
       //System.out.println(session +"=============================");
       Map<String, Object> obj = new HashMap<>();

       String token = jwtProvider.resolveToken(request);
       if (!jwtProvider.validateToken(token)) {
           obj.put("res", "FAIL");
           obj.put("msg", "로그인 정보가 없거나 만료되었습니다. 다시 로그인해주세요.");
           return obj;
       }

       if (dto == null) {
           // 입력된 정보가 없을 때
           obj.put("res", "FAIL");
           obj.put("msg", "입력된 정보가 없습니다.");
           return obj;
       }

       Optional<BoardEntity> boardEntity = boardRepository.findById(dto.getIdx());

       //isEmpty는 없으면 true 있으면 false
       if (boardEntity.isEmpty()) {
           // 게시글을 찾을 수 없을 때
           obj.put("res", "FAIL");
           obj.put("msg", "게시글을 찾을 수 없습니다.");

       }

       BoardEntity board = boardEntity.get();
       String storedPassword = board.getBoardPw();

       if (!passwordEncoder.matches(dto.getBoardPw(), storedPassword)) {
           // 비밀번호가 일치하지 않을 때
           obj.put("res", "FAIL");
           obj.put("msg", "비밀번호가 일치하지 않습니다.");
       }

       // 비밀번호가 일치할 때 상태 업데이트
       board.setState(dto.getState());
       board.setUdtDt(LocalDate.now());
       boardRepository.save(board);

       obj.put("res", "OK");
       obj.put("msg", "삭제되었습니다.");
       return obj;
   }


}
