import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class NoteBook {

    //creating a Singleton class so there is only one instance of notebook
    private static NoteBook noteBook = null;
    private ArrayList<Note> notes;

    //constructor of singleton class has to be private in order to
    // get controlled by one of its method and not by the user
    private NoteBook(){
        notes = new ArrayList<>();
    }


    /**
     * creates new instance only if it's the first time creating, else returns notebook
     *
     * @return instance created for the first time
     */
    public static NoteBook getInstance(){
        if(noteBook == null){
            noteBook = new NoteBook();
        }

        return noteBook;

    }

    private ArrayList<Note> getNotes() {
        return notes;
    }

    /**
     * this method creates a note and gets all are needed.
     *
     * @return the note that is created inside it
     */
    private Note createNote(){
        Scanner scanner = new Scanner(System.in);

        //gets the title of note
        System.out.println("enter title of note or you can cancel by entering 'NONE'");
        String title = scanner.nextLine();

        //checks if the user entered NONE? because it means user does not tend to create a new one
        if(title.equals("NONE")){
            return null;
        }

        //gets note and contains of a note
        System.out.println("write a note : ");
        String note = scanner.nextLine();

        return new Note(title, note);

    }

}
