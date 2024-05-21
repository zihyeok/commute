package hos.acorns.work.domain.reply.service;


import hos.acorns.work.domain.reply.dto.ReplyRequestDTO;
import hos.acorns.work.domain.reply.entity.ReplyEntity;
import hos.acorns.work.domain.reply.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public Map<String, Object> Crate(ReplyRequestDTO dto) {

        Map<String, Object> obj = new HashMap<>();

        if (dto != null) {
            ReplyEntity ReplyEntity = replyRepository.save(dto.toReplyEntity());
            obj.put("list", ReplyEntity);
            // 성공
            obj.put("res", "OK");
            obj.put("msg", "성공적으로 저장되었습니다.");
        } else {
            // 실패
            obj.put("res", "FAIL");
            obj.put("msg", "저장에 실패했습니다.");
        }
        return obj;
    }

    @Transactional
    public Map<String, Object> Delete(ReplyRequestDTO dto) {


        Map<String, Object> obj = new HashMap<>();

        //목록이 비어 있지 않다면
        if (dto != null) {
            Optional<ReplyEntity> replyEntity = replyRepository.findById(dto.getIdx());

            ReplyEntity reply = replyEntity.get();
            String storedPassword = reply.getReplyPw(); // 게시글에 저장된 비밀번호

            // 입력한 비밀번호와 저장된 비밀번호 비교
            if (storedPassword.equals(dto.getReplyPw())) {
                // 비밀번호가 일치하는 경우 삭제 진행
                reply.setState(dto.getState());
                replyRepository.save(reply);

                obj.put("res", "OK");
                obj.put("msg", "성공적으로 삭제되었습니다.");
            } else {
                // 비밀번호가 일치하지 않는 경우
                obj.put("res", "FAIL");
                obj.put("msg", "비밀번호가 일치하지 않습니다.");
            }
        } else {
            obj.put("res", "FAIL");
            obj.put("msg", "해당 게시글을 찾을 수 없습니다.");
        }
        return obj;
    }

    /*
    @Transactional
    public Map<String, Object> List(Pageable pageable, Long boardIdx, ReplyRequestDTO dto) {
        Map<String, Object> obj = new HashMap<>();

        //index 비어 있지 않다면
        if (boardIdx != null) {
            Page<ReplyEntity> replyEntity  = replyRepository.findByReplyAndBoard(pageable, boardIdx, dto);

            // 성공
            obj.put("res", "OK");
            //obj.put("msg", "목록를 성공적으로 가져왔습니다.");
            obj.put("list", replyEntity);

        } else {
            // 실패
            obj.put("res", "FAIL");
            obj.put("msg", "목록이 비어 있습니다.");
        }
        return obj;
    }
    */
    @Transactional
    public Map<String, Object> List(Pageable pageable, Long boardIdx) {
        Map<String, Object> obj = new HashMap<>();

        //index 비어 있지 않다면
        if (boardIdx != null) {
            Page<ReplyEntity> replyEntity  = replyRepository.findByReplyAndBoard(pageable, boardIdx);

            // 성공
            obj.put("res", "OK");
            //obj.put("msg", "목록를 성공적으로 가져왔습니다.");
            obj.put("list", replyEntity);

        } else {
            // 실패
            obj.put("res", "FAIL");
            obj.put("msg", "목록이 비어 있습니다.");
        }
        return obj;
    }

}
