package com.company;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import static java.util.Objects.hash;


public class Main {
    static int n;
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        AVLTree IdTree = new AVLTree();
        AVLTree SongNameTree = new AVLTree();
        AVLTree ArtistTree = new AVLTree();

        ArrayList<Song> songList = new ArrayList<Song>();
        File file = new File("src/com/company/songs.txt");

        try {
            FileReader f = new FileReader(file);
            BufferedReader br = new BufferedReader(f);
            String lineRead;
            Song newSong = null;
            //converting file datas to meanable objects

            while ((lineRead = br.readLine()) != null) { // satır satır okur. Null satıra kadar

                String[] item = lineRead.split(";"); // ; olan yere kadar data'yı item indexlerine atar ve okunan bu şarkıları her satır atladığında songList'e atıyor.
                newSong = new Song(item[0], item[1], Integer.parseInt(item[2]), item[3], Integer.parseInt(item[4]));
                songList.add(newSong);

                if (newSong.getSongName() != null) {
                    SongNameTree.root = SongNameTree.insert(SongNameTree.root, newSong.getSongName(), hash(newSong.getSongName()));// index  key, obje
                    IdTree.root = IdTree.insert(IdTree.root, newSong.getID(), hash(newSong.getID()));
                    ArtistTree.root = ArtistTree.insert(ArtistTree.root, newSong.getArtist(), hash(newSong.getArtist()));
                }
            }
            Scanner data= new Scanner(System.in);
            Scanner data2 = new Scanner(System.in);
            while(n != 5){
                switch (n) {
                    case 1:
                        System.out.println("Enter how you want to search for a song (If by name enter 1, if by id enter 2, if by artist enter 3) :");
                        int x = data.nextInt();
                        if (x == 1) {
                            System.out.println("Enter the name of the song: ");
                            String a = data2.nextLine();
                            SongNameTree.searchString(a);
                            for (int i = 0; i < songList.size(); i++) {
                                if (songList.get(i).getSongName().equals(a)) {
                                    System.out.println("Name: " + songList.get(i).getSongName() + " Artist: " + songList.get(i).getArtist() + " ID: " + songList.get(i).getID());

                                }
                            }
                            System.out.println("\n");
                            break;

                        }
                        if (x == 2) {
                            System.out.println("Enter the id of the song:.");
                            int a = data2.nextInt();
                            IdTree.searcById(a); // searchById & searchString two diff same methods
                            for (int i = 0; i < songList.size(); i++) {
                                if (songList.get(i).getID() == a) { // Songlist de ID'si girilen id'ye eşit olursa tüm liste verilerini yazdır.
                                    System.out.println("Name: " + songList.get(i).getSongName() + " Artist: " + songList.get(i).getArtist() + " ID: " + songList.get(i).getID());

                                }
                            }
                            System.out.println("\n");
                            break;


                        }
                        if (x == 3) {
                            System.out.println("Enter the name of the artist: ");
                            String a = data2.nextLine();
                            ArtistTree.searchString(a);
                            for (int i = 0; i < songList.size(); i++) {
                                if (songList.get(i).getArtist().equals(a)) {
                                    System.out.println("Name: " + songList.get(i).getSongName() + " Artist: " + songList.get(i).getArtist() + " ID: " + songList.get(i).getID());

                                }
                            }
                            System.out.println("\n");
                            break;

                        }break;

                    case 2:
                        System.out.println("** Search by Range Display ** \n Enter the range that you'll search for : (idRange1, idRange2) ");
                        int idRange1 = scn.nextInt();
                        int idRange2 = scn.nextInt();
                        IdTree.SearchInaRange(IdTree.root, idRange1, idRange2);
                        System.out.println("\n");
                        break;

                    case 3:
                        System.out.println("** Delete a Song Display ** \n For delete song by it's song name press 1 . " +
                                " \n For delete by it's id press 2" +
                                " \n For delete by it's artist name press 3. ");
                        int a = scn.nextInt();
                        if(a == 1) {
                            Scanner s = new Scanner(System.in);
                            System.out.println("Enter Song Name for Delete : ");
                            String songNameD = s.nextLine();
                            SongNameTree.deleteNode(SongNameTree.root, songNameD);
                            SongNameTree.traverseLevelOrder(SongNameTree.root);
                            System.out.println("\n");

                        }
                        if(a == 2) {
                            System.out.println("Enter ID for Delete : ");
                            int idD = data2.nextInt();
                            IdTree.deleteNodeID(IdTree.root, idD);
                            IdTree.traverseLevelOrder(IdTree.root);
                            System.out.println("\n");

                        }
                        if(a == 3) {
                            Scanner sc = new Scanner(System.in);
                            System.out.println("Enter Artist Name for Delete : ");
                            String artistD = sc.nextLine();
                            ArtistTree.deleteNode(ArtistTree.root, artistD);
                            ArtistTree.traverseLevelOrder(ArtistTree.root);
                            System.out.println("\n");

                        } else{
                            break;
                        }


                    case 4:
                        System.out.println("Print AVL Tree's : ");
                        System.out.println("_____________SONG NAME TREE __________________\n");
                        SongNameTree.traverseLevelOrder(SongNameTree.root);
                        System.out.println("_____________ARTIST NAME TREE __________________\n");
                        ArtistTree.traverseLevelOrder(ArtistTree.root);
                        System.out.println("_____________ID TREE ________________\n");
                        IdTree.traverseLevelOrder(IdTree.root);
                        System.out.println("_______________________________\n");

                        break;


                    case 5:
                        IdTree = null;
                        SongNameTree = null;
                        ArtistTree = null;
                        songList = null;
                        System.exit(0);
                        System.out.println(" EXİT ... ");
                        break;

                }

                System.out.println("______________________________________________\n");
                printMenu();
                System.out.println("\n_____________________________________________");


            }
            scn.close();

            f.close();



            //closes the stream and release the resources


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int printMenu() {
        Scanner scn = new Scanner(System.in);
        System.out.println("What would you like to do ? (Enter a number)");
        System.out.println("0. Print Menu \n" +
                "1. Search a Song\n" +
                "2. Search by range\n" +
                "3. Delete a record from all trees\n" +
                "4. Print Tree's  \n" +
                "5. Exit\n"
        );
        n = scn.nextInt();
        return n;
    }
}