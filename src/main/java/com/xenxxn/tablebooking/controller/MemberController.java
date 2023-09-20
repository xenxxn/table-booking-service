package com.xenxxn.tablebooking.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @RequestMapping(value = "/member/register", method = RequestMethod.POST)
    public String register() {
        return "member/register";
    }
}
