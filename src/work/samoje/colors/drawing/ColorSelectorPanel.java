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

/**
 * {@link JPanel} which allows the GUI user to select an arbitrary color from
 * the HTTP color code.
 *
 * @author Jennie Sadler
 *
 */
/*
 * TODO this panel is very primitive and could use an upgrade. Investigate if
 * the JColorChooser class is a more suitable replacement for the home-brewed,
 * pick-your-own color code, minimally-validated solution in place now.
 */
public class ColorSelectorPanel extends JPanel implements ColorProvider {
    private static final long serialVersionUID = 1L;

    private Color activeColor;
    private final JTextField colorInput;
    private final JLabel colorLabel;
    private final JComponent colorView;

    public ColorSelectorPanel() {
        this.setPreferredSize(new Dimension(160, 80));

        this.activeColor = Color.RED;
        this.colorInput = new JTextField("FFFFFF", 4);
        this.add(colorInput);

        this.colorLabel = new JLabel();
        this.add(colorLabel);

        this.colorView = new ColorView();
        this.add(colorView);

        final JButton submitButton = new JButton("Update");
        submitButton.addActionListener(new UpdateListener());
        this.add(submitButton);

        getColorAndUpdate();
        validate();
    }

    @Override
    public Color getColor() {
        return activeColor;
    }

    private void getColorAndUpdate() {
        final String text = colorInput.getText();
        activeColor = new Color(Integer.parseInt(text, 16));
        colorLabel.setText(text);
        colorView.repaint();
    }

    private class UpdateListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            getColorAndUpdate();
        }
    }

    public class ColorView extends JComponent {
        private static final long serialVersionUID = 1L;

        public ColorView() {
            this.setPreferredSize(new Dimension(20, 20));
        }

        @Override
        public void paintComponent(final Graphics g) {
            g.setColor(activeColor);
            g.fillRect(0, 0, 20, 20);
        }
    }
}