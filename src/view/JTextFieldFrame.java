package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;

public class JTextFieldFrame extends JFrame {
    JPanel root;
    JLabel limitLabel,goalLabel;
    private static int limit,goal;
    JTextField limitTextField,goalTextField;
    JButton confirmButton;
    public JTextFieldFrame(){
        root = new JPanel();
        setContentPane(root);
        setLayout(null);
        //限制步数标签
        limitLabel = new JLabel("限制步数：");
        limitLabel.setBounds(52,33,64,15);
        root.add(limitLabel);
        //限制步数文本框
        limitTextField = new JTextField(12);
        limitTextField.setBounds(116,30,139,21);
        root.add(limitTextField);
        //目标分数标签
        goalLabel = new JLabel("目标分数：");
        goalLabel.setBounds(52,74,64,15);
        root.add(goalLabel);
        //目标分数文本框
        goalTextField = new JTextField(12);
        goalTextField.setBounds(116,71,139,21);
        root.add(goalTextField);
        //确认按钮
        confirmButton = new JButton("确认");
        confirmButton.setBounds(136,116,69,23);
        confirmButton.addActionListener(e -> {
            limit = Integer.parseInt(limitTextField.getText());
            goal = Integer.parseInt(goalTextField.getText());
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController((810 * 4 / 5) / 9, mainFrame.getChessboardComponent(), new Chessboard());
            mainFrame.setGameController(gameController);
            gameController.setScoreLabel(mainFrame.getScoreLabel());
            gameController.setSwaplimitLabel(mainFrame.getSwaplimitLabel());
            gameController.setgoalLabel(mainFrame.getGoalLabel());
            GameController.setSwaplimit(getLimit());
            GameController.setGoal(getGoal());
            mainFrame.setVisible(true);
            setVisible(false);
        });
        root.add(confirmButton);
        //设置窗口风格
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(400,300,340,245);
        setVisible(true);
    }

    public static int getLimit() {
        return limit;
    }


    public static int getGoal() {
        return goal;
    }

}
