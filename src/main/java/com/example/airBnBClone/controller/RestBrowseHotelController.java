package com.example.airBnBClone.controller;

import com.example.airBnBClone.dto.HotelDTO;
import com.example.airBnBClone.dto.HotelSearchRequest;
import com.example.airBnBClone.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Slf4j
public class RestBrowseHotelController {

    private final InventoryService inventoryService;

    @PostMapping("/search")
    public ResponseEntity<Page<HotelDTO>> searchHotels(@RequestBody HotelSearchRequest searchRequest) {
        log.info("Received search request: {}", searchRequest);
        Page<HotelDTO> page =  inventoryService.searchHotels(searchRequest);
        log.info("Search completed. Found {} hotels for request: {}", page.getTotalElements(), searchRequest);
        return ResponseEntity.ok(page);
    }
}
