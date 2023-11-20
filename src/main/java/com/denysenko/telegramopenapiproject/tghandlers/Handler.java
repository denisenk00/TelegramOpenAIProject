package com.denysenko.telegramopenapiproject.tghandlers;

import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Handler {
    public abstract boolean isApplicable(Update update);
    public abstract void handle(Update update);
}
