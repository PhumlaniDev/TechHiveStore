package com.phumlanidev.techhivestore;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p> comment </p>.
 */
@SpringBootApplication
@EnableEncryptableProperties
public class TechHiveStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechHiveStoreApplication.class, args);
    }

}
