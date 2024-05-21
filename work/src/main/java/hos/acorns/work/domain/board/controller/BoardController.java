package hos.acorns.work.domain.board.controller;

import hos.acorns.work.domain.board.dto.BoardRequestDTO;
import hos.acorns.work.domain.board.dto.BoardResponseDTO;
import hos.acorns.work.domain.board.service.BoardSevice;
import hos.acorns.work.domain.schedule.dto.ScheduleResponseDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/board")
@Tag(name = "Board", description = "공지,게시판 APIs")
@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    // 생성자주입
    private final BoardSevice boardSevice;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = BoardResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "공지,게시글 생성 API", description = "필요한 값 : category, boardPw, title, cpmtemt, view, state")
    @PostMapping(value = "/create")
    public Map<String, Object> BoardCreate(@RequestBody BoardRequestDTO dto, HttpServletRequest request) {

        return boardSevice.Crate(dto,request);
    }


    /*@ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = BoardResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "공지,게시글 상세 API", description = "필요한 값 : idx")
    @GetMapping(value = "/article")
    public Map<String, Object> BoardAticle(@RequestParam Long idx) {

        return boardSevice.Article(idx);
    }*/

    /* list엔 idx 추가
    @ApiOperation(value=" 게시판리스트 "
            , notes="<h2>✓ Test </h2>  <h1> (Test : 테스트)) </h1>")
    @GetMapping(value = "/list")
    public Map<String, Object> BoardList(@RequestParam int size, @RequestParam int page, @RequestParam Long idx) {
        // 페이지 설정 (들어온페이지, 총 보여줄개수);
        Pageable pageable = PageRequest.of(page-1, size);
        return boardSevice.List(pageable, idx);
    }*/

   /* 쿼리로 불러옴
   @ApiOperation(value=" 게시판리스트 "
            , notes="<h2>✓ Test </h2>  <h1> (Test : 테스트)) </h1>")
    @GetMapping(value = "/list")
    public Map<String, Object> BoardList(@RequestParam int size, @RequestParam int page) {

        System.out.print(size+page);
        // 페이지 설정 (들어온페이지, 총 보여줄개수);
        Pageable pageable = PageRequest.of(page-1, size);
        return boardSevice.List(pageable);
    }*/

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = BoardResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "공지,게시글 리스트 API", description = "필요한 값 : ")
    @GetMapping(value = "/list")
    public Map<String, Object> BoardList(@Parameter Pageable pageable, HttpServletRequest request) {

        return boardSevice.List(pageable, request);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = BoardResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "공지,게시글 수정 API", description = "필요한 값 : idx, category, title, content, boardPw, udt_Dt, view, state")
    @PostMapping(value = "/update")
    public Map<String, Object> BoardUpdate(@RequestBody BoardRequestDTO dto, HttpServletRequest request) {

        return boardSevice.Update(dto, request);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = BoardResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "공지,게시글 삭제 API", description = "필요한 값 : idx, udt_Dt, boardPw, state")
    @PostMapping(value = "/delete")
    public Map<String, Object> BoardDelete(@RequestBody BoardRequestDTO dto, HttpServletRequest request) {

        return boardSevice.Delete(dto, request);
    }


}
