package work.samoje.colors.filter.selection;

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

public class FilterPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final FilterBus filterBus;

    private final JSlider valueSelector;
    private final JLabel filterLabel;
    final List<JCheckBox> selectors;

    public FilterPanel(final FilterBus filterBus) {
        this.filterBus = filterBus;

        this.filterLabel = new JLabel();
        this.add(filterLabel);

        this.add(new JLabel());
        this.valueSelector = new JSlider(0, FilterBus.MAX_MULTIPLIER);
        this.valueSelector.setOrientation(SwingConstants.HORIZONTAL);
        this.valueSelector.setValue(10);
        this.valueSelector.setPreferredSize(new Dimension(130,30));
        this.valueSelector.addChangeListener(new RevisedMethodListener());
        this.add(valueSelector);

        this.add(new JLabel());
        selectors = new ArrayList<JCheckBox>();
        for (final FilterMethod method : FilterMethod.values()) {
            if (method != FilterMethod.SPECIAL) {
                final JCheckBox box = new JCheckBox(method.name(), false);
                selectors.add(box);
                box.addActionListener(new RevisedMethodListener());
                this.add(box);
            }
        }

        validate();
    }

    private EnumSet<FilterMethod> selectedValues() {
        final Set<FilterMethod> selections = new HashSet<FilterMethod>();
        for (final JCheckBox checker : selectors) {
            if (checker.isSelected()) {
                selections.add(FilterMethod.valueOf(checker.getText()));
            }
        }
        if (selections.isEmpty()) {
            return EnumSet.allOf(FilterMethod.class);
        } else {
            return EnumSet.copyOf(selections);
        }
    }

    public class RevisedMethodListener implements ActionListener, ChangeListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            filterBus.update(selectedValues(), valueSelector.getValue());
        }

        @Override
        public void stateChanged(final ChangeEvent e) {
            filterBus.update(selectedValues(), valueSelector.getValue());
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(160, 160);
    }
}