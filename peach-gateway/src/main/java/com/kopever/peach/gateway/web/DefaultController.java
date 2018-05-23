package com.kopever.peach.gateway.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lullaby on 2018/5/23
 */
@RestController
public class DefaultController {

    @GetMapping("/404")
    public String notFound() {
        return "wow 404";
    }

}
