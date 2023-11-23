package com.jeleniasty.betapp.features.scheduler;

import java.time.Duration;
import java.time.Instant;

public record Task(Long name, Instant delay, Duration period, Runnable task) {}
