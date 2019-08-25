package mx.jovannypcg.fc;

import mx.jovannypcg.fc.command.FractionCalculator;
import mx.jovannypcg.fc.commons.Message;
import mx.jovannypcg.fc.domain.Fraction;
import mx.jovannypcg.fc.exception.CalculatorException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Scanner;

@SpringBootApplication
public class FcApplication implements CommandLineRunner {
    private FractionCalculator fractionCalculator;

    public FcApplication(FractionCalculator fractionCalculator) {
        this.fractionCalculator = fractionCalculator;
    }

    public static void main(String[] args) {
        SpringApplication.run(FcApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(Arrays.toString(args));
        try {
            Fraction result = fractionCalculator.perform(args);
            System.out.println(Message.resultOutput(args, result.toString()));
        } catch (CalculatorException ce) {
            System.out.println(Message.usage(ce.getMessage()));
        }
    }
}
