import java.util.Scanner;
import java.util.Random;

// Base character class
class Character {
    String name;
    int health;
    int attackPower;

    public Character(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public void attack(Character enemy) {
        int damage = new Random().nextInt(attackPower);
        enemy.health -= damage;
        System.out.println(name + " attacks " + enemy.name + " for " + damage + " damage!");
    }

    public boolean isAlive() {
        return health > 0;
    }
}

// Player class
class Player extends Character {
    public Player(String name) {
        super(name, 100, 20);
    }
}

// Enemy class
class Enemy extends Character {
    public Enemy(String name) {
        super(name, 80, 15);
    }
}

// Main game class
public class SimpleRPG {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🌟 Welcome to Simple RPG! 🌟");
        System.out.print("Enter your name, hero: ");
        String playerName = scanner.nextLine();

        Player player = new Player(playerName);
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
