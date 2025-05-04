import java.util.Scanner;
import java.util.Random;
import java.io.File;
import javax.swing.*;

// Base character class
class Character {
    String name;
    int health;
    int attackPower;
    String avatar;

    public Character(String name, int health, int attackPower, String avatar) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.avatar = avatar;
    }

    public void attack(Character enemy) {
        int damage = new Random().nextInt(attackPower);
        enemy.health -= damage;
        System.out.println(name + " attacks " + enemy.name + " for " + damage + " damage!");
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void displayAvatar() {
        System.out.println("\nHere is your hero:");
        System.out.println(avatar);
    }
}

// Player class
class Player extends Character {
    public Player(String name, String avatar) {
        super(name, 100, 20, avatar);
    }
}

// Enemy class
class Enemy extends Character {
    public Enemy(String name) {
        super(name, 80, 15, "(>_<)");
    }
}

// Custom GIF character display
class GifCharacter {
    private String name;
    private File gifFile;

    public GifCharacter(String name, File gifFile) {
        this.name = name;
        this.gifFile = gifFile;
    }

    public void showGifAvatar() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Your Custom Hero: " + name);
            ImageIcon gifIcon = new ImageIcon(gifFile.getAbsolutePath());
            JLabel label = new JLabel(gifIcon);
            frame.add(label);
            frame.setSize(gifIcon.getIconWidth(), gifIcon.getIconHeight());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

// Main game class
public class SimpleRPG {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🌟 Welcome to Simple RPG! 🌟");
        System.out.print("Enter your name, hero: ");
        String playerName = scanner.nextLine();

        // Avatar selection
        String[] avatars = {
            "⚔️ (•_•)  \n /|\\    \n / \\    ",       // Knight
            "🧙‍♂️ (∩｀-´)⊃━☆ﾟ.*･｡ﾟ\n /|\\    \n / \\    ", // Wizard
            "🏹 (¬‿¬)つ──☆*:・ﾟ\n /|>    \n / \\    ",     // Archer
            "🐱 (=^-ω-^=)  \n /|\\     \n / \\     "        // Cat hero 😸
        };

        System.out.println("\nChoose your character style:");
        System.out.println("1. Knight ⚔️");
        System.out.println("2. Wizard 🧙‍♂️");
        System.out.println("3. Archer 🏹");
        System.out.println("4. Cat Hero 🐱");

        int choice = 0;
        while (choice < 1 || choice > 4) {
            System.out.print("Enter 1-4: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❗ Please enter a number from 1 to 4.");
            }
        }

        String avatar = avatars[choice - 1];
        Player player = new Player(playerName, avatar);
        player.displayAvatar();

        // Offer GIF upload
        System.out.print("\nDo you want to upload a custom GIF avatar? (yes/no): ");
        String uploadChoice = scanner.nextLine().toLowerCase();

        if (uploadChoice.equals("yes")) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Your GIF Avatar");
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File gifFile = fileChooser.getSelectedFile();
                    GifCharacter gifPlayer = new GifCharacter(playerName, gifFile);
                    gifPlayer.showGifAvatar();
                } else {
                    System.out.println("🚫 No file selected. Using default avatar.");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error showing GIF: " + e.getMessage());
            }
        }

        Enemy enemy = new Enemy("Dark Goblin");
        System.out.println("\n⚔️ A wild " + enemy.name + " appears!");

        // Game loop
        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\nChoose your action:");
            System.out.println("1. Attack");
            System.out.println("2. Run (Quit)");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                player.attack(enemy);
                if (enemy.isAlive()) {
                    enemy.attack(player);
                } else {
                    System.out.println("\n🎉 You defeated the " + enemy.name + "!");
                }
            } else if (input.equals("2")) {
                System.out.println("\n🏃 You ran away! Game over.");
                break;
            } else {
                System.out.println("❓ Invalid action!");
            }

            System.out.println("\n" + player.name + " HP: " + player.health);
            System.out.println(enemy.name + " HP: " + enemy.health);
        }

        if (!player.isAlive()) {
            System.out.println("\n💀 You were defeated by the " + enemy.name + "...");
        }

        System.out.println("👋 Thanks for playing!");
        scanner.close();
    }
}
