package de.otto.edison.togglz.configuration;

import com.mongodb.client.MongoDatabase;
import de.otto.edison.mongo.configuration.MongoProperties;
import de.otto.edison.togglz.FeatureClassProvider;
import de.otto.edison.togglz.mongo.MongoTogglzRepository;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.user.UserProvider;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableConfigurationProperties(MongoProperties.class)
@ConditionalOnProperty(prefix = "edison.togglz", name = "mongo.enabled", havingValue = "true")
@ConditionalOnBean(type = "com.mongodb.MongoClient")
public class MongoTogglzConfiguration {

    private static final Logger LOG = getLogger(MongoTogglzConfiguration.class);

    @Bean
    StateRepository stateRepository(final MongoDatabase mongoDatabase,
                                    final FeatureClassProvider featureClassProvider,
                                    final UserProvider userProvider,
                                    final MongoProperties mongoProperties) {
        LOG.info("===============================");
        LOG.info("Using MongoTogglzRepository with " + mongoDatabase.getClass().getSimpleName() + " MongoDatabase impl.");
        LOG.info("===============================");
        return new MongoTogglzRepository(mongoDatabase, featureClassProvider, userProvider, mongoProperties);
    }
}