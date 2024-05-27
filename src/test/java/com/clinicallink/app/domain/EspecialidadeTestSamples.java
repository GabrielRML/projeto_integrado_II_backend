package com.clinicallink.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EspecialidadeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Especialidade getEspecialidadeSample1() {
        return new Especialidade().id(1L).name("name1");
    }

    public static Especialidade getEspecialidadeSample2() {
        return new Especialidade().id(2L).name("name2");
    }

    public static Especialidade getEspecialidadeRandomSampleGenerator() {
        return new Especialidade().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
