package com.shopme.admin.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "rest")
public class MainRestController {

    @GetMapping(value = "")
    public String defaultRestController() {
        return "abc";
    }


}
