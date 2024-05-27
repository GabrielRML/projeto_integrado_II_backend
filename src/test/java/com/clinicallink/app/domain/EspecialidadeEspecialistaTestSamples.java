package com.clinicallink.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class EspecialidadeEspecialistaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EspecialidadeEspecialista getEspecialidadeEspecialistaSample1() {
        return new EspecialidadeEspecialista().id(1L);
    }

    public static EspecialidadeEspecialista getEspecialidadeEspecialistaSample2() {
        return new EspecialidadeEspecialista().id(2L);
    }

    public static EspecialidadeEspecialista getEspecialidadeEspecialistaRandomSampleGenerator() {
        return new EspecialidadeEspecialista().id(longCount.incrementAndGet());
    }
}
