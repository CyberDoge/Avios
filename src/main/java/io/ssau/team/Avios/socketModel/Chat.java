package io.ssau.team.Avios.socketModel;

import java.io.Closeable;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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

    public Chat() {
        id = random.nextInt();
    }

    public Integer getChatId() {
        return id;
    }

    public void run() {
        current = firstUser;
        opponent = secondUser;
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
        }, TIME_FOR_OUT);
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

    private void endGame() {
        //todo
        try {
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void userLeaved() {
        //todo
        try {
            sendMessageToAll(current.getUsername() + " leaved game", false);
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

    private void sendMessageToAll(String message, boolean success) {
        current.sendMessage(String.valueOf(success));
        opponent.sendMessage(message);
        changeCurrent();
    }

    private void changeCurrent() {
        rounds++;
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
