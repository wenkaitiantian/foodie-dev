package com.imooc.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
@GetMapping("/hello")
public Object hello(){
    return "wenkai";
}
}
