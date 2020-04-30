package com.example.messenger_v11;

import com.example.messenger_v11.SocketNetwork.CreateRoomManager;

import org.junit.Test;

import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    CreateRoomManager createRoomManager = new CreateRoomManager("user1", "user2");

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


   @Test
    public void testcreteRoom(){


        createRoomManager.createRoom("user1", "user2")
                .subscribe(aBoolean -> {
                    assertEquals(true,aBoolean);
                });
   }






}