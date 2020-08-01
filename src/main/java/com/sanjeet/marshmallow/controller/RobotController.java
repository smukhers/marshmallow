package com.sanjeet.marshmallow.controller;

import com.sanjeet.marshmallow.model.RequestInstructions;
import com.sanjeet.marshmallow.model.Response;
import com.sanjeet.marshmallow.service.RobotCleanerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RobotController {

    @Autowired
    RobotCleanerService robotCleanerService;

    @PostMapping("/")
    public Response clean(@RequestBody RequestInstructions requestInstructions) {
        return robotCleanerService.cleanOcean(requestInstructions);
    }
}
