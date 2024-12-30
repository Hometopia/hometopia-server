package com.hometopia.ruleservice.config.drools;

import com.hometopia.commons.utils.AppConstants;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DroolsConfig {
    private static final KieServices kieServices = KieServices.Factory.get();

    @Value("${rule.category}")
    private String categoryRulesDrl;

    @Value("${rule.useful-life}")
    private String usefulLifeRulesDrl;

    @Bean("categoryRuleContainer")
    @Profile(AppConstants.DEV_PROFILE)
    public KieContainer devCategoryRuleContainer() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(categoryRulesDrl));
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

    @Bean("assetUsefulLifeRuleContainer")
    @Profile(AppConstants.DEV_PROFILE)
    public KieContainer devAssetUsefulLifeRuleContainer() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(usefulLifeRulesDrl));
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

    @Bean("categoryRuleContainer")
    @Profile(AppConstants.PROD_PROFILE)
    public KieContainer prodCategoryRuleContainer() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newFileResource(categoryRulesDrl));
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

    @Bean("assetUsefulLifeRuleContainer")
    @Profile(AppConstants.PROD_PROFILE)
    public KieContainer prodAssetUsefulLifeRuleContainer() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newFileResource(usefulLifeRulesDrl));
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }
}
