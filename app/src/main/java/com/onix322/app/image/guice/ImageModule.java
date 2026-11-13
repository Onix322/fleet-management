package com.onix322.app.image.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.onix322.app.image.resource.model.ImageAbstract;
import com.onix322.app.image.resource.model.impl.ImageDamage;
import com.onix322.app.image.resource.model.impl.ImageProfile;
import com.onix322.app.image.resource.model.impl.ImageReport;

@Singleton
public class ImageModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Class<?>> entities = Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>() {
        });
        entities.addBinding().toInstance(ImageAbstract.class);
        entities.addBinding().toInstance(ImageProfile.class);
        entities.addBinding().toInstance(ImageDamage.class);
        entities.addBinding().toInstance(ImageReport.class);
    }

}
