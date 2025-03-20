package com.phumlanidev.techhivestore;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Comment: this is the placeholder for documentation.
 */
@SpringBootApplication
@EnableEncryptableProperties
@EnableJpaAuditing
@EnableCaching
public class TechHiveStoreApplication {

  /**
   * Comment: this is the placeholder for documentation.
   */
  public static void main(String[] args) {
    SpringApplication.run(TechHiveStoreApplication.class, args);
  }

}
