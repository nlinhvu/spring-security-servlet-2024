package vn.cloud.servletsecuritydemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
class HelloController {

    @GetMapping("/hello")
    String hello() {
        return "Hello World!";
    }

    @GetMapping("/api-key/hello")
    Map<String, String> apiHello() {
        return Map.of("greeting", "Hello World!");
    }
}

