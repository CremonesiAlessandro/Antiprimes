package ui;

import antiprimes.AntiPrimesSequence;
import antiprimes.Number;
import antiprimes.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * The application window.
 */
public class MainWindow extends JFrame implements Observer {

    private AntiPrimesSequence sequence;
    private DefaultListModel display = new DefaultListModel();

    private static final int SHOW_LAST = 5;

    /**
     * Build a window tied to the given sequence of antinumbers.
     */
    public MainWindow(AntiPrimesSequence sequence) {
        this.sequence = sequence;
        setTitle("Antiprimes");

        JScrollPane list = new JScrollPane(new JList(display));
        JButton nextBtn = new JButton("Next");
        JButton resetBtn = new JButton("Reset");
        updateDisplay();

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sequence.computeNext();
                updateDisplay();
            }
        });

        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sequence.reset();
                updateDisplay();
            }
        });

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Last antiprimes found");
        label.setAlignmentX(Container.LEFT_ALIGNMENT);
        list.setAlignmentX(Container.LEFT_ALIGNMENT);
        getContentPane().add(label);
        getContentPane().add(list);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(nextBtn);
        buttonPane.add(resetBtn);
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.setAlignmentX(Container.LEFT_ALIGNMENT);
        getContentPane().add(buttonPane, BorderLayout.WEST);
        pack();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Change the list showing the last antiprimes found so far.
     */
    private void updateDisplay() {
        display.clear();
        for (Number n : sequence.getLastK(SHOW_LAST))
            display.add(0, "" + n.getValue() + " (" + n.getDivisors() + ")");
    }

	@Override
	public void update() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				updateDisplay();
			}
		});
		
	}
}
