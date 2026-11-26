package com.onix322.app.image.guice;

import com.google.inject.AbstractModule;
import com.onix322.app.image.resource.model.ImageAbstract;
import com.onix322.app.image.resource.model.impl.dao.ImageDamage;
import com.onix322.app.image.resource.model.impl.dao.ImageProfile;
import com.onix322.app.image.resource.model.impl.dao.ImageReport;
import com.onix322.app.image.resource.repository.ImageRepository;
import jakarta.inject.Singleton;
import onix322.initializer.guice.InitializerModule;

import java.util.Set;

@Singleton
public class ImageModule extends AbstractModule {

    private static final Set<Class<?>> IMAGE_ENTITIES = Set.of(
            ImageAbstract.class,
            ImageProfile.class,
            ImageDamage.class,
            ImageReport.class
    );

    @Override
    protected void configure() {
        install(new InitializerModule(IMAGE_ENTITIES, ImageRepository.class));
    }

}
