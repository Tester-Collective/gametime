package id.ac.ui.cs.advprog.gametime.config;

import id.ac.ui.cs.advprog.gametime.service.CartService;
import id.ac.ui.cs.advprog.gametime.service.CartServiceImpl;
import id.ac.ui.cs.advprog.gametime.service.decorator.CartServiceDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CartService cartService(CartServiceImpl cartServiceImpl) {
        return new CartServiceDecorator(cartServiceImpl);
    }
}
