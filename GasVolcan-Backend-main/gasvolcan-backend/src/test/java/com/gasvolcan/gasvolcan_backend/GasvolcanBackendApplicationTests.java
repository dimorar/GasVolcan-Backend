package com.gasvolcan.gasvolcan_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:gasvolcan_test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "app.jwt.secret=GasVolcanTestSecretKeyMustHaveAtLeast32Characters",
    "app.cors.allowed-origin=http://localhost:5173"
})
class GasvolcanBackendApplicationTests {

    @Test
    void contextLoads() {
    }
}
