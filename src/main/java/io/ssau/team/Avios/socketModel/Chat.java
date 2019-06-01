package io.ssau.team.Avios.socketModel;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Chat implements Closeable {
    private static final Random random = new Random();
    private SocketUser firstUser;
    private SocketUser secondUser;
    private SocketUser current;
    private Integer id;
    private boolean ready = false;
    private OutputStream secondUserOutputStream;
    private OutputStream firstUserOutputStream;
    private TimerTask task;
    private Timer timer;

    public Chat() {
        id = random.nextInt();
    }

    public Integer getId() {
        return id;
    }

    public void startChat() {
        new Thread(firstUser).start();
        new Thread(secondUser).start();
        this.timer = new Timer(id.toString(), true);
        //таймер на 60 секунд
        task = new TimerTask() {
            @Override
            public void run() {
                sendMessageToAll("timeout", false);
            }
        };
        timer.schedule(task, 60000);
    }

    public void readMessage(String message, SocketUser sender) {
        if (current == sender) {
            timer.cancel();
            sendMessageToAll(message, true);
            timer.schedule(task, 60000);
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
                secondUser.sendMessage("recieved");
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
    }
}
