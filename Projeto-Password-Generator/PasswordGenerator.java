import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.util.Random;

public class PasswordGenerator extends JFrame {

    private JCheckBox upperCaseCheckBox;
    private JCheckBox lowerCaseCheckBox;
    private JCheckBox numbersCheckBox;
    private JCheckBox specialCharsCheckBox;
    private JTextField lengthField;
    private JTextArea resultArea;
    private JButton copyButton;

    public PasswordGenerator() {
        setTitle("Gerador de Senhas");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));

        upperCaseCheckBox = new JCheckBox("Incluir Letras Maiúsculas - ABC");
        lowerCaseCheckBox = new JCheckBox("Incluir Letras Minúsculas - abc");
        numbersCheckBox = new JCheckBox("Incluir Números - 123");
        specialCharsCheckBox = new JCheckBox("Incluir Caracteres Especiais - (!@#$%&*()-+.,;?{[}]^><:)");

        panel.add(upperCaseCheckBox);
        panel.add(lowerCaseCheckBox);
        panel.add(numbersCheckBox);
        panel.add(specialCharsCheckBox);

        JPanel lengthPanel = new JPanel();
        lengthPanel.setLayout(new FlowLayout());
        lengthPanel.add(new JLabel("Comprimento da Senha (8-20):"));
        lengthField = new JTextField(5);
        lengthPanel.add(lengthField);
        panel.add(lengthPanel);

        JButton generateButton = new JButton("Gerar Senha");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePassword();
            }
        });
        panel.add(generateButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(350, 100));

        copyButton = new JButton("Copiar Senha");
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyToClipboard();
            }
        });
        panel.add(copyButton);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void generatePassword() {
        if (!upperCaseCheckBox.isSelected() && !lowerCaseCheckBox.isSelected() &&
            !numbersCheckBox.isSelected() && !specialCharsCheckBox.isSelected()) {
            resultArea.setText("Não foi possível gerar a senha, por favor escolher no mínimo um opção de senha");
            return;
        } else {
            resultArea.setText(""); // Limpa a mensagem anterior
        }

        int length;
        try {
            length = Integer.parseInt(lengthField.getText());
        } catch (NumberFormatException e) {
            resultArea.setText("Por favor, insira um comprimento válido.");
            return;
        }

        if (length < 8 || length > 20) {
            resultArea.setText("Por favor, insira um comprimento de senha entre 8 e 20 caracteres.");
            return;
        }

        boolean includeUpperCase = upperCaseCheckBox.isSelected();
        boolean includeLowerCase = lowerCaseCheckBox.isSelected();
        boolean includeNumbers = numbersCheckBox.isSelected();
        boolean includeSpecialChars = specialCharsCheckBox.isSelected();

        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#$%&*()-+.,;?{[}]^><:";
        StringBuilder allowedChars = new StringBuilder();

        if (includeUpperCase) {
            allowedChars.append(upperCase);
        }
        if (includeLowerCase) {
            allowedChars.append(lowerCase);
        }
        if (includeNumbers) {
            allowedChars.append(numbers);
        }
        if (includeSpecialChars) {
            allowedChars.append(specialChars);
        }

        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allowedChars.length());
            password.append(allowedChars.charAt(index));
        }

        resultArea.setText(password.toString());
    }

    private void copyToClipboard() {
        StringSelection stringSelection = new StringSelection(resultArea.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        JOptionPane.showMessageDialog(this, "Senha copiada com sucesso!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PasswordGenerator().setVisible(true);
            }
        });
    }
}
