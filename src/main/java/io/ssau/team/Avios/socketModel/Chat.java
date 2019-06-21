package io.ssau.team.Avios.socketModel;

import io.ssau.team.Avios.socketModel.json.MessageJson;
import io.ssau.team.Avios.socketService.ChatService;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Chat extends Thread implements Closeable {
    private static final Integer TIME_FOR_OUT = 60 * 1000;
    private SocketUser firstUser;
    private SocketUser secondUser;
    private SocketUser current;
    private SocketUser opponent;
    private Integer id;
    private boolean ready = false;
    private boolean ended = false;
    private Runnable task;
    private Timer roundsTimer;
    private int rounds = 0;
    private List<MessageJson> messages;
    private List<SocketViewer> socketViewers;
    private ChatService chatService;
    private Integer themeId;

    public Chat(ChatService chatService, Integer id, Integer themeId) {
        this.chatService = chatService;
        this.id = id;
        this.themeId = themeId;
        Timer connectionTimer = new Timer(true);
        //таймер ожидает 10 секунд, и если хотя бы 1 игрок не подключаться, то заканчиваем игру
        connectionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!ready) {
                    close();
                }
            }
        }, 10 * 1000);
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
        socketViewers = new ArrayList<>();
        current = firstUser;
        opponent = secondUser;
        new Thread(firstUser).start();
        new Thread(secondUser).start();
        this.roundsTimer = new Timer(true);
        //таймер на 60 секунд
        task = () -> sendMessageToAll(new MessageJson(messages.size(), Integer.MAX_VALUE, "timeout", false));
        roundsTimer.schedule(new TimerTask() {
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
            roundsTimer.cancel();
            sendMessageToAll(new MessageJson(messages.size(), sender.getId(), message, true));
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

    public void addSocketViewer(SocketViewer socketViewer) {
        this.socketViewers.add(socketViewer);
        try {
            socketViewer.sendAllMessages(messages);
        } catch (IOException e) {
            socketViewer.close();
            socketViewers.remove(socketViewer);
        }
    }

    public boolean isEnded() {
        return ended;
    }

    private void endGame(boolean fullGame) {
        if (fullGame) {
            chatService.addResultsToUser(messages, current);
        }
        chatService.addResultsToUser(messages, opponent);
        close();
    }

    void userLeaved() {
        sendMessageToAll(new MessageJson(messages.size(), current.getId(), current.getUsername() + " leaved game", false));
        endGame(false);
    }

    @Override
    public void close() {
        ended = true;
        if (firstUser != null) {
            firstUser.close();
        }
        if (secondUser != null) {
            secondUser.close();
        }
        this.chatService.deleteChat(this.id);
    }

    private void sendMessageToAll(MessageJson message) {
        messages.add(message);
        current.sendMessage(new MessageJson(message));
        opponent.sendMessage(message);
        for (int i = 0; i < socketViewers.size(); i++) {
            try {
                socketViewers.get(i).sendMessage(message);
            } catch (IOException e) {
                socketViewers.get(i).close();
                socketViewers.remove(i);
            }
        }
        changeCurrent();
    }

    private void notifyVote(boolean up, Integer messageId) {
        MessageJson message = new MessageJson(messages.size(), Integer.MAX_VALUE, messageId + ":" + up, true);
        current.sendMessage(message);
        opponent.sendMessage(message);
        for (int i = 0; i < socketViewers.size(); i++) {
            try {
                socketViewers.get(i).sendMessage(message);
            } catch (IOException e) {
                socketViewers.get(i).close();
                socketViewers.remove(i);
            }
        }
    }

    private void changeCurrent() {
        rounds++;
        if (rounds == 20) {
            endGame(true);
            return;
        }
        var tmp = current;
        current = opponent;
        opponent = tmp;
        roundsTimer = new Timer(true);
        roundsTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, TIME_FOR_OUT);
    }

    public void setCurrent(SocketUser current) {
        this.opponent = this.current;
        this.current = current;
    }

    public SocketUser getFirstUser() {
        return firstUser;
    }

    public SocketUser getSecondUser() {
        return secondUser;
    }

    public void voteForMessage(Integer messageIndex, Integer userId) {
        --messageIndex;
        if (messages.size() > messageIndex) {
            //send message to all that message# voted up or down
            notifyVote(messages.get(messageIndex).vote(userId), messageIndex);
        }
    }
}
