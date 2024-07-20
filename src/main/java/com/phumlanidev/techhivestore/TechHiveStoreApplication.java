package com.phumlanidev.techhivestore;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
@EnableEncryptableProperties
public class TechHiveStoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(TechHiveStoreApplication.class, args);
  }

}
