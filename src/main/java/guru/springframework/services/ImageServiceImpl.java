package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(String recipeId, MultipartFile file) {

        try {
            Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

            if (!recipeOptional.isPresent()) {
                log.error("Recipe with ID: " + recipeId + " not found");
                throw new NotFoundException("Cannot save image for recipe, because recipe with id: " +
                        recipeId + " not found in the database");
            }

            Recipe recipe = recipeOptional.get();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }

            recipe.setImage(byteObjects);

            recipeRepository.save(recipe);

        } catch (IOException e) {
            log.error("Error occurred", e);
            e.printStackTrace();
            throw new RuntimeException("Error occurred during save operation. " +
                    "This is the server internal error.");
        }
    }
}
