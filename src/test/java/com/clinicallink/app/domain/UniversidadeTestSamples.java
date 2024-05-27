package com.clinicallink.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UniversidadeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Universidade getUniversidadeSample1() {
        return new Universidade().id(1L).cnpj("cnpj1").name("name1").cep("cep1");
    }

    public static Universidade getUniversidadeSample2() {
        return new Universidade().id(2L).cnpj("cnpj2").name("name2").cep("cep2");
    }

    public static Universidade getUniversidadeRandomSampleGenerator() {
        return new Universidade()
            .id(longCount.incrementAndGet())
            .cnpj(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .cep(UUID.randomUUID().toString());
    }
}
