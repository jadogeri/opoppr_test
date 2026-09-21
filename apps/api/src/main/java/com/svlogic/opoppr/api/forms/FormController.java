package com.svlogic.opoppr.api.forms;

import com.svlogic.opoppr.api.forms.FormModels.DashboardSummary;
import com.svlogic.opoppr.api.forms.FormModels.FormSummary;
import com.svlogic.opoppr.api.forms.FormModels.Lat5Form;
import com.svlogic.opoppr.api.forms.FormModels.Lat5UpdateRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class FormController {
    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping("/dashboard")
    public DashboardSummary dashboard(Authentication authentication) {
        return formService.dashboard(authentication.getName());
    }

    @GetMapping("/forms/{formId}/lat5")
    public Lat5Form getLat5(@PathVariable long formId) {
        return formService.getLat5(formId);
    }

    @PutMapping("/forms/{formId}/lat5")
    public Lat5Form updateLat5(@PathVariable long formId, @Valid @RequestBody Lat5UpdateRequest request) {
        return formService.updateLat5(formId, request);
    }

    @PostMapping("/forms/{formId}/submit")
    public FormSummary submit(@PathVariable long formId) {
        return formService.submit(formId);
    }
}