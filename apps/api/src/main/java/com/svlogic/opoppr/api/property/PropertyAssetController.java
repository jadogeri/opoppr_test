package com.svlogic.opoppr.api.property;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property-assets")
public class PropertyAssetController {
    private static final List<PropertyAsset> ASSETS = List.of(
            new PropertyAsset(1, 1, "10", "Furniture and fixtures"),
            new PropertyAsset(2, 1, "20", "Computer equipment"),
            new PropertyAsset(3, 2, "30", "Machinery and equipment"),
            new PropertyAsset(4, 3, "40", "Leasehold improvements"),
            new PropertyAsset(5, 4, "50", "Vehicles"),
            new PropertyAsset(6, 5, "60", "Other personal property")
    );

    @GetMapping
    public List<PropertyAsset> list(
            @RequestParam(defaultValue = "1") @Min(1) @Max(5) int section
    ) {
        return ASSETS.stream().filter(asset -> asset.section() == section).toList();
    }

    public record PropertyAsset(long id, int section, String category, String description) {
    }
}