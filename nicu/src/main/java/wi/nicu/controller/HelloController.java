package wi.nicu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController //建置RESTful Web Service的控制器
public class HelloController {

//	@RequestMapping("/") //透過RequestMapping指定URL路徑"/"，對應hello方法
//	public String hello(){
//		return "Hello World! We are use Spring Boot!";
//	}
	@RequestMapping("/nicu")
	public String nicu() {
		return "Hello NICU!";
	}	
}
