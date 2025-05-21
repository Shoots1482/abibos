package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner commandLineRunner(Environment env) {
        return args -> {
            String protocol = "http";
            if (Arrays.asList(env.getActiveProfiles()).contains("prod")) {
                protocol = "https";
            }
            
            String hostAddress = "localhost";
            try {
                hostAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                // Fallback to localhost
            }
            
            String port = env.getProperty("server.port", "8080");
            
            System.out.println("\n----------------------------------------------------------");
            System.out.println("Application is running!");
            System.out.println("Local: " + protocol + "://localhost:" + port);
            System.out.println("External: " + protocol + "://" + hostAddress + ":" + port);
            System.out.println("Profile(s): " + Arrays.toString(env.getActiveProfiles()));
            System.out.println("----------------------------------------------------------\n");
        };
    }
}
