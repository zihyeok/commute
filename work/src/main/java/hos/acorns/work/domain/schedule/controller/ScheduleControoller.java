package hos.acorns.work.domain.schedule.controller;

import hos.acorns.work.domain.board.dto.BoardRequestDTO;
import hos.acorns.work.domain.schedule.dto.ScheduleRequestDTO;
import hos.acorns.work.domain.schedule.dto.ScheduleResponseDTO;
import hos.acorns.work.domain.schedule.service.ScheduleSevice;
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

import javax.xml.transform.Source;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@RequestMapping("/schedule")
@Tag(name = "Schedule", description = "스케줄 APIs")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ScheduleControoller {

    private final ScheduleSevice scheduleSevice;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ScheduleResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "스케줄 업로드 API", description = "필요한 값 : userIdx, content, startDt, endDt")
    @PostMapping(value = "/upload" , consumes = "application/json")
    public Map<String, Object> Upload(@RequestBody ScheduleRequestDTO dto, HttpServletRequest request) {
        //@PostMapping(value = "/upload" , consumes = "application/json")
        //System.out.println(dto+"--------------------");
        return scheduleSevice.Upload(dto,request);
    }

    /*@Operation(summary = "스케줄 상세 API", description = "필요한 값 : idx")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ScheduleResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(value = "/article")
    public Map<String, Object> Aticle(@RequestParam Long idx, HttpServletRequest request) {

        return scheduleSevice.Article(idx,request);
    }*/

    @Operation(summary = "스케줄 리스트 API", description = "필요한 값 : ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ScheduleResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(value = "/list")
    public Map<String, Object> List(HttpServletRequest request) {

        return scheduleSevice.List(request);
    }

    @Operation(summary = "스케줄 수정 API", description = "필요한 값 : idx, userIdx, content, startDt, endDt, udt_Dt(공백), udtActive")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ScheduleResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
    })
    @PostMapping(value = "/update")
    public Map<String, Object> Update(@RequestBody ScheduleRequestDTO dto, HttpServletRequest request) {

        return scheduleSevice.Update(dto,request);
    }

    @Operation(summary = "스케줄 삭제 API", description = "필요한 값 : idx, userIdx, udt_Dt(공백), udtActive")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ScheduleResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Failed", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error", content = {@Content(mediaType = "application/json")})
    })
    @PostMapping(value = "/delete")
    public Map<String, Object> Delete(@RequestBody ScheduleRequestDTO dto, HttpServletRequest request) {
        return scheduleSevice.Delete(dto,request);
    }

    /*@ApiOperation(value=" 스케줄 새로고침 "
            , notes="<h2>✓ Test </h2>  <h1> (Test : 테스트)) </h1>")
    @PostMapping(value = "/renewal")
    public Map<String, Object> Renewal(@RequestParam(value = "today",required = false) String today) {

        return scheduleSevice.Renewal(today);
    }*/

}
