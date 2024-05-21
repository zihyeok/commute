package hos.acorns.work.domain.reply.controller;


import hos.acorns.work.domain.reply.dto.ReplyRequestDTO;
import hos.acorns.work.domain.reply.dto.ReplyResponseDTO;
import hos.acorns.work.domain.reply.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/reply")
@Tag(name = "Reply", description = "댓글 APIs")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ReplyResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "댓글 등록 API", description = "필요한 값 : content, refIdx, replyPw, state")
    @PostMapping(value = "/create")
    public Map<String, Object> ReplyCreate(@RequestBody ReplyRequestDTO dto) {

        return replyService.Crate(dto);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ReplyResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "댓글 삭제 API", description = "필요한 값 : idx, replyPw, state")
    @PostMapping(value = "/delete")
    public Map<String, Object> ReplyDelete(@RequestBody ReplyRequestDTO dto) {

        return replyService.Delete(dto);
    }

    /*@ApiOperation(value=" 댓글리스트 TEST"
            , notes="<h2>✓ Test </h2>  <h1> (Test : 테스트)) </h1>")
    @GetMapping(value = "/list Test")
    public Map<String, Object> ReplyList(@Parameter Pageable pageable, @RequestParam Long boardIdx,@RequestBody ReplyRequestDTO dto) {

        return replyService.List(pageable, boardIdx, dto);
    }*/

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ReplyResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "댓글 리스트 API", description = "필요한 값 : ")
    @GetMapping(value = "/list")
    public Map<String, Object> ReplyList(@Parameter Pageable pageable, @RequestParam Long boardIdx) {

        return replyService.List(pageable, boardIdx);
    }


}
