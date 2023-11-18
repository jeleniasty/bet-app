package com.jeleniasty.betapp.features.result.score;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ScoreDTO(
  @Min(value = 0, message = "Score cannot be negative")
  @Max(value = 200, message = "Score cannot be higher than 200")
  @NotNull
  Integer home,
  @Min(value = 0, message = "Score cannot be negative")
  @Max(value = 200, message = "Score cannot be higher than 200")
  @NotNull(message = "Score cannot be null")
  Integer away
) {}
