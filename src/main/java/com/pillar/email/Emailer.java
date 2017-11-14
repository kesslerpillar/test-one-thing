package com.pillar.email;

public interface Emailer {
    void send(Emailable to, Attachable attachment);
}
