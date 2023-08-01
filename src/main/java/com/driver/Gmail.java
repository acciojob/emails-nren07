package com.driver;

import java.time.LocalTime;
import java.util.*;

public class Gmail extends Email {

    int inboxCapacity; //maximum number of mails inbox can store
    //Inbox: Stores mails. Each mail has date (Date), sender (String), message (String). It is guaranteed that message is distinct for all mails.
    //Trash: Stores mails. Each mail has date (Date), sender (String), message (String)
    PriorityQueue<Message> inbox;
    PriorityQueue<Message>trash;

    public void setInboxCapacity(int inboxCapacity) {
        this.inboxCapacity = inboxCapacity;
    }

    public PriorityQueue<Message> getInbox() {
        return inbox;
    }

    public void setInbox(PriorityQueue<Message> inbox) {
        this.inbox = inbox;
    }

    public PriorityQueue<Message> getTrash() {
        return trash;
    }

    public void setTrash(PriorityQueue<Message> trash) {
        this.trash = trash;
    }

    public Gmail(String emailId, int inboxCapacity) {
        super(emailId);
        this.inboxCapacity=inboxCapacity;
        this.inbox=new PriorityQueue<>(new Comparator<Message>() {
            @Override
            public int compare(Message a, Message b) {
                return a.getDate().compareTo(b.getDate());
            }
        });
        this.trash=new PriorityQueue<>(new Comparator<Message>() {
            @Override
            public int compare(Message a, Message b) {
                return a.getDate().compareTo(b.getDate());
            }
        });
    }

    public void receiveMail(Date date, String sender, String message){
        // If the inbox is full, move the oldest mail in the inbox to trash and add the new mail to inbox.
        // It is guaranteed that:
        // 1. Each mail in the inbox is distinct.
        // 2. The mails are received in non-decreasing order. This means that the date of a new mail is greater than equal to the dates of mails received already.
        if(inbox.size()>=inboxCapacity){
            trash.add(inbox.remove());
        }
        inbox.add(new Message(date,sender,message));
    }

    public void deleteMail(String message){
        // Each message is distinct
        // If the given message is found in any mail in the inbox, move the mail to trash, else do nothing

        Queue<Message>q=new LinkedList<>();
        while(inbox.size()!=0){
            Message msg=inbox.remove();
            if(msg.getMessage().equals(message)){
                break;
            }else{
                q.add(msg);
            }
        }
        while(q.size()!=0){
            inbox.add(q.remove());
        }

    }

    public String findLatestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the latest mail present in the inbox

        Queue<Message>q=new LinkedList<>();
        while(inbox.size()!=1){
            Message msg=inbox.remove();
            q.add(msg);
        }
        Message msg=inbox.peek();
        inbox.remove();
        q.add(msg);
        while(q.size()!=0){
            inbox.add(q.remove());
        }
        inbox.add(msg);
        return msg.getMessage();

    }

    public String findOldestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the oldest mail present in the inbox
        if(inbox.size()==0) return "null";
        return inbox.peek().getMessage();

    }

    public int findMailsBetweenDates(Date start, Date end){
        //find number of mails in the inbox which are received between given dates
        //It is guaranteed that start date <= end date
        int cnt=0;
        Queue<Message>q=new LinkedList<>();
        while(inbox.size()!=0){
            Message msg=inbox.remove();
//            Date date=msg.getDate();
//            int startFlag=msg.getDate().compareTo(start);
//            int endFlag=end.compareTo(msg.getDate());
            if(msg.getDate().compareTo(start)>=0 && end.compareTo(msg.getDate())>=0){
                cnt++;
            }
            q.add(msg);
        }
        while(q.size()!=0){
            inbox.add(q.remove());
        }
        return cnt;

    }

    public int getInboxSize(){
        // Return number of mails in inbox
        return inbox.size();

    }

    public int getTrashSize(){
        // Return number of mails in Trash
        return trash.size();

    }

    public void emptyTrash(){
        // clear all mails in the trash
        trash.clear();

    }

    public int getInboxCapacity() {
        // Return the maximum number of mails that can be stored in the inbox
        return inboxCapacity;
    }
}
