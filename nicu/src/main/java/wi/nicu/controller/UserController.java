package wi.nicu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // 需要回傳視圖解析器，也就是需要web page
public class UserController {
	@RequestMapping("/page") // RequestParam接收請求是否有帶入title的name參數
	public String greeting(@RequestParam(value = "title", required = false, defaultValue = "Great to Coding!") String title,
			Model model) {
		model.addAttribute("name", title); // 增加一個Attribute叫做name，將title參數添加到Model上叫做name
		return "index"; // 回傳View，Spring Boot使用Thymeleaf template engine
	}
}
