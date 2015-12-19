package work.samoje.colors;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GenericSelectorPanel<E extends Enum<E>> extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int INITIAL_VALUE = 10;

    private final JSlider valueSelector;
    final List<JCheckBox> selectors;
    private final JLabel valueLabel;
    private final GenericSelectorBus<E> selectorBus;

    public GenericSelectorPanel(final GenericSelectorBus<E> selectorBus) {
        this.selectorBus = selectorBus;

        final int numRows = 2 + selectorBus.getAllOptions().size();
        this.setPreferredSize(new Dimension(160, numRows*20));
        this.setLayout(new GridLayout(numRows, 1));

        this.valueSelector = new JSlider(0, getMaxMultiplier());
        this.valueSelector.setOrientation(SwingConstants.HORIZONTAL);
        this.valueSelector.setValue(INITIAL_VALUE);
        this.valueSelector.setPreferredSize(new Dimension(130, 30));
        this.valueSelector.addChangeListener(new RevisedMethodListener());
        this.add(valueSelector);

        this.valueLabel = new JLabel(Integer.toString(INITIAL_VALUE));
        this.add(valueLabel);

        selectors = new ArrayList<JCheckBox>();
        for (final E method : selectorBus.getAllOptions()) {
            final JCheckBox box = new JCheckBox(method.name(), selectorBus
                    .getSelected().contains(method));
            selectors.add(box);
            box.addActionListener(new RevisedMethodListener());
            this.add(box);
        }

        validate();
    }

    public void setMultiplier(final int value) {
        this.valueSelector.setValue(value);
    }

    public int getMaxMultiplier() {
        return selectorBus.getMaxMultiplier();
    }

    private EnumSet<E> selectedValues() {
        final Set<String> selections = new HashSet<String>();
        for (final JCheckBox checker : selectors) {
            if (checker.isSelected()) {
                selections.add(checker.getText());
            }
        }
        if (selections.isEmpty()) {
            return selectorBus.getAllOptions();
        } else {
            return selectorBus.getEnumSetFor(selections);
        }
    }

    public class RevisedMethodListener implements ActionListener, ChangeListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            valueLabel.setText(Integer.toString(valueSelector.getValue()));
            selectorBus.update(selectedValues(), valueSelector.getValue());
        }

        @Override
        public void stateChanged(final ChangeEvent e) {
            valueLabel.setText(Integer.toString(valueSelector.getValue()));
            selectorBus.update(selectedValues(), valueSelector.getValue());
        }
    }
}