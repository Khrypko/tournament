package game.tournaments.config;

import game.tournaments.battle.config.BattleFactory;
import game.tournaments.battle.config.BattleFactoryImpl;
import game.tournaments.battle.config.battlefield.BattlefieldFactory;
import game.tournaments.battle.config.battlefield.BattlefieldFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by maks on 28.09.17.
 */
@Configuration
public class BattleConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(50);
        return executor;
    }

    @Bean
    public BattleFactory battleFactory(){
        return new BattleFactoryImpl(battlefieldFactory());
    }

    @Bean
    public BattlefieldFactory battlefieldFactory(){
        return new BattlefieldFactoryImpl();
    }

}
