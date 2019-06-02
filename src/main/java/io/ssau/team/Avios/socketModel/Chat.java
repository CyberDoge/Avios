package io.ssau.team.Avios.socketModel;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
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
    private OutputStream secondUserOutputStream;
    private OutputStream firstUserOutputStream;
    private Runnable task;
    private Timer timer;

    public Chat() {
        id = random.nextInt();
    }

    public Integer getCharId() {
        return id;
    }

    public void run() {
        new Thread(firstUser).start();
        new Thread(secondUser).start();
        this.timer = new Timer(id.toString(), true);
        //таймер на 60 секунд
        task = () -> {
            sendMessageToAll("timeout", false);
        };
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, 6000);
    }

    public void readMessage(String message, SocketUser sender) {
        if (current == sender) {
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
            secondUserOutputStream = secondUser.getOutputStream();
            firstUserOutputStream = firstUser.getOutputStream();
            this.ready = true;
        }
    }


    @Override
    public void close() throws IOException {
        firstUser.close();
        secondUser.close();
    }

    private void sendMessageToAll(String message, boolean success) {
        try {
            if (current == firstUser) {
                firstUser.sendMessage(String.valueOf(success));
                secondUser.sendMessage(message);
            } else {
                secondUser.sendMessage(String.valueOf(success));
                firstUser.sendMessage(message);
            }
            changeCurrent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeCurrent() {
        if (current == firstUser) {
            current = secondUser;
        } else {
            secondUser = firstUser;
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, 6000);
    }
}
