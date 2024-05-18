package id.ac.ui.cs.advprog.gametime;

import enums.Categories;
import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "id.ac.ui.cs.advprog.gametime")
@EnableCaching
public class GametimeApplication {

	@Autowired
	private CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(GametimeApplication.class, args);
	}

	@Bean
	@Transactional
	public CommandLineRunner loadData() {
		return args -> {
			if (categoryRepository.count() == 0) {
				for (Categories categoryType : Categories.values()) {
					Category category = new Category();
					category.setName(categoryType.getValue());
					categoryRepository.save(category);
				}
			}
		};
	}

}
