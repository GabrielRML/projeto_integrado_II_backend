package com.clinicallink.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EspecialistaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Especialista getEspecialistaSample1() {
        return new Especialista().id(1L).cpf("cpf1").identification("identification1").description("description1").timeSession(1);
    }

    public static Especialista getEspecialistaSample2() {
        return new Especialista().id(2L).cpf("cpf2").identification("identification2").description("description2").timeSession(2);
    }

    public static Especialista getEspecialistaRandomSampleGenerator() {
        return new Especialista()
            .id(longCount.incrementAndGet())
            .cpf(UUID.randomUUID().toString())
            .identification(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .timeSession(intCount.incrementAndGet());
    }
}
