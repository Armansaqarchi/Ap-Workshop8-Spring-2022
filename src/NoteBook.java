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

    /**
     * takes a note and adds it to the Notebook
     * @param note, the note has to be added to the notebook
     */
    private void addNotes(Note note) {

        //checks the note if its null... why?
        //because parameter comes from output of create note function...
        //so it might be null if the user regrets it
        if(note == null){
            return;
        }
        //checking the title to see if it exists in the notebook or not..
        //because every title which is created, is unique
        if(!titleExist(note.getTitle())) {
            notes.add(note);
        }
        else{
            System.out.println(note.getTitle() + " already exists");
        }
    }

    /**
     * this method gets title and returns note that has the same name as title...
     * @param title, title of note
     * @return note whose title is the same as title entered as an input
     */
    private Note getNoteByTitle(String title){

        //searching whole the notes in order to find it
        for(Note i : notes){
            if(i.getTitle().equals(title)){
                return i;
            }
        }

        //if the program reaches there... it means it could not find any match.
        return null;
    }

    /**
     * takes the title and remove the note if it does exist...
     * @param title, title of the note which has to be removed
     */
    private void remoteNote(String title){
        //checks title of note to see if it exists
        if(titleExist(title)){
            //removes the note
            notes.remove(getNoteByTitle(title));
            System.out.println("note has been successfully removed");
        }
        else{
            //the title was not found so it cannot remove
            System.out.println(title + " doesnt exist in the note book");
        }
    }

    /**
     * prints the note into a text file with the same name as note's title
     * @param note, note which has to be printed in a text file
     */
    private void export(Note note){

        //trying to open the folder
        //attention that if file opening leads to a failure by any cause... there would be an IO problem
        //IO refers to the any attempt for reading from and writing into the file...


        //and in these cases... a good choice is to use "try with resources" technique
        //so if the opening leads to a problem... all the files declared inside the try will be closed automatically
        try(FileWriter fout = new FileWriter("src/all the notes/" + note.getTitle() + ".txt" )){



            //to write the object inside the file
            fout.write(note.getNote());

            //closing the file
            fout.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * this method gets a note in the notebook and prints it into a text file
     */
    private void chooseExportNote(){

        //creating a scanner instance
        Scanner scanner = new Scanner(System.in);

        //invoking "notes" method which prints all the notes in a list
        notes();
        System.out.println("enter note you want to export :");
        System.out.println("you can cancel that by pressing '0' ");

        int index = -1;

        //getting input while the input is not valid
        do{
            index = getSafeInput();
        }
        while(index < 0 || index > notes.size());

        if(index == 0){
            return;
        }
        export(notes.get(index - 1));
    }

    /**
     * this method gets a number safely... what is the meaning of safely?
     * it means that input will not cause an error or an exception in our code...
     * so it's simple... all it does is handling the possible exceptions while scanning
     *
     * @return the input
     */
    private int getSafeInput(){
        Scanner scanner = new Scanner(System.in);

        int input = 0;
        try{
            input = Integer.parseInt(scanner.nextLine());
        }
        catch(NumberFormatException e){
            System.out.println("invalid input");
        }

        return input;
    }

    /**
     * prints all the notes existing in a notebook
     */
    private void notes(){
        for(int i = 0; i < notes.size(); i++){
            System.out.println(i + 1 + ". " + notes.get(i).getTitle());
        }

        System.out.println();
    }

    //gets a title and search the whole notebook to see if note with such title exists
    private boolean titleExist(String title){
        for(Note i : notes){
            if (i.getTitle().equals(title)) {
                //returns true if it exists
                return true;
            }
        }

        return false;
    }


    private void saveNotes(){
        try(FileOutputStream fout = new FileOutputStream("src/all the notes/save.txt");
            ObjectOutputStream out = new ObjectOutputStream(fout)){

            for(Note i : notes){
                out.writeObject(i);
            }

            fout.close();
            out.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * gets a note of notebook by getting its index
     */
    private void getNote(){

        Scanner scanner = new Scanner(System.in);
        int choose = 0;

        //while input taken by the user is not valid... the program keeps scanning
        do{

            //showing all the notes
            notes();
            System.out.println(notes.size() + 1 + ". cancel");
            System.out.println("enter number of note");

            //getting the input with handling possible exceptions
            try {
                choose = Integer.parseInt(scanner.nextLine());
                if (choose > notes.size() + 1 || choose < 1) {
                    System.out.println("invalid input");
                }
            }
            catch(NumberFormatException e){
                System.out.println("invalid input");
            }

            if(choose == notes.size() + 1){
                return;
            }

            if(choose >= 1  && choose <= notes.size() + 1) {
                System.out.println(notes.get(choose - 1).getNote());
            }
            else{
                System.out.println("index is not valid");
            }
        }
        while(choose > notes.size() + 1 || choose < 1);



    }


    /**
     * run method to perform all the methods and applications of them
     * method has 5 option you can see below
     */
    public void run(){
        Scanner scanner = new Scanner(System.in);
        int choose = 0;


        do{

            System.out.println("[1] Add");
            System.out.println("[2] Remove");
            System.out.println("[3] Notes");
            System.out.println("[4] Export");
            System.out.println("[5] Exit");


            try {
                choose = Integer.parseInt(scanner.nextLine());
            }
            catch(NumberFormatException e){
                System.out.println("invalid input");
            }

            switch(choose){
                case 1:
                    //add method
                    Note newNote = createNote();
                    addNotes(newNote);
                    break;
                case 2:
                    //remove method
                    System.out.println("enter title of note : ");
                    String title = scanner.nextLine();
                    remoteNote(title);
                    break;
                case 3:
                    getNote();
                    break;
                case 4:
                    chooseExportNote();
                    break;
                case 5:
                    saveNotes();


            }
        }
        while(choose != 5);





    }


}
