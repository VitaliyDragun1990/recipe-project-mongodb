package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToCommand;
    private final IngredientCommandToIngredient commandToIngredient;

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UnitOfMeasureRepository unitRepository;


    private IngredientService ingredientService;

    // init converters
    public IngredientServiceImplTest() {
        this.ingredientToCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.commandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToCommand, commandToIngredient,
                recipeRepository, unitRepository);
    }

    @Test
    public void testFindByRecipeIdAndIngredientIdHappyPath() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("2");

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3");

        recipe.addIngredient(ingredient1).addIngredient(ingredient2).addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        // when
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3");

        // then
        assertEquals("3", ingredientCommand.getId());
        assertEquals("1", ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyString());
    }

    @Test
    public void testUpdateRecipeCommand() {
        // given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("3");

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        // when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        // then
        assertEquals("3", savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));


    }

    @Test
    public void testSaveRecipeCommand() {
        // given
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId("1");

        IngredientCommand command = new IngredientCommand();
        command.setRecipeId("2");
        command.setDescription("New ingredient");
        command.setAmount(BigDecimal.valueOf(50));
        command.setUom(unitOfMeasureCommand);


        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId("1");

        Ingredient ingredient = new Ingredient();
        ingredient.setId("10");
        ingredient.setDescription("New ingredient");
        ingredient.setAmount(BigDecimal.valueOf(50));
        ingredient.setUom(unitOfMeasure);

        Recipe recipe = new Recipe();
        recipe.addIngredient(ingredient);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());


        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(recipe);


        // when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        // then
//        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testDeleteById() {
        // given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3");
        recipe.addIngredient(ingredient);
//        ingredient.setRecipe(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));

        // when
        ingredientService.deleteById("1", "3");

        // then
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}