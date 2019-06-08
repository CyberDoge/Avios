package io.ssau.team.Avios.socketModel;

import java.io.Closeable;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Chat extends Thread implements Closeable {
    private static final Random random = new Random();
    private SocketUser firstUser;
    private SocketUser secondUser;
    private SocketUser current;
    private Integer id;
    private boolean ready = false;
    private Runnable task;
    private Timer timer;

    public Chat() {
        id = random.nextInt();
    }

    public Integer getChatId() {
        return id;
    }

    public void run() {
        current = firstUser;
        new Thread(firstUser).start();
        new Thread(secondUser).start();
        this.timer = new Timer(true);
        //таймер на 60 секунд
        task = () -> sendMessageToAll("timeout", false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, 60000);
    }

    public void readMessage(String message, SocketUser sender) {
        if (message == null) {
            //todo end of game
        } else if (current == sender) {
            timer.cancel();
            sendMessageToAll(message, true);
        }
    }

    public boolean isReady() {
        return ready;
    }

    public void setUser(SocketUser socketUser) {
        if (firstUser == null) {
            this.firstUser = socketUser;
        } else {
            this.secondUser = socketUser;
            this.ready = true;
        }
    }


    @Override
    public void close() throws IOException {
        firstUser.close();
        secondUser.close();
    }

    private void sendMessageToAll(String message, boolean success) {
        if (current == firstUser) {
            firstUser.sendMessage(String.valueOf(success));
            secondUser.sendMessage(message);
        } else {
            secondUser.sendMessage(String.valueOf(success));
            firstUser.sendMessage(message);
        }
        changeCurrent();
    }

    private void changeCurrent() {
        current = current == firstUser ? secondUser : firstUser;
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, 6000);
    }
}
