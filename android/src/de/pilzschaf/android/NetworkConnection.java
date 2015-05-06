package de.pilzschaf.android;

import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Pilzschaf on 12.04.2015.
 */
public class NetworkConnection extends Thread {
    public boolean connected = false;
    private boolean retry = false;
    private boolean dialogFinished = false;
    private Socket clientSocket = null;

    public void ConnectToServer(){
        this.start();
    }

    @Override
    public void run(){
        //connect to server
        System.out.println("start connect");
        this.connect();
    }

    private void connect(){
        System.out.println("Trying to connect to server");
        int tries = 0;
        //connecte in schleife
        while(true) {
            if(tries < 2){
                try {
                    clientSocket = new Socket(/*"pilzschaf.noip.me"*/"192.168.2.101", 27000);
                    break;
                } catch (IOException e) {
                    try {
                        sleep(1500);
                        tries++;
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            else {
                System.out.println("Can't reach server");
                ShowCantReachServer();
                while (!dialogFinished) {
                    //retry connection
                    try {
                        sleep(100);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                if (retry) {
                    tries = 0;
                    System.out.println("retry connect");
                }
                else{
                    return;
                }
            }
        }
        sendauthdata();
    }

    private void sendauthdata(){
        Scanner     in = null;
        try {
            in = new Scanner(clientSocket.getInputStream());
            System.out.println("Erstellen des NetzwerkScanners");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter out = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("Erstellen des NetzwerkPrinters");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.println("clientinitrequest");
            System.out.println("authentification sent to server");
            System.out.println(in.nextLine());
            System.out.println("server authenticated");

        }
        catch(NullPointerException e){
            e.printStackTrace();
            System.out.println("Server hat nichts gesendet");
        }
        //Verbunden
        connected = true;
    }

    private void ShowCantReachServer(){
        retry = false;
        dialogFinished = false;
        GameData.getInstance().launcher.showConnectionLostDialog(new ConnectionLostConfirmInstance(){
            @Override
            public void retry() {
                //retry connection
                retry = true;
                dialogFinished = true;
            }

            @Override
            public void quit() {
                //quit game
                Gdx.app.exit();
                dialogFinished = true;
            }
        });
    }
}
