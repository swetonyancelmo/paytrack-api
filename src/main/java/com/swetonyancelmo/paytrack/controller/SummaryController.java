package com.swetonyancelmo.paytrack.controller;

import com.swetonyancelmo.paytrack.dtos.response.IncomeDto;
import com.swetonyancelmo.paytrack.dtos.response.SummaryDto;
import com.swetonyancelmo.paytrack.service.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/summary/v1")
@Tag(name = "Summary", description = "Endpoints for Managing Summary")
public class SummaryController implements com.swetonyancelmo.paytrack.controller.docs.SummaryControllerDocs {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Override
    public ResponseEntity<SummaryDto> summary(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(summaryService.summary(userId));
    }

}
