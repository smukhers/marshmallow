package com.sanjeet.marshmallow.service;

import com.sanjeet.marshmallow.error.ParameterParsingException;
import com.sanjeet.marshmallow.model.RequestInstructions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestValidatorTest {

    private final RequestValidator rv = new RequestValidator();

    @Test
    public void validInputsPass() {
        rv.validateInput(aValidInput().build());
    }

    @Test
    public void nullAreaSizeFails() {
        assertThatThrownBy(() -> rv.validateInput(aValidInput().areaSize(null).build()))
                .isInstanceOf(ParameterParsingException.class)
                .hasMessageContaining("areaSize");
    }

    @Test
    public void nullStartPosFails() {
        assertThatThrownBy(() -> rv.validateInput(aValidInput().startingPosition(null).build()))
                .isInstanceOf(ParameterParsingException.class)
                .hasMessageContaining("startingPosition");
    }

    @Test
    public void noOilPatchesIsValid() {
        rv.validateInput(aValidInput().oilPatches(null).build());
    }

    @Test
    public void tooMuchOilFails() {
        assertThatThrownBy(() -> rv.validateInput(aValidInput().areaSize(new int[]{1, 1}).build()))
                .isInstanceOf(ParameterParsingException.class)
                .hasMessageContaining("Too many oil patches");
    }

    @Test
    public void noDirectionFails() {
        assertThatThrownBy(() -> rv.validateInput(aValidInput().navigationInstructions(null).build()))
                .isInstanceOf(ParameterParsingException.class)
                .hasMessageContaining("navigationInstructions");
    }

    @Test
    public void nonCardinalDirectionFails() {
        assertThatThrownBy(() -> rv.validateInput(aValidInput().navigationInstructions("ABC").build()))
                .isInstanceOf(ParameterParsingException.class)
                .hasMessageContaining("navigationInstructions");
    }

    private RequestInstructions.RequestInstructionsBuilder aValidInput() {
        int[][] oilPatches = {
                new int[]{1, 0},
                new int[]{2, 2},
                new int[]{2, 3}
        };
        return RequestInstructions.builder()
                .areaSize(new int[]{5, 5})
                .startingPosition(new int[]{1, 2})
                .oilPatches(oilPatches)
                .navigationInstructions("NNESEESWNWW");
    }
}