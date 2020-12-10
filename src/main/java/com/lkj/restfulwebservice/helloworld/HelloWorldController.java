package com.lkj.restfulwebservice.helloworld;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class HelloWorldController {

    private final MessageSource messageSource;

    /**
     * Dispatcher Servlet
     * client -> http request -> DispatchServlet -> Handler Mapping
     * -> RestController(@Controller + @ResponseBody) -> http Response -> client
     */

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");   //return Object -> return response body
    }

    @GetMapping("/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping("/hello-world-internationalized")   //다국어 처리를 위한 controller
    public String helloWorldInternationalized(
            @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        //header 에 입력된 Accept-Language filed 값에 해당하는 다국어 리턴, 해당 나가락 없거나 or null -> default 한국어
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
