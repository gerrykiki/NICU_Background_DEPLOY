package deploy.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
	
	@GetMapping("/lab02")
	public void lab02() throws IOException {
		FileReader fr = new FileReader("lab02.txt");

		BufferedReader br = new BufferedReader(fr);

		while (br.ready()) {

			System.out.println(br.readLine());

		}

		fr.close();
	}
}
