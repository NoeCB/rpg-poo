package com.dbd.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Puente estático para acceder al contexto de Spring desde clases
 * que no son gestionadas por él (como Personaje).
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    /**
     * Devuelve el Bean del tipo especificado que está gestionado por Spring.
     */
    public static <T> T getBean(Class<T> beanClass) {
        if (context == null) {
            System.err.println("CRITICAL: El contexto de Spring aún no se ha inicializado.");
            return null;
        }
        return context.getBean(beanClass);
    }
}
