package guru.springframework.commands;

import guru.springframework.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private String id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @NotNull
    @Min(1)
    @Max(999)
    private Integer prepTime;

    @NotNull
    @Min(1)
    @Max(999)
    private Integer cookTime;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer servings;

    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;

    private List<IngredientCommand> ingredients = new ArrayList<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private List<CategoryCommand> categories = new ArrayList<>();
    private Byte[] image;
}
