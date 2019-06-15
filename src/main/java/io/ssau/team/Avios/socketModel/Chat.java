package io.ssau.team.Avios.socketModel;

import io.ssau.team.Avios.socketModel.json.MessageJson;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;

public class Chat extends Thread implements Closeable {
    private static final Random random = new Random();
    private static final Integer TIME_FOR_OUT = 60 * 1000;
    private SocketUser firstUser;
    private SocketUser secondUser;
    private SocketUser current;
    private SocketUser opponent;
    private Integer id;
    private boolean ready = false;
    private Runnable task;
    private Timer timer;
    private int rounds = 0;
    private List<MessageJson> messages;
    //todo set theme
    private Integer themeId;

    public Chat(Integer id, Integer themeId) {
        this.id = id;
        this.themeId = themeId;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public Integer getChatId() {
        return id;
    }

    public List<MessageJson> getMessages() {
        return messages;
    }

    public void run() {
        messages = new ArrayList<>(20);
        current = firstUser;
        opponent = secondUser;
        new Thread(firstUser).start();
        new Thread(secondUser).start();
        this.timer = new Timer(true);
        //таймер на 60 секунд
        task = () -> sendMessageToAll(new MessageJson(Integer.MAX_VALUE, "timeout"), false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, TIME_FOR_OUT);
    }

    public void readMessage(String message, SocketUser sender) {
        if (message == null) {
            userLeaved();
        } else if (current == sender) {
            timer.cancel();
            sendMessageToAll(new MessageJson(sender.getId(), message), true);
        }
    }

    public boolean isReady() {
        return ready;
    }

    public boolean setUser(SocketUser socketUser) {
        socketUser.setChat(this);
        if (firstUser == null) {
            this.firstUser = socketUser;
        } else {
            this.secondUser = socketUser;
            this.ready = true;
        }
        return this.ready;
    }

    private void endGame() {
        try {
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void userLeaved() {
        try {
            sendMessageToAll(new MessageJson(current.getId(), current.getUsername() + " leaved game"), false);
            endGame();
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        firstUser.close();
        secondUser.close();
    }

    private void sendMessageToAll(MessageJson message, boolean success) {
        current.sendMessage(new MessageJson(success));
        opponent.sendMessage(message);
        changeCurrent();
    }

    private void changeCurrent() {
        rounds++;
        if (rounds == 20) {
            endGame();
            return;
        }
        var tmp = current;
        current = opponent;
        opponent = tmp;
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, TIME_FOR_OUT);
    }

    public SocketUser getFirstUser() {
        return firstUser;
    }

    public SocketUser getSecondUser() {
        return secondUser;
    }
}
