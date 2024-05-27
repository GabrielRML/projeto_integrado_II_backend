package com.clinicallink.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ConsultaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Consulta getConsultaSample1() {
        return new Consulta().id(1L).reason("reason1").link("link1");
    }

    public static Consulta getConsultaSample2() {
        return new Consulta().id(2L).reason("reason2").link("link2");
    }

    public static Consulta getConsultaRandomSampleGenerator() {
        return new Consulta().id(longCount.incrementAndGet()).reason(UUID.randomUUID().toString()).link(UUID.randomUUID().toString());
    }
}
