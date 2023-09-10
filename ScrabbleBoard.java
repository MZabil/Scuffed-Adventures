import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


import javax.swing.border.EmptyBorder;

public class ScrabbleBoard extends JFrame  {
    private Clip animationSoundClip; 
    private JPanel letterRackPanel;
    private JPanel selectedLettersPanel;
    private JPanel topContainerPanel;
    private JLabel selectedLetterLabel;
    private HashSet<String> wordList;
    StringBuilder stringBuilder = new StringBuilder();
    protected boolean turn;
    private JLabel player1HPLabel;
    private JLabel player2HPLabel;
    private int user_hp=100;
    private int opponent_hp=100;
    private JPanel hpPanel;
    private JPanel removeButtonPanel;
    private JPanel bottomContainerPanel;
    // Initialize the available letters
    List<String> availableLetters = new ArrayList<>(Arrays.asList(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    ));
    public ScrabbleBoard() {
        
        // Set up the JFrame
        JFrame my=new JFrame("Hi");
        //setTitle("Scrabble Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        //INSERT MAIN BACKGROUND HERE
        ImageIcon backgroundImage = new ImageIcon("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\Main Background.jpg");
         Image image3 = backgroundImage.getImage();
        Image resizedImage3 = image3.getScaledInstance(1600, 1000, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon3 = new ImageIcon(resizedImage3);
        JLabel backgroundLabel = new JLabel(new ImageIcon(resizedImage3));
            

        // Set layout for the background label
        backgroundLabel.setLayout(new BorderLayout());

        // Add the background label to the main frame
        setContentPane(backgroundLabel);
        
        
        wordList = new HashSet<>();
        ReadDic(wordList);

        setResizable(false); // Set window resizable to false

        final int boardSize = 4; // size of the board
        JButton[][] tiles = new JButton[boardSize][boardSize];

        // Create the top container panel with GridBagLayout
        topContainerPanel = new JPanel(new GridBagLayout());
        //topContainerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a border
        topContainerPanel.setOpaque(false);
        add(topContainerPanel, BorderLayout.CENTER);

        // Configure GridBagConstraints for component placement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Allow components to fill the space
        gbc.insets = new Insets(5, 5, 5, 5); // Add insets for desired spacing

        // Create the selected letters panel
        selectedLettersPanel = new JPanel();
        selectedLettersPanel.setLayout(new GridLayout(1, boardSize));
        topContainerPanel.add(selectedLettersPanel);

        // Create the letter rack panel
        letterRackPanel = new JPanel();
        letterRackPanel.setLayout(new GridLayout(4, 4));
        letterRackPanel.setOpaque(false);
        add(letterRackPanel, BorderLayout.SOUTH);

        // Add letters to the letter rack
        Collections.shuffle(availableLetters);

        //Generate Buttons
        generateButtons(availableLetters);



        // Creating the remove button that removes the selected letter from the upper container
        JButton removeButton = new JButton();
        removeButton.setPreferredSize(new Dimension(100, 50));
        ImageIcon Button = new ImageIcon("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\Remove.png");
         Image ButtonImage = Button.getImage();
        Image ResizedButton = ButtonImage.getScaledInstance(450, 150, Image.SCALE_SMOOTH);
        ImageIcon ResizedButtonIcon = new ImageIcon(ResizedButton);
        removeButton.setIcon(ResizedButtonIcon);
        removeButton.setLayout(new BorderLayout());
            
            
        removeButton.addActionListener(e -> {
            
        playSoundEffect("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\videoplayback.wav");
            //Remove the last entered string from the main word string and keep it equal to label aswell
            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                System.out.println(stringBuilder);
            }

            int componentCount = selectedLettersPanel.getComponentCount();
            if (componentCount > 0) {
                Component lastComponent = selectedLettersPanel.getComponent(componentCount - 1);
                selectedLettersPanel.remove(lastComponent);
                letterRackPanel.add(createLetterButton(((JLabel) lastComponent).getText()));
                letterRackPanel.revalidate();
                letterRackPanel.repaint();
                selectedLettersPanel.revalidate();
                selectedLettersPanel.repaint();
                pack();
            }
        });

        JButton shuffleButton = new JButton();
        shuffleButton.setPreferredSize(new Dimension(100, 50));
       ImageIcon button = new ImageIcon("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\Shuffle.png");
         Image buttonImage = button.getImage();
        Image resizedButton = buttonImage.getScaledInstance(450, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedButtonIcon = new ImageIcon(resizedButton);
        shuffleButton.setIcon(resizedButtonIcon);
        
            
        shuffleButton.addActionListener(e -> {
            playSoundEffect("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\videoplayback.wav");

            //Only clickable if the String/label is empty
            if (stringBuilder.length() > 0) {
                JOptionPane.showMessageDialog(null, "Please remove all text from field by pressing the remove button ");
            }
            else{
                shuffle(availableLetters);
            }


        });

      
        
        JButton attackButton = new JButton();
        attackButton.setPreferredSize(new Dimension(100, 50));
       ImageIcon button1 = new ImageIcon("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\Attack.png");
         Image buttonImage1 = button1.getImage();
        Image resizedButton1 = buttonImage1.getScaledInstance(450, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedButtonIcon1 = new ImageIcon(resizedButton1);
        attackButton.setIcon(resizedButtonIcon1);
        attackButton.setLayout(new BorderLayout());
            
            
        attackButton.addActionListener(e -> {

             playSoundEffect("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\videoplayback.wav");

        
        
            String formedWord = stringBuilder.toString();
            //Player 1 turn
            if (turn == true && formedWord.length() >=3 && wordList.contains(formedWord) ) {
                 
                
                
                user_hp = Attack(wordList, formedWord, user_hp,1);

                stringBuilder.delete(0, stringBuilder.length());

                // Update the Player1 Hp label
                player1HPLabel.setText("Player 1 HP: " + user_hp);

                // Clear the selectedletter container panel
                selectedLettersPanel.removeAll();
                selectedLettersPanel.revalidate();
                selectedLettersPanel.repaint();

                // Clear label text
                selectedLetterLabel.setText("");
                topContainerPanel.add(selectedLetterLabel);

                shuffle(availableLetters);
                turn = false;
            }
            //Player 2 turn
            else if (turn == false && formedWord.length()>=3  && wordList.contains(formedWord)) {

                playSoundEffect("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\videoplayback.wav");


                opponent_hp = Attack(wordList,formedWord,opponent_hp, 2);
                stringBuilder.delete(0, stringBuilder.length());


                // //Player2 HP label
                player2HPLabel.setText("Player 2 HP: " + opponent_hp);

                // Clear the selectedletter container panel
                selectedLettersPanel.removeAll();
                selectedLettersPanel.revalidate();
                selectedLettersPanel.repaint();


                // Clear label text
                selectedLetterLabel.setText("");
                topContainerPanel.add(selectedLetterLabel);

                shuffle(availableLetters);

                turn = true;
            }
            else{
                JOptionPane.showMessageDialog(null, "The word does not exist");
            }




            // Pack the JFrame to adjust its size if necessary
            pack();


        });



        // Create empty panels to position the top container panel in the center
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setOpaque(false);
        rightPanel.setOpaque(false);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);


        // Create a separate panel to confine the letter rack to the middle
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.add(letterRackPanel);
        add(bottomPanel, BorderLayout.PAGE_END);

        // Create a panel to hold the HP labels
        hpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        hpPanel.setOpaque(false);

            
// Create HP labels
        player1HPLabel = new JLabel("Player 1 HP: 100");
        player1HPLabel.setOpaque(false);
        player2HPLabel = new JLabel("Player 2 HP: 100");
        player2HPLabel.setOpaque(false);

// Set font and alignment for HP labels
        player1HPLabel.setFont(new Font("Arial", Font.BOLD, 20));
        player2HPLabel.setFont(new Font("Arial", Font.BOLD, 20));
        player1HPLabel.setForeground(Color.WHITE);
        player2HPLabel.setForeground(Color.WHITE);
        player1HPLabel.setHorizontalAlignment(SwingConstants.LEFT);
        player2HPLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Add spacing between HP labels
        hpPanel.add(Box.createHorizontalStrut(10)); // Adjust the spacing as needed
        hpPanel.add(player1HPLabel);
        hpPanel.add(Box.createHorizontalStrut(80)); // Adjust the spacing as needed
        hpPanel.add(player2HPLabel);

        // Create a panel for the remove button
        removeButtonPanel = new JPanel(new GridLayout(0, 1));
        removeButtonPanel.setOpaque(false);
        removeButtonPanel.add(removeButton);
        removeButtonPanel.add(shuffleButton);
        removeButtonPanel.add(attackButton);
        removeButtonPanel.add(hpPanel);

// Create a container panel for the bottom components
        bottomContainerPanel = new JPanel(new BorderLayout());
        bottomContainerPanel.add(removeButtonPanel, BorderLayout.WEST);
        bottomContainerPanel.add(bottomPanel, BorderLayout.CENTER);
        bottomContainerPanel.setOpaque(false);
        add(bottomContainerPanel, BorderLayout.PAGE_END);

        // Set the preferred size of the JFrame and pack its components
        setPreferredSize(new Dimension(1600, 1000));
        pack();
        setVisible(true);
    }
    private Border createThickBorder(Color color) {
        int thickness = 5;
        Border lineBorder = BorderFactory.createLineBorder(color, thickness);
        Border emptyBorder = new EmptyBorder(thickness, thickness, thickness, thickness);
        return new CompoundBorder(lineBorder, emptyBorder);
    }

    private JButton createLetterButton(String letter) {
        
        JButton letterButton = new JButton(letter);
        letterButton.setPreferredSize(new Dimension(100, 100));
        ImageIcon icon = new ImageIcon("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\WOOD.png");
            letterButton.setIcon(icon);
            letterButton.setLayout(new BorderLayout());
            JLabel label = new JLabel(letter);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            letterButton.add(label);
        letterButton.addActionListener(e -> {
            playSoundEffect("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\videoplayback.wav");

            JButton selectedButton = (JButton) e.getSource();
            String selectedLetter = selectedButton.getText();

            //Add the letters to a string
            stringBuilder.append(selectedLetter);
            System.out.println(stringBuilder);

            selectedLetterLabel = new JLabel(selectedLetter);
            selectedLetterLabel.setHorizontalAlignment(SwingConstants.CENTER);
            selectedLetterLabel.setPreferredSize(new Dimension(50, 50));


            selectedLettersPanel.add(selectedLetterLabel);
            letterRackPanel.remove(selectedButton);
            letterRackPanel.revalidate();
            letterRackPanel.repaint();
            selectedLettersPanel.revalidate();
            selectedLettersPanel.repaint();
            pack();
        });
        return letterButton;
    }

    private void shuffle(List<String> availableLetters) {
        letterRackPanel.removeAll();
        Collections.shuffle(availableLetters);
        generateButtons(availableLetters);
        letterRackPanel.revalidate();
        letterRackPanel.repaint();
    }


    private void generateButtons(List<String> availableLetters)
    {
        for (int i = 0; i < 16; i++) {
            JButton letterButton = new JButton(availableLetters.get(i));
            letterButton.setPreferredSize(new Dimension(100, 100));
            ImageIcon icon = new ImageIcon("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\WOOD.png");
            letterButton.setIcon(icon);
            letterButton.setLayout(new BorderLayout());
            JLabel label = new JLabel(availableLetters.get(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            letterButton.add(label);
            letterRackPanel.add(letterButton);
            

            letterButton.addActionListener(e -> {

                playSoundEffect("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\videoplayback.wav");


                JButton selectedButton = (JButton) e.getSource();
                String selectedLetter = selectedButton.getText();

                //Add the letters to a string
                stringBuilder.append(selectedLetter);
                //System.out.println(stringBuilder);

                // Create a label to display the selected letter
                selectedLetterLabel = new JLabel(selectedLetter);
                selectedLetterLabel.setHorizontalAlignment(SwingConstants.CENTER);
                selectedLetterLabel.setPreferredSize(new Dimension(50, 50));

                // Add the selected letter label to the selected letters panel
                selectedLettersPanel.add(selectedLetterLabel);

                // Remove the selected letter button from the letter rack
                letterRackPanel.remove(selectedButton);

                // Repaint the panels to update the changes
                letterRackPanel.revalidate();
                letterRackPanel.repaint();
                selectedLettersPanel.revalidate();
                selectedLettersPanel.repaint();

                // Pack the JFrame to adjust its size if necessary
                pack();
            });
        }
        
    }

    private void playSoundEffect(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

     // Method to play the animation sound effect
    private void playAnimationSound(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            animationSoundClip = AudioSystem.getClip();
            animationSoundClip.open(audioInputStream);
            animationSoundClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    //Dictionary Reader
    private static void ReadDic(HashSet<String>wordsList)
    {

        try {
            // specify the file path
            File file = new File("Dictionary.txt");

            // create a scanner to read from the file
            Scanner read = new Scanner(file);

            // read each word from the file and add it to the list
            while (read.hasNextLine()) {
                String word = read.nextLine().trim().toUpperCase();
                wordsList.add(word.toUpperCase());
            }


            // close the scanner
            read.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public int Attack(HashSet<String> wordsList, String formedWord, int user_hp, int playerNo) {
        
        //Adding attack animations
        JFrame frame = new JFrame("Kamehameha Animation");
       frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        
        
        //Calling player 1 animation
        if(playerNo!=1){
        frame.add(new KamehamehaAnimation());
        frame.setVisible(true);
        }
        //Calling player 2 animation
        else{
        frame.add(new KamehamehaAnimation2());
        frame.setVisible(true);
        }
        
        if (formedWord.length() >= 3) {
            if (wordsList.contains(formedWord)) {
                user_hp -= 10;

            }
        } else if (formedWord.length() >= 4) {
            if (wordsList.contains(formedWord)) {
                user_hp -= 20;

            }
        } else if (formedWord.length() >= 7) {
            if (wordsList.contains(formedWord)) {
                user_hp -= 30;

            }
        }

        return user_hp;

    }


}

//Attack animation for player 1
class KamehamehaAnimation extends JPanel {

    private int x, y; // Position of the Kamehameha wave
    private int waveLength; // Length of the Kamehameha wave
    private Timer timer;
    private Image backgroundImage; // Background image
    private Image leftImage; // Left side image
    private Image rightImage; // Right side image

    public KamehamehaAnimation() {
        x = 0; // Initial x-position
        y = 200; // Initial y-position
        waveLength = 0; // Initial wave length
        playSoundEffect("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\Kamehameha.wav");
        // Load the background image (replace "background.jpg" with your own image path)
        backgroundImage = new ImageIcon("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\Animation Background.jpg").getImage();
        // Load the left and right side images (replace "leftImage.jpg" and "rightImage.jpg" with your own image paths)
        leftImage = new ImageIcon("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\Character2.png").getImage();
        rightImage = new ImageIcon("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\Character1.png").getImage();

        timer = new Timer(10, e -> {
            x += 5; // Move the wave to the right
            waveLength += 10; // Increase the wave length

            if (x > getWidth() + waveLength) {
                timer.stop(); // Stop the animation when the wave goes off the screen
            }

            repaint(); // Redraw the panel
        });

        timer.start(); // Start the animation

        
    }
private void playSoundEffect(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(50)); // Set the stroke width to 50 pixels
        
         int leftImageY = (getHeight() - leftImage.getHeight(null)) / 2; // Calculate the y-coordinate for the left image
        int rightImageY = (getHeight() - rightImage.getHeight(null)) / 2; // Calculate the y-coordinate for the right image

        // Draw the left side image
        g.drawImage(leftImage, 0, leftImageY, null);
        // Draw the right side image
        g.drawImage(rightImage, getWidth() - rightImage.getWidth(null), rightImageY, null);

        // Draw the Kamehameha wave
        g2d.setColor(Color.BLUE);
        g2d.drawLine(x, y, x + waveLength, y);
    }
}

//Attack animation for player 2
class KamehamehaAnimation2 extends JPanel {

    private int x, y; // Position of the Kamehameha wave
    private int waveLength; // Length of the Kamehameha wave
    private Timer timer;
    private Image backgroundImage; // Background image
    private Image leftImage; // Left side image
    private Image rightImage; // Right side image

    public KamehamehaAnimation2() {
        x = 1000; // Initial x-position
        y = 200; // Initial y-position
        waveLength = 0; // Initial wave length
        playSoundEffect("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\Kamehameha.wav");

        // Load the background image (replace "background.jpg" with your own image path)
        backgroundImage = new ImageIcon("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\Animation Background.jpg").getImage();
        // Load the left and right side images (replace "leftImage.jpg" and "rightImage.jpg" with your own image paths)
        leftImage = new ImageIcon("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\Character2.png").getImage();
        rightImage = new ImageIcon("D:\\University Stuff\\Semester 2\\Object Oriented Programming\\Project\\Scuffed_Adventures\\src\\resources\\Character1.png").getImage();

        timer = new Timer(10, e -> {
            x -= 5; // Move the wave to the left
            waveLength += 10; // Increase the wave length

            if (x + waveLength < 0) {
                timer.stop(); // Stop the animation when the wave goes off the screen
            }

            repaint(); // Redraw the panel
        });

        timer.start(); // Start the animation
    }
    private void playSoundEffect(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(50)); // Set the stroke width to 50 pixels

        int leftImageY = (getHeight() - leftImage.getHeight(null)) / 2; // Calculate the y-coordinate for the left image
        int rightImageY = (getHeight() - rightImage.getHeight(null)) / 2; // Calculate the y-coordinate for the right image

        // Draw the left side image
        g.drawImage(leftImage, 0, leftImageY, null);
        // Draw the right side image
        g.drawImage(rightImage, getWidth() - rightImage.getWidth(null), rightImageY, null);

        // Draw the Kamehameha wave
        g2d.setColor(Color.RED);
        g2d.drawLine(x, y, x - waveLength, y);
    }

    
}
