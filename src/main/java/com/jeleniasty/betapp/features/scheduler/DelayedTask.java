package com.jeleniasty.betapp.features.scheduler;

import java.time.Instant;

public record DelayedTask(Long id, Instant delay, Runnable task) {}
