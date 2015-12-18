package work.samoje.colors.drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ColorPanel extends JPanel implements ColorOverrideProvider {
    private static final long serialVersionUID = 1L;

    private Color activeColor;
    private final JTextField colorInput;
    private final JLabel colorLabel;
    private final JComponent colorView;

    public ColorPanel() {
        this.activeColor = Color.RED;
        this.colorInput = new JTextField("FFFFFF", 4);
        this.add(colorInput);

        this.colorLabel = new JLabel();
        this.add(colorLabel);

        this.colorView = new ColorView();
        this.add(colorView);

        final JButton submitButton = new JButton("Save");
        submitButton.addActionListener(new UpdateListener());
        this.add(submitButton);

        syncValues();
        validate();
    }

    @Override
    public Color getColor() {
        return activeColor;
    }

    private void syncValues() {
        final String text = colorInput.getText();
        activeColor = new Color(Integer.parseInt(text, 16));
        colorLabel.setText(text);
        colorView.repaint();
    }

    private class UpdateListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            syncValues();
        }
    }

    public class ColorView extends JComponent {
        private static final long serialVersionUID = 1L;

        @Override
        public void paintComponent(final Graphics g) {
            g.setColor(activeColor);
            g.fillRect(0, 0, 20, 20);
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(20, 20);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(20, 20);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(160, 120);
    }
}