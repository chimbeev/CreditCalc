/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CredCalcSpringSecured;
    import java.util.concurrent.atomic.AtomicLong;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 1
 */


@RestController
public class CreditController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Credit greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Credit(counter.incrementAndGet(), String.format(template, name));
	}
}

