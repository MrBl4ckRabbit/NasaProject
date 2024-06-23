package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class MyTelegramBot extends TelegramLongPollingBot {

    public static final String BOT_TOKEN = "7487406852:AAH8NEUaq-JKIj3Z1hwFUwrlbu-xt7ubbgU";

    public static final String BOT_USERNAME = "NetologyNasaBot";
    String URI = "https://api.nasa.gov/planetary/apod?api_key=IwBTrFvtgYLw4jQz9yYTCxGfxo3LeVVTtL30UtF7";
    public static long chat_id;

    public MyTelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            chat_id = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            String[] splittedText = text.split(" ");
            String action = splittedText[0];

            switch (action) {
                case "/start":
                case "/старт":
                    sendMessage("Я бот от НАСА. Я присылаю картинку дня.Для получения картинки дня, введите /image или /фото." +
                            "Если хотите получить фото за некую определённую дату, то введите /date или /дата (год-месяц-день) .");
                    break;
                case "/help":
                case "/помощь":
                    sendMessage("Привет, я бот NASA! Я высылаю ссылки на картинки по запросу. " +
                            "Напоминаю, что картинки на сайте NASA обновляются раз в сутки");
                    break;
                case "/image":
                case "/фото":
                    try {
                        sendMessage(Utils.getUrl(URI));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "/date":
                    String date = splittedText[1];
                    String image = null;
                    try {
                        image = Utils.getUrl(URI);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        image = Utils.getUrl(URI + "&date=" + date);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sendMessage(image);
                    break;
                default:
                    sendMessage("Я не понимаю :(");
            }
        }
    }

    private void sendMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
