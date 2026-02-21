package com.swetonyancelmo.paytrack.controller.docs;

import com.swetonyancelmo.paytrack.dtos.response.SummaryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface SummaryControllerDocs {

    @Operation(
            summary = "Find a Summary",
            description = "Get a Summary by User Id",
            tags = {"Summary"},
            responses = {
                    @ApiResponse(
                            description = "Succes",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = SummaryDto.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<SummaryDto> summary(@RequestParam(name = "userId") Long userId);
}
