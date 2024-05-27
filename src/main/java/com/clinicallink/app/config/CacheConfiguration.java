package com.clinicallink.app.config;

import com.github.benmanes.caffeine.jcache.configuration.CaffeineConfiguration;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Caffeine caffeine = jHipsterProperties.getCache().getCaffeine();

        CaffeineConfiguration<Object, Object> caffeineConfiguration = new CaffeineConfiguration<>();
        caffeineConfiguration.setMaximumSize(OptionalLong.of(caffeine.getMaxEntries()));
        caffeineConfiguration.setExpireAfterWrite(OptionalLong.of(TimeUnit.SECONDS.toNanos(caffeine.getTimeToLiveSeconds())));
        caffeineConfiguration.setStatisticsEnabled(true);
        jcacheConfiguration = caffeineConfiguration;
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.clinicallink.app.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.clinicallink.app.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.clinicallink.app.domain.User.class.getName());
            createCache(cm, com.clinicallink.app.domain.Authority.class.getName());
            createCache(cm, com.clinicallink.app.domain.User.class.getName() + ".authorities");
            createCache(cm, com.clinicallink.app.domain.Usuario.class.getName());
            createCache(cm, com.clinicallink.app.domain.Usuario.class.getName() + ".avaliacaos");
            createCache(cm, com.clinicallink.app.domain.Usuario.class.getName() + ".consultas");
            createCache(cm, com.clinicallink.app.domain.Especialista.class.getName());
            createCache(cm, com.clinicallink.app.domain.Especialista.class.getName() + ".especialidadeEspecialistas");
            createCache(cm, com.clinicallink.app.domain.Especialista.class.getName() + ".avaliacaos");
            createCache(cm, com.clinicallink.app.domain.Especialista.class.getName() + ".consultas");
            createCache(cm, com.clinicallink.app.domain.Especialista.class.getName() + ".especialistas");
            createCache(cm, com.clinicallink.app.domain.Universidade.class.getName());
            createCache(cm, com.clinicallink.app.domain.Universidade.class.getName() + ".especialistas");
            createCache(cm, com.clinicallink.app.domain.Especialidade.class.getName());
            createCache(cm, com.clinicallink.app.domain.Especialidade.class.getName() + ".especialidadeEspecialistas");
            createCache(cm, com.clinicallink.app.domain.EspecialidadeEspecialista.class.getName());
            createCache(cm, com.clinicallink.app.domain.Avaliacao.class.getName());
            createCache(cm, com.clinicallink.app.domain.Consulta.class.getName());
            createCache(cm, com.clinicallink.app.domain.Estado.class.getName());
            createCache(cm, com.clinicallink.app.domain.Estado.class.getName() + ".cidades");
            createCache(cm, com.clinicallink.app.domain.Estado.class.getName() + ".usuarios");
            createCache(cm, com.clinicallink.app.domain.Estado.class.getName() + ".especialistas");
            createCache(cm, com.clinicallink.app.domain.Cidade.class.getName());
            createCache(cm, com.clinicallink.app.domain.Cidade.class.getName() + ".usuarios");
            createCache(cm, com.clinicallink.app.domain.Cidade.class.getName() + ".especialistas");
            // jhipster-needle-caffeine-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
