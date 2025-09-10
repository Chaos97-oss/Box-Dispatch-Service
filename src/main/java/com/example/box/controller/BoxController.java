package com.example.box.controller;

import com.example.box.dto.ApiResponse;
import com.example.box.entity.Box;
import com.example.box.entity.Item;
import com.example.box.service.BoxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boxes")
public class BoxController {

    private final BoxService boxService;

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @PostMapping("create-box")
    public ResponseEntity<ApiResponse<Box>> createBox(@RequestBody Box box) {
        Box saved = boxService.createBox(box);
        return ResponseEntity.ok(ApiResponse.success(saved));
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<Box>>> getAvailable() {
        return ResponseEntity.ok(ApiResponse.success(boxService.getAvailableBoxes()));
    }

    @PostMapping("/{txref}/load")
    public ResponseEntity<ApiResponse<List<Item>>> loadItems(@PathVariable String txref, @RequestBody List<Item> items) {
        return ResponseEntity.ok(ApiResponse.success(boxService.loadItems(txref, items)));
    }

    @GetMapping("/{txref}/items")
    public ResponseEntity<ApiResponse<List<Item>>> getItems(@PathVariable String txref) {
        return ResponseEntity.ok(ApiResponse.success(boxService.getBoxItems(txref)));
    }

    @GetMapping("/{txref}/battery")
    public ResponseEntity<ApiResponse<Integer>> getBattery(@PathVariable String txref) {
        return ResponseEntity.ok(ApiResponse.success(boxService.getBattery(txref)));
    }
}
