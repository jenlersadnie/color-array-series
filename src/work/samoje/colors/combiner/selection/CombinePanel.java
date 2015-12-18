package work.samoje.colors.combiner.selection;

import java.awt.Dimension;
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

public class CombinePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int INITIAL_VALUE = 10;

    private final JSlider valueSelector;
    private final JLabel combinerLabel;
    final List<JCheckBox> selectors;
    private final JLabel valueLabel;
    private final ColorCombinerBus combinerProvider;

    public CombinePanel(final ColorCombinerBus combinerProvider) {
        this.combinerProvider = combinerProvider;

        this.combinerLabel = new JLabel();
        this.add(combinerLabel);

        this.add(new JLabel());
        this.valueLabel = new JLabel(Integer.toString(INITIAL_VALUE));
        this.add(valueLabel);
        this.valueSelector = new JSlider(0, getMaxMultiplier());
        this.valueSelector.setOrientation(SwingConstants.HORIZONTAL);
        this.valueSelector.setValue(INITIAL_VALUE);
        this.valueSelector.setPreferredSize(new Dimension(130, 30));
        this.valueSelector.addChangeListener(new RevisedMethodListener());
        this.add(valueSelector);

        this.add(new JLabel());
        selectors = new ArrayList<JCheckBox>();
        for (final CombineMethod method : CombineMethod.values()) {
            final JCheckBox box = new JCheckBox(method.name(), combinerProvider
                    .getCombineMethods().contains(method));
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
        return ColorCombinerBus.MAX_MULTIPLIER;
    }

    private EnumSet<CombineMethod> selectedValues() {
        final Set<CombineMethod> selections = new HashSet<CombineMethod>();
        for (final JCheckBox checker : selectors) {
            if (checker.isSelected()) {
                selections.add(CombineMethod.valueOf(checker.getText()));
            }
        }
        if (selections.isEmpty()) {
            return EnumSet.allOf(CombineMethod.class);
        } else {
            return EnumSet.copyOf(selections);
        }
    }

    public class RevisedMethodListener implements ActionListener, ChangeListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            valueLabel.setText(Integer.toString(valueSelector.getValue()));
            combinerProvider.update(selectedValues(), valueSelector.getValue());
        }

        @Override
        public void stateChanged(final ChangeEvent e) {
            valueLabel.setText(Integer.toString(valueSelector.getValue()));
            combinerProvider.update(selectedValues(), valueSelector.getValue());
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(160, 280);
    }
}