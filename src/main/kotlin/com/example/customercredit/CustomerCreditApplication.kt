git apackage com.example.customercredit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CustomerCreditApplication

fun main(args: Array<String>) {
	runApplication<CustomerCreditApplication>(*args)
}
