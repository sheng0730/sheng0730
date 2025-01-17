import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class CoinRPG {
    private JFrame frame;
    private JTextArea storyArea;
    private JLabel playerStatus, coinLabel;
    private JButton fightButton, shopButton, taskButton;
    private Character player;
    private int coins = 100; // 初始金币

    public CoinRPG() {
        initializeGame();
        createUI();
    }

    private void initializeGame() {
        String playerName = JOptionPane.showInputDialog("请输入角色的名字：");
        player = new Character(playerName, "冒险者");
    }

    private void createUI() {
        frame = new JFrame("币值 RPG 游戏");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        frame.setLayout(new BorderLayout());

        // 故事区域
        storyArea = new JTextArea();
        storyArea.setEditable(false);
        storyArea.setLineWrap(true);
        storyArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(storyArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // 状态栏
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(2, 1));
        playerStatus = new JLabel("玩家状态");
        coinLabel = new JLabel("金币: " + coins);
        statusPanel.add(playerStatus);
        statusPanel.add(coinLabel);
        frame.add(statusPanel, BorderLayout.NORTH);

        // 按钮区域
        JPanel buttonPanel = new JPanel();
        fightButton = new JButton("战斗");
        shopButton = new JButton("商店");
        taskButton = new JButton("接受任务");
        buttonPanel.add(fightButton);
        buttonPanel.add(shopButton);
        buttonPanel.add(taskButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // 按钮事件
        fightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fightEnemy();
            }
        });

        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShop();
            }
        });

        taskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acceptTask();
            }
        });

        updateStatus();
        frame.setVisible(true);
    }

    private void fightEnemy() {
        Random random = new Random();
        int reward = random.nextInt(50) + 10; // 随机奖励
        coins += reward;
        appendStory("你击败了敌人，获得了 " + reward + " 金币！");
        updateStatus();
    }

    private void openShop() {
        String[] items = {"药水 - 20 金币", "武器 - 50 金币", "护甲 - 80 金币"};
        String selected = (String) JOptionPane.showInputDialog(
                frame,
                "请选择想购买的物品：",
                "商店",
                JOptionPane.QUESTION_MESSAGE,
                null,
                items,
                items[0]
        );

        if (selected != null) {
            int cost = 0;
            if (selected.contains("药水")) cost = 20;
            else if (selected.contains("武器")) cost = 50;
            else if (selected.contains("护甲")) cost = 80;

            if (coins >= cost) {
                coins -= cost;
                appendStory("你购买了 " + selected.split(" ")[0] + "，花费了 " + cost + " 金币。");
            } else {
                appendStory("金币不足，无法购买！");
            }
        }

        updateStatus();
    }

    private void acceptTask() {
        appendStory("你接受了一个新任务，完成后会获得奖励！");
    }

    private void updateStatus() {
        playerStatus.setText("玩家 - 名字: " + player.getName() + " | HP: " + player.getHp());
        coinLabel.setText("金币: " + coins);
    }

    private void appendStory(String text) {
        storyArea.append(text + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CoinRPG::new);
    }
}

class Character {
    private String name;
    private int hp;

    public Character(String name, String charClass) {
        this.name = name;
        this.hp = 100;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }
}