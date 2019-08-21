package mx.jovannypcg.fc;

import mx.jovannypcg.fc.command.CalculatorCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FcApplication implements CommandLineRunner {
    private CalculatorCommand calculatorCommand;

    public FcApplication(CalculatorCommand calculatorCommand) {
        this.calculatorCommand = calculatorCommand;
    }

    public static void main(String[] args) {
        SpringApplication.run(FcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        calculatorCommand.perform(args);
    }
}
